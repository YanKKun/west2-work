package utils.Exception;

/**
 * @author 12080
 * 商品已存在的异常类
 */
public class GoodIdExistException extends RuntimeException{
    public GoodIdExistException(String message) {super(message);}
}
