import cn.javat.javaLearn.experiment4.controller.Impl.OrderControllerImpl;
import cn.javat.javaLearn.experiment4.controller.OrderController;
import cn.javat.javaLearn.experiment4.entity.OrderEntity;
import cn.javat.javaLearn.experiment4.service.Impl.OrderServiceImpl;
import cn.javat.javaLearn.experiment4.service.OrderService;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

public class OrderTest {

    @Test
    public void test() {
        OrderService orderService = OrderServiceImpl.getInstance();
        OrderEntity order = new OrderEntity(1, 1, 1, 1, 1.0, 1L);
//        设置创建时间为时间戳
        order.setCreateTime(new Timestamp(System.currentTimeMillis()).getTime());
//        orderService.insert(order);
        OrderController orderController = new OrderControllerImpl();
    }
}
