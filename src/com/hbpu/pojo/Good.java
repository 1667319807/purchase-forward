package com.hbpu.pojo;

/**
 * @author qiaolu
 * @time 2020/3/1 14:35
 */
public class Good {
    private String id;
    private String gname;
    private String type;
    private Double price;
    private String pic;
    private Integer num;
    public Good() {
    }

    public Good(String id, String gname, String type, Double price, String pic) {
        this.id = id;
        this.gname = gname;
        this.type = type;
        this.price = price;
        this.pic = pic;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
