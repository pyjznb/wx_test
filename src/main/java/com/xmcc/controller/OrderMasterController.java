package com.xmcc.controller;

import com.google.common.collect.Maps;
import com.xmcc.common.ResultResponse;
import com.xmcc.dto.DetailPageDto;
import com.xmcc.dto.OrderMasterDto;
import com.xmcc.dto.PageDto;
import com.xmcc.service.OrderMasterService;
import com.xmcc.utils.JsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("buyer/order")
@Api(value = "订单相关接口",description = "完成订单的增删改查")
public class OrderMasterController {

    @Autowired
    private OrderMasterService masterService;

    //创建订单
    @RequestMapping("/create")
    @ApiOperation(value = "创建订单",httpMethod = "POST",response = ResultResponse.class)
    public ResultResponse create(@Valid @ApiParam(name = "订单对象",value = "传入json格式",required = true
    ) OrderMasterDto masterDto, BindingResult bindingResult){
        //校验参数  创建Map集合
        Map<String,String> map = Maps.newHashMap();
        //判参数是否有误
        if (bindingResult.hasErrors()){
            List<String> collect = bindingResult.getFieldErrors().stream().map(err ->err.getDefaultMessage())
                    .collect(Collectors.toList());
            map.put("参数校验异常", JsonUtil.object2string(collect));
        }
        return masterService.insertOrder(masterDto);
    }

    //分页查看订单列表
    @RequestMapping("/list")
    @ApiOperation(value = "订单列表",httpMethod = "GET",response = ResultResponse.class)
    public ResultResponse pageList(@Valid @ApiParam(name = "订单对象",value = "传入json格式",required = true
    ) PageDto pageDto,BindingResult bindingResult){

        //校验参数  创建Map集合
        Map<String,String> map = Maps.newHashMap();
        //判参数是否有误
        if (bindingResult.hasErrors()){
            List<String> collect = bindingResult.getFieldErrors().stream().map(err ->err.getDefaultMessage())
                    .collect(Collectors.toList());
            map.put("参数校验异常", JsonUtil.object2string(collect));
        }
        ResultResponse page = masterService.findAllByPage(pageDto);
        return page;

    }

    //查看订单详情
    @RequestMapping("/detail")
    @ApiOperation(value = "查看订单详情",httpMethod = "GET",response = ResultResponse.class)
    public ResultResponse getProduct(@Valid @ApiParam(name = "订单对象",value = "传入json格式",required = true)
            DetailPageDto detailPageDto,BindingResult bindingResult){

        //校验参数  创建Map集合
        Map<String,String> map = Maps.newHashMap();
        //判参数是否有误
        if (bindingResult.hasErrors()){
            List<String> collect = bindingResult.getFieldErrors().stream().map(err ->err.getDefaultMessage())
                    .collect(Collectors.toList());
            map.put("参数校验异常", JsonUtil.object2string(collect));
        }
        ResultResponse productByPage = masterService.findProductByPage(detailPageDto);
        return productByPage;
    }

    //取消订单
    @RequestMapping("/cancel")
    @ApiOperation(value = "取消订单",httpMethod = "POST",response = ResultResponse.class)
    public ResultResponse cancelOrder(@Valid @ApiParam(name = "订单对象",value = "传入json格式",required = true)
            DetailPageDto detailPageDto,BindingResult bindingResult){
      //校验参数  创建Map集合
        Map<String,String> map = Maps.newHashMap();
        //判参数是否有误
        if (bindingResult.hasErrors()){
            List<String> collect = bindingResult.getFieldErrors().stream().map(err ->err.getDefaultMessage())
                    .collect(Collectors.toList());
            map.put("参数校验异常", JsonUtil.object2string(collect));
        }
        ResultResponse resultResponse = masterService.cancelOrder(detailPageDto);
        return resultResponse;
    }
}
