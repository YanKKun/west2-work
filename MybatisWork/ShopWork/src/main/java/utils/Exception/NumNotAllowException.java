package utils.Exception;

/**
 * @author 12080
 * 商品数量有误的异常类
 */
public class NumNotAllowException extends RuntimeException{
    public  NumNotAllowException(){}
    public  NumNotAllowException(String message){super(message);}
}
