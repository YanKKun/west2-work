package utils.Exception;

/**
 * @author 12080
 * 订单编号已存在的异常类
 */
public class OrderIdExistException extends RuntimeException{
    public OrderIdExistException(){}
    public OrderIdExistException(String message){super(message);}
}
