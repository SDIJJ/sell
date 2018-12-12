package com.imooc.sell;

import com.imooc.sell.Utils.KeyUtil;
import com.imooc.sell.dao.OrderMasterRepository;
import com.imooc.sell.dataObject.OrderMaster;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SellApplicationTests {
    @Autowired
    private OrderMasterRepository orderMasterRepository;
	@Test
	public void contextLoads() {
        OrderMaster orderMaster=new OrderMaster();
       // orderMaster.setOrderId("3333");
        orderMaster.setBuyerName("王聪");
        orderMaster.setBuyerAddress("山东");
        orderMaster.setBuyerPhone("111111111");
        orderMaster.setBuyerOpenid("54321");
        orderMaster.setPayStatus(0);
        orderMaster.setOrderStatus(0);
        this.orderMasterRepository.save(orderMaster);
	}

}
