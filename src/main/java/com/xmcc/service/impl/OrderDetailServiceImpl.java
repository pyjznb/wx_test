package com.xmcc.service.impl;

import com.xmcc.dao.impl.BathDaoImpl;
import com.xmcc.model.OrderDetail;
import com.xmcc.service.OrderDetailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderDetailServiceImpl extends BathDaoImpl<OrderDetail> implements OrderDetailService {

    //批量插入
    @Override
    @Transactional
    public void batchInsert(List<OrderDetail> list) {
        super.batchInsert(list);
    }
}
