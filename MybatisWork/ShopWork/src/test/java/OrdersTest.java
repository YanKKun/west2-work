import dao.OrdersMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionException;
import org.junit.Test;
import pojo.GoodsOrders;
import pojo.Orders;
import utils.Exception.OrderIdExistException;
import utils.MybatisUtils;
import utils.OrdersUtils;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class OrdersTest {
    @Test
    public void test(){
        OrdersUtils ordersUtils = new OrdersUtils();
//        ordersUtils.addOrder(1,LocalDateTime.now());
//        ordersUtils.addOrder(2,LocalDateTime.now());
//        ordersUtils.addOrder(3,LocalDateTime.now());

//        ordersUtils.addOrder(3,LocalDateTime.now());
        //订单编号已存在
    }

    @Test
    public void test2(){
        OrdersUtils ordersUtils = new OrdersUtils();
//        ordersUtils.saveGoodIntoOrder("可口可乐",1,10);
//        ordersUtils.saveGoodIntoOrder("百事可乐",1,6);
//        ordersUtils.saveGoodIntoOrder("原石",1,8);
//        ordersUtils.saveGoodIntoOrder("复仇男神",1,2);
//        ordersUtils.saveGoodIntoOrder("复仇女神",1,18);
//        ordersUtils.saveGoodIntoOrder("可口可乐",2,10);
//        ordersUtils.saveGoodIntoOrder("百事可乐",2,6);
//        ordersUtils.saveGoodIntoOrder("原石",2,8);
//        ordersUtils.saveGoodIntoOrder("复仇男神",2,2);
//        ordersUtils.saveGoodIntoOrder("可口可乐",3,10);
//        ordersUtils.saveGoodIntoOrder("百事可乐",3,6);
//        ordersUtils.saveGoodIntoOrder("复仇女神",3,8);
//        ordersUtils.saveGoodIntoOrder("复仇男神",3,2);

        //添加重复商品时直接增加num值
//        ordersUtils.saveGoodIntoOrder("原石",1,8);
//        ordersUtils.saveGoodIntoOrder("原石",1,8);

//        ordersUtils.saveGoodIntoOrder("百事可乐",4,4);
        //订单不存在
//        ordersUtils.saveGoodIntoOrder("百事可乐",1,-34);
        //数量有误
    }
    @Test
    public void test3() {
        OrdersUtils ordersUtils = new OrdersUtils();
//        ordersUtils.getOrderById(1);
//        ordersUtils.getOrderById(5);
        //订单不存在
//        ordersUtils.listOrderByOrderTime();
//        ordersUtils.listOrderByPrice();
//        ordersUtils.listOrderByLimit(0,2);
//        ordersUtils.listOrder();
//
//        ordersUtils.removeOrder(3);
//        ordersUtils.removeOrder(4);
        //订单不存在
    }
    @Test
    public void test4(){
        OrdersUtils ordersUtils = new OrdersUtils();
//        ordersUtils.updateOrder(1,1,20);

//        ordersUtils.updateOrder(10,3,20);
        //订单内商品不存在
//        ordersUtils.updateOrder(1,3,20);
        //订单不存在
//        ordersUtils.updateOrder(1,1,-20);
        //订单不存在

//        ordersUtils.removeGoodInOrderById(2,1);

//        ordersUtils.removeGoodInOrderById(4,1);
        //订单不存在
//        ordersUtils.removeGoodInOrderById(2,10);
        //订单中商品不存在
    }


}
