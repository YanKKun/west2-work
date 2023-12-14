package dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import pojo.Goods;

import java.util.List;
import java.util.Map;

/**
 * @author 12080
 */
public interface GoodsMapper {
    //通过价格排序查询商品
    List<Goods> listGoodsByPrice();

    //模糊查找商品
    List<Goods> listGoodsByLike(Map map);

    //通过id查找商品
    Goods getGoodById(@Param("id")int id);

    //查询全部商品
    List<Goods> listGoods();

    //增加商品
    int saveGood(Goods goods);

    //更新商品
    int updateGood(Map map);

    //通过id删除商品
    int removeGoodsById(@Param("goodId") int goodId);

    //删除订单中的商品
    int removeGoodInOrder(@Param("goodId")int goodId);

    //修改商品存在
    @Update("update goods set exist = 1 where goodId = #{goodId}")
    void updateGoodExist(@Param("goodId")int goodId);

    //分页查询商品
    List<Goods> listGoodsByLimit(@Param("beginStep")int beginStep,@Param("pageSize")int pageSize);

    //获得存在的商品数量
    @Select("select goodId from goods where exist = 1")
    List<Goods> countGoods();

    //通过id返回对象 辅助其他方法
    Goods getGoodObject(@Param("goodId")int goodId);

    //通过id判断商品是否存在
    int judgeGoodExistById(@Param("goodId")int goodId);
}
