package com.xmcc.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xmcc.common.*;
import com.xmcc.dto.*;
import com.xmcc.exception.CustomException;
import com.xmcc.model.OrderDetail;
import com.xmcc.model.OrderMaster;
import com.xmcc.model.ProductInfo;
import com.xmcc.repository.OrderDetailRepository;
import com.xmcc.repository.OrderMasterRepository;
import com.xmcc.service.OrderDetailService;
import com.xmcc.service.OrderMasterService;
import com.xmcc.service.ProductInfoService;
import com.xmcc.utils.BigDecimalUtil;
import com.xmcc.utils.IDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class OrderMasterServiceImpl implements OrderMasterService {

    @Autowired
    private ProductInfoService infoService;

    @Autowired
    private OrderDetailService detailService;

    @Autowired
    private OrderMasterRepository masterRepository;

    @Autowired
    private OrderDetailRepository detailRepository;

    //创建订单
    @Override
    public ResultResponse insertOrder(OrderMasterDto masterDto) {

        //取出订单项
        List<OrderDetailDto> items = masterDto.getOrderDetailList();
        //创建集合来存储OrderDetail
        ArrayList<OrderDetail> detailList = Lists.newArrayList();
        //初始化订单总金额
        BigDecimal totalPrice = new BigDecimal("0");

        //遍历订单项，取出商品详情
        for (OrderDetailDto dto: items) {
            //查询订单
            ResultResponse<ProductInfo> infoList = infoService.queryById(dto.getProductId());
            //判断ResultResponse的code即可
            if (infoList.getCode() == ResultEnums.FAIL.getCode()){
                throw new CustomException(infoList.getMsg());
            }

            //得到商品
            ProductInfo info = infoList.getData();
            //比较库存
            if (info.getProductStock() < dto.getProductQuantity()){
                throw new CustomException(ProductEnums.PRODUCT_NOT_ENOUGH.getMsg());
            }
            //创建订单项
            OrderDetail orderDetail = OrderDetail.builder().detailId(IDUtils.createIdbyUUID()).productIcon(info.getProductIcon())
                    .productId(dto.getProductId()).productName(info.getProductName())
                    .productPrice(info.getProductPrice()).productQuantity(dto.getProductQuantity()).build();
            //添加到订单项集合以后
            detailList.add(orderDetail);
            //买了东西，库存数量减少
            info.setProductStock(info.getProductStock() - dto.getProductQuantity());
            //更新商品数据
            infoService.updateProduct(info);
            //计算价格
            totalPrice = BigDecimalUtil.add(totalPrice,BigDecimalUtil.multi(info.getProductPrice(),dto.getProductQuantity()));
            //到这里  订单项算全部封装完全了

        }
        //生成订单Id
        String order_id = IDUtils.createIdbyUUID();
        //构建订单信息
        OrderMaster master = OrderMaster.builder()
                .orderId(order_id).buyerAddress(masterDto.getAddress())
                .buyerName(masterDto.getName()).buyerOpenid(masterDto.getOpenid())
                .buyerPhone(masterDto.getPhone()).orderAmount(totalPrice)
                .orderStatus(OrderEnums.NEW.getCode())
                .payStatus(PayEnums.WAIT.getCode()).build();

        //把生成的订单id设置到订单项中
        List<OrderDetail> orderDetails = detailList.stream().map(orderDetail -> {
            orderDetail.setOrderId(order_id);
            return orderDetail;
        }).collect(Collectors.toList());

        detailService.batchInsert(orderDetails);

        //插入订单
        masterRepository.save(master);
        HashMap<String, String> map = Maps.newHashMap();
        map.put("orderId",order_id);


        return ResultResponse.success(map);
    }

    //分页查看订单
    @Override
    public ResultResponse<OrderMaster> findAllByPage(PageDto pageDto) {

        Pageable pageable = PageRequest.of(pageDto.getPage(),pageDto.getSize());
        OrderMaster master = new OrderMaster();
        master.setBuyerOpenid(pageDto.getOpenid());
        Example<OrderMaster> example = Example.of(master);
        Page<OrderMaster> page = masterRepository.findAll(example, pageable);

        List<OrderMaster> content = page.getContent();
        List<OrderMasterPageDto> collect = content.stream().map(orderMaster ->
                OrderMasterPageDto.build(orderMaster)
        ).collect(Collectors.toList());

        return ResultResponse.success(collect);
    }

    //查看订单项详情
    @Override
    public ResultResponse findProductByPage(DetailPageDto detailPageDto) {
        OrderMaster master = masterRepository.findOrderMasterByOpenidAndOrderId(detailPageDto.getOrderId(), detailPageDto.getOpenid());
        if (master == null){

            return ResultResponse.fail();
        }
        OrderMasterPageDto masterPageDto = OrderMasterPageDto.build(master);
        OrderDetail detail = detailRepository.findOrderDetailByOrderId(master.getOrderId());

        masterPageDto.setOrderDetailList(Lists.newArrayList(detail));

        return ResultResponse.success(masterPageDto);
    }

    //取消订单
    @Override
    public ResultResponse cancelOrder(DetailPageDto detailPageDto) {
        OrderMaster master = masterRepository.findOrderMasterByOpenidAndOrderId(detailPageDto.getOrderId(), detailPageDto.getOpenid());
        if (master == null){
            return ResultResponse.fail();
        }
        if (master.getPayStatus() == OrderEnums.NEW.getCode()){
            master.setOrderStatus(OrderEnums.CANCEL.getCode());
            masterRepository.save(master);
        }

        return ResultResponse.success();
    }

}
