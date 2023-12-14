package pojo;

/**
 * @author 12080
 * 中间表的实体类
 */
public class GoodsOrders {
    private int oid;
    private int gid;
    private int num;

    public GoodsOrders() {
    }

    public GoodsOrders(int oid, int gid, int num) {
        this.oid = oid;
        this.gid = gid;
        this.num = num;
    }

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "GoodsOrders{" +
                "oid=" + oid +
                ", gid=" + gid +
                ", num=" + num +
                '}';
    }
}
