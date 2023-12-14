package utils.Exception;

/**
 * @author 12080
 * 价格输入的异常类
 */
public class PriceNotAllowException extends RuntimeException{
    public PriceNotAllowException(){}
    public PriceNotAllowException(String message){super(message);}
}
