package com.xmcc.repository;

import com.xmcc.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderDetailRepository extends JpaRepository<OrderDetail,String> {

    @Query(value = "select * from order_detail where order_id = ?1",nativeQuery = true)
    OrderDetail findOrderDetailByOrderId(String orderId);
}
