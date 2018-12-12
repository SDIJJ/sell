package com.imooc.sell.dao;

import com.imooc.sell.dataObject.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderMasterRepository extends JpaRepository<OrderMaster,String> {

    OrderMaster findByOrderId(String orderId);
    Page<OrderMaster> findByOrderId(String buyerOpenid, Pageable pageable);

}
