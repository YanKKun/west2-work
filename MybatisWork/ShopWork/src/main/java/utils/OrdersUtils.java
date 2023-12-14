package utils;

import dao.GoodsMapper;
import dao.OrdersMapper;
import org.apache.ibatis.session.SqlSession;
import pojo.Goods;
import pojo.Orders;
import utils.Exception.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 12080
 * 不知道怎么在mapper调用方法时抛出异常而创建的订单工具类
 * 不用重复创建mapper调用方法
 * 简化了操作
 */
public class OrdersUtils {
    //分页查询订单列表
    public void listOrderByLimit(int beginStep,int pageSize){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        OrdersMapper mapper = sqlSession.getMapper(OrdersMapper.class);
        List<Orders> orderList = mapper.listOrder();
        int size = orderList.size();
        if(beginStep<0||beginStep>=size){
            sqlSession.close();
            throw new LimitNotAllowException("起始值有误");
        }
        if(pageSize>size){
            sqlSession.close();
            throw new LimitNotAllowException("一页数量过多");
        }
        for (int i = beginStep; i <size; i+=pageSize) {
            for (int j = i; j <i+pageSize ; j++) {
                if(j==size){
                    break;
                }
                System.out.println(orderList.get(j));
            }
            System.out.println("============================================================================");
        }
    }

    //根据价格排序查询订单
    public void listOrderByPrice(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        OrdersMapper mapper = sqlSession.getMapper(OrdersMapper.class);
        List<Orders> orders = mapper.listOrderByPrice();
        for (Orders order : orders) {
            System.out.println(order);
        }
        sqlSession.close();
    }
    //根据下单时间排序查询订单
    public void listOrderByOrderTime(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        OrdersMapper mapper = sqlSession.getMapper(OrdersMapper.class);
        List<Orders> orders = mapper.listOrderByOrderTime();
        for (Orders order : orders) {
            System.out.println(order);
        }
        sqlSession.close();
    }
    //获得订单列表
    public void listOrder(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        OrdersMapper mapper = sqlSession.getMapper(OrdersMapper.class);
        List<Orders> orders = mapper.listOrder();
        for (Orders order : orders) {
            System.out.println(order);
        }
        sqlSession.close();
    }
    //通过id获得订单
    public void getOrderById(int id){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        OrdersMapper mapper = sqlSession.getMapper(OrdersMapper.class);
        if(id<=0){
            sqlSession.close();
            throw new OrderNotFoundException("订单不存在");
        }else {
            Orders order = mapper.getOrderById(id);
            if(order==null){
                sqlSession.close();
                throw new OrderNotFoundException("订单不存在");
            }
            System.out.println(order);
            sqlSession.close();
        }
    }
    //添加订单
    public void saveOrder(int id, LocalDateTime localDateTime){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        OrdersMapper mapper = sqlSession.getMapper(OrdersMapper.class);
        if(getOrderObjectById(id)!=null){
            sqlSession.close();
            throw new OrderIdExistException("订单编号已存在");
        }
        int i = mapper.saveOrder(id, localDateTime);
        if(i!=0){
            System.out.println("添加成功");
        }
        sqlSession.close();
    }

    //删除订单
    public void removeOrderById(int id){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        OrdersMapper mapper = sqlSession.getMapper(OrdersMapper.class);
        if(getOrderObjectById(id)==null){
            sqlSession.close();
            throw new OrderNotFoundException("订单不存在");
        }
        int i = mapper.removeOrderById(id);
        removeIdInMidTable(id);
        if (i!=0){
            System.out.println("删除成功");
        }
        sqlSession.close();
    }

    //删除中间表的订单
    public void removeIdInMidTable(int id){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        OrdersMapper mapper = sqlSession.getMapper(OrdersMapper.class);
        mapper.removeIdInMidTable(id);
        sqlSession.close();
    }

    //将商品添加进订单
    public void saveGoodIntoOrder(String name,int oid,int num){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        OrdersMapper mapper = sqlSession.getMapper(OrdersMapper.class);
        int i1 = mapper.findGoodId(name);
        if(i1==0){
            sqlSession.close();
            throw new GoodsNotFoundException("商品不存在");
        }
        if(num<=0){
            sqlSession.close();
            throw new NumNotAllowException("数量有误");
        }
        if (getOrderObjectById(oid)==null){
            sqlSession.close();
            throw new OrderNotFoundException("订单不存在");
        }
        if(mapper.getOidGid(i1,oid)!=null){
            int i = mapper.updateOrder(i1, oid, mapper.getOidGid(i1, oid).getNum() + num);
            if(i!=0){
                System.out.println("添加成功");
            }
            mapper.updateOrderPrice(oid);
            sqlSession.close();
        }else {
            int i = mapper.saveGoodIntoOrder(i1, oid, num);
            if (i != 0) {
                System.out.println("添加成功");
            }
            mapper.updateOrderPrice(oid);
            sqlSession.close();
        }
    }

    //通过id删除订单中的商品
    public void removeGoodInOrderById(int oid,int gid){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        OrdersMapper mapper = sqlSession.getMapper(OrdersMapper.class);
        Orders orderObjectById = getOrderObjectById(oid);
        if (orderObjectById==null){
            sqlSession.close();
            throw new OrderNotFoundException("订单不存在");
        }
        if (mapper.getOidGid(oid, gid)==null){
            sqlSession.close();
            throw new GoodsNotFoundException("订单中商品不存在");
        }
        int i = mapper.removeGoodInOrderById(oid, gid);
        if (i!=0){
            System.out.println("删除成功");
        }
        mapper.updateOrderPrice(oid);
        sqlSession.close();
    }

    //更新订单
    public void updateOrder(int gid,int oid,int num){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        OrdersMapper mapper = sqlSession.getMapper(OrdersMapper.class);
        if(num<=0){
            sqlSession.close();
            throw new NumNotAllowException("数量有误");
        }
        if (mapper.getOidGid(oid,gid)==null){
            sqlSession.close();
            throw new GoodsNotFoundException("订单内商品不存在");
        }
        if(mapper.getOrderObjectById(oid)==null){
            sqlSession.close();
            throw new OrderNotFoundException("订单不存在");
        }
        int i = mapper.updateOrder(gid, oid, num);
        if(i>0){
            System.out.println("更新成功");
        }
        sqlSession.close();
    }

    //通过id返回订单对象 用于辅助其他功能
    public Orders getOrderObjectById(int id){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        OrdersMapper mapper = sqlSession.getMapper(OrdersMapper.class);
        return mapper.getOrderObjectById(id);
    }
}
