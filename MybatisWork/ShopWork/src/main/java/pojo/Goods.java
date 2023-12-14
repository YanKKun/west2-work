package pojo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 12080
 * 商品的实体类
 */
@Getter
@Setter
public class Goods {
    private int goodId;
    private String name;
    private double price;
    private int num;
    private int exist;

    public Goods() {
    }

    public Goods(int goodId, String name, double price) {
        this.goodId = goodId;
        this.name = name;
        this.price = price;
    }

    public Goods(int goodId, String name, double price, int exist) {
        this.goodId = goodId;
        this.name = name;
        this.price = price;
        this.exist = exist;
    }

    public void setExist(int exist) {
        this.exist = exist;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setGoodId(int goodId) {
        this.goodId = goodId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "goodId=" + goodId +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", num=" + num +
                '}';
    }

    public String toString(Goods good) {
        return "Goods{" +
                "goodId=" + goodId +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
