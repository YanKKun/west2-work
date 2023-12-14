package utils.Exception;

/**
 * @author 12080
 * 订单不存在到的异常类
 */
public class OrderNotFoundException extends RuntimeException{

    public OrderNotFoundException(){}
    public OrderNotFoundException(String message){super(message);}
}
