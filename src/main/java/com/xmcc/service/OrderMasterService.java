package com.xmcc.service;

import com.xmcc.common.ResultResponse;
import com.xmcc.dto.DetailPageDto;
import com.xmcc.dto.OrderMasterDto;
import com.xmcc.dto.PageDto;

public interface OrderMasterService<T> {

    //创建订单
    ResultResponse insertOrder(OrderMasterDto masterDto);

    //分页查看订单
    ResultResponse<T> findAllByPage(PageDto pageDto);

    //查看订单详情
    ResultResponse findProductByPage(DetailPageDto detailPageDto);

    //取消订单
    ResultResponse cancelOrder(DetailPageDto detailPageDto);
}
