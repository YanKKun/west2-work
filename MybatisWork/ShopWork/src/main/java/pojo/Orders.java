package pojo;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @author 12080
 * 订单的实体类
 */
public class Orders {
    private int id;
    private double price;
    private LocalDateTime orderTime;
    private List<Goods> goods;

    public Orders() {
    }

    public Orders(int id, double price, LocalDateTime orderTime, List<Goods> goods) {
        this.id = id;
        this.price = price;
        this.orderTime = orderTime;
        this.goods = goods;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }

    public List<Goods> getGoods() {
        return goods;
    }

    public void setGoods(List<Goods> goods) {
        this.goods = goods;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "id=" + id +
                ", price=" + price +
                ", orderTime=" + orderTime +
                ", goods=" + goods +
                '}';
    }
}
