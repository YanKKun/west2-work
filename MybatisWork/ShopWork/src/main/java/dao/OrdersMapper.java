package dao;

import com.sun.org.apache.xpath.internal.operations.Or;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import pojo.Goods;
import pojo.GoodsOrders;
import pojo.Orders;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 12080
 */
public interface OrdersMapper {
    //查询全部订单
    List<Orders> listOrder();

    //查询指定订单
    Orders getOrderById(@Param("id")int id);

    //查询添加订单
    int saveOrder(@Param("id")int id, @Param("orderTime") LocalDateTime date);

    //查找商品id
    @Select("select goodId from goods where name=#{name} and exist = 1")
    int findGoodId(@Param("name")String name);

    //增加商品进入订单
    @Insert("insert into goods_Orders(gid,oid,num) values(#{goodId},#{orderId},#{num})")
    int saveGoodIntoOrder(@Param("goodId")int goodId,@Param("orderId") int orderId,@Param("num")int num);

    //更新订单
    void updateOrderPrice(@Param("id")int id);

    //删除订单
    int removeOrderById(@Param("id")int id);

    //删除关联表中的商品
    int removeIdInMidTable(@Param("id")int id);

    //通过id删除订单中的商品
    int removeGoodInOrderById(@Param("oid")int oid,@Param("gid")int gid);

    //通过id获得订单对象 用于辅助其他功能
    @Select("select id,price,order_time from orders where id =#{id}")
    Orders getOrderObjectById(@Param("id")int id);

    //通过商品编号，订单编号获得中间表对象 辅助其他功能
    GoodsOrders getOidGid(@Param("gid")int gid, @Param("oid")int oid);

    //根据价格查询订单
    List<Orders> listOrderByPrice();

    //根据下单时间查询订单
    List<Orders> listOrderByOrderTime();

    //更新订单
    int updateOrder(@Param("gid")int gid,@Param("oid")int oid,@Param("num")int num);
}
