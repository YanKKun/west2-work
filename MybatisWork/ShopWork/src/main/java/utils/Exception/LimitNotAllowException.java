package utils.Exception;

/**
 * @author 12080
 * 分页数据有误的异常类
 */
public class LimitNotAllowException extends RuntimeException {
    public LimitNotAllowException(){}
    public LimitNotAllowException(String message){super(message);}
}
