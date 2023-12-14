import dao.GoodsMapper;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import pojo.Goods;
import pojo.GoodsOrders;
import utils.GoodsUtils;
import utils.MybatisUtils;

import java.util.HashMap;
import java.util.List;

public class GoodsTest {
    @Test
    public void test(){
        GoodsUtils goodsUtils = new GoodsUtils();
//        goodsUtils.saveGoods(1,"百事可乐",3);
//        goodsUtils.saveGoods(2,"可口可乐",3.5);
//        goodsUtils.saveGoods(3,"原石",88);
//        goodsUtils.saveGoods(4,"源石",77);
//        goodsUtils.saveGoods(5,"复仇女神",111);
//        goodsUtils.saveGoods(6,"复仇男神",222);
//        goodsUtils.saveGoods(7,"辣条",10);

//        goodsUtils.saveGood(1,"辣条",2);
        //商品编号已存在
//        goodsUtils.saveGood(8,"打胶",-10);
        //商品价格设置有误
    }

    @Test
    public void test2(){
        GoodsUtils goodsUtils = new GoodsUtils();
        goodsUtils.removeGoodsById(7);
//
        goodsUtils.updateGood(1,"百事可乐",3.99);

//        goodsUtils.removeGoodsById(8);
        //商品不存在
    }
    @Test
    public void test3(){
        GoodsUtils goodsUtils = new GoodsUtils();
//        goodsUtils.listGood();

//        goodsUtils.getGoodById(1);

//        goodsUtils.getGoodById(10);
        //商品不存在

//        goodsUtils.listGoodsByLimit(0,3);

//        goodsUtils.listGoodsByPrice();

        HashMap map = new HashMap();
        map.put("name","可乐");
        goodsUtils.listGoodsByLike(map);

//        goodsUtils.listGoodsByLimit(10,3);
        //起始点输入有误
//        goodsUtils.listGoodsByLimit(0,10);
        //一页大小输入过大

    }



}
