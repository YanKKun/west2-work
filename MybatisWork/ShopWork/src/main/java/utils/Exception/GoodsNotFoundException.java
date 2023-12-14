package utils.Exception;

/**
 * @author 12080
 * 商品不存在的异常类
 */
public class GoodsNotFoundException extends RuntimeException {
    public  GoodsNotFoundException(){}
    public GoodsNotFoundException(String message){super(message);}
}
