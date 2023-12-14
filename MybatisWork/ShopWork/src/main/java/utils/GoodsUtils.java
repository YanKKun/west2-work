package utils;

import dao.GoodsMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import pojo.Goods;
import utils.Exception.GoodIdExistException;
import utils.Exception.GoodsNotFoundException;
import utils.Exception.LimitNotAllowException;
import utils.Exception.PriceNotAllowException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 12080
 * 不知道怎么在mapper调用方法时抛出异常而创建的商品工具类
 * 不用重复创建mapper调用方法
 * 简化了操作
 */
public class GoodsUtils {
    //模糊查询商品
    public void listGoodsByLike(Map map){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        GoodsMapper mapper = sqlSession.getMapper(GoodsMapper.class);
        List<Goods> goods = mapper.listGoodsByLike(map);
        if (goods.isEmpty()){
            System.out.println("未查找到有关商品");
        }
        for (Goods good : goods) {
            System.out.println(good.toString(good));
        }
        sqlSession.close();
    }

    //分页查询商品
    public void listGoodsByLimit(int beginStep,int pageSize){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        GoodsMapper mapper = sqlSession.getMapper(GoodsMapper.class);
        List<Goods> goodsNum = mapper.countGoods();
        int num = goodsNum.size();
        if(beginStep<0||beginStep>=num){
            sqlSession.close();
            throw new LimitNotAllowException("起始点输入有误");
        }
        if (pageSize>num){
            sqlSession.close();
            throw new LimitNotAllowException("一页大小输入过大");
        }
        for (int i = beginStep; i <num ; i+=pageSize) {
            List<Goods> goodsByLimit = mapper.listGoodsByLimit(i,pageSize);
            for (Goods goods : goodsByLimit) {
                System.out.println(goods.toString(goods));
            }
            System.out.println("===============================================");
        }
        sqlSession.close();
    }

    //根据价格排序查询订单
    public void listGoodsByPrice(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        GoodsMapper mapper = sqlSession.getMapper(GoodsMapper.class);
        List<Goods> goodsByPrice = mapper.listGoodsByPrice();
        for (Goods goods : goodsByPrice) {
            System.out.println(goods.toString(goods));
        }
        sqlSession.close();
    }

    //查询商品列表
    public void listGood(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        GoodsMapper mapper = sqlSession.getMapper(GoodsMapper.class);
        List<Goods> goods = mapper.listGoods();
        for (Goods good : goods) {
            System.out.println(good.toString(good));
        }
        sqlSession.close();
    }

    //通过id获取商品
    public void getGoodById(int goodId){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        GoodsMapper mapper = sqlSession.getMapper(GoodsMapper.class);
        Goods goodById = mapper.getGoodById(goodId);
        sqlSession.close();
        if(goodById==null){
            throw new GoodsNotFoundException("商品不存在");
        }
        System.out.println(goodById.toString(goodById));
    }

    //通过id返回商品对象 用于辅助其他功能
    public Goods getGoodObjectById(int goodId){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        GoodsMapper mapper = sqlSession.getMapper(GoodsMapper.class);
        Goods resultGoodObject = mapper.getGoodObject(goodId);
        sqlSession.close();
        return resultGoodObject;
    }

    //添加商品
    public void saveGood(int goodId,String name,double price){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        GoodsMapper mapper = sqlSession.getMapper(GoodsMapper.class);
        if(price<=0){
            sqlSession.close();
            throw new PriceNotAllowException("价格有误请重新输入");
        }
        Goods goodObject = getGoodObjectById(goodId);
        if(goodObject!=null&&mapper.getGoodById(goodId)!=null){
            sqlSession.close();
            throw new GoodIdExistException("商品已存在");
        }else if(goodObject==null&&mapper.getGoodById(goodId)==null){
            int i = mapper.saveGood(new Goods(goodId, name, price));
            if(i>0){
                System.out.println("添加成功");
            }
        }else if(mapper.judgeGoodExistById(goodId)!=0){
            mapper.updateGoodExist(goodId);
            System.out.println("添加成功");
        }
        sqlSession.close();

    }

    //更新商品
    public void updateGood(int id,String name,double price){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        GoodsMapper mapper = sqlSession.getMapper(GoodsMapper.class);
        HashMap map = new HashMap();
        Goods goodObject = getGoodObjectById(id);
        if(goodObject==null||goodObject.getExist()==-1){
            sqlSession.close();
            throw new GoodsNotFoundException("商品不存在");
        }
        map.put("goodId",id);
        map.put("name",name);
        map.put("price",price);
        int i = mapper.updateGood(map);
        if(i!=0){
            System.out.println("修改成功");
        }
        sqlSession.close();
    }

    //通过id删除商品
    public void removeGoodsById(int goodId){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        GoodsMapper mapper = sqlSession.getMapper(GoodsMapper.class);
        Goods goodObject = getGoodObjectById(goodId);
        if (goodObject==null||goodObject.getExist()==-1){
            sqlSession.close();
            throw new GoodsNotFoundException("商品不存在");
        }
        goodObject.setExist(-1);
        int i = mapper.removeGoodsById(goodId);
        if(i!=0){
            System.out.println("删除成功");
        }
        sqlSession.close();
    }
}
