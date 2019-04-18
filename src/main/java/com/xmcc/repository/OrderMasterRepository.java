package com.xmcc.repository;

import com.xmcc.model.OrderMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface OrderMasterRepository extends JpaRepository<OrderMaster,String> {

    @Query(value = "select * from order_master where order_id = ?1 and buyer_openid = ?2",nativeQuery = true)
    OrderMaster findOrderMasterByOpenidAndOrderId(String orderId,String openid);

}
