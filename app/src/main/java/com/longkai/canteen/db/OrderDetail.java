package com.longkai.canteen.db;


import org.litepal.crud.LitePalSupport;

public class OrderDetail extends LitePalSupport {
  private int id;
  private int oid;
  private OrderInfo oi;
  private int pid;
  private Dish pi;
  private int num;
  private double price;
  private double totalprice;
  private String dishName;
  private String dishType;
  private String whichCanteen;


  public double getPrice() {
    return price;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setOid(int oid) {
    this.oid = oid;
  }

  public void setOi(OrderInfo oi) {
    this.oi = oi;
  }

  public void setPid(int pid) {
    this.pid = pid;
  }

  public void setPi(Dish pi) {
    this.pi = pi;
  }

  public void setNum(int num) {
    this.num = num;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public void setTotalprice(double totalPrice) {
    this.totalprice = totalPrice;
  }

  public int getId() {
    return id;
  }

  public int getOid() {
    return oid;
  }

  public OrderInfo getOi() {
    return oi;
  }

  public int getPid() {
    return pid;
  }

  public Dish getPi() {
    return pi;
  }

  public int getNum() {
    return num;
  }

  public double getTotalprice() {
    return totalprice;
  }

  public String getDishName() {
    return dishName;
  }

  public void setDishName(String dishName) {
    this.dishName = dishName;
  }

  public String getDishType() {
    return dishType;
  }

  public void setDishType(String dishType) {
    this.dishType = dishType;
  }

  public String getWhichCanteen() {
    return whichCanteen;
  }

  public void setWhichCanteen(String whichCanteen) {
    this.whichCanteen = whichCanteen;
  }

  @Override
  public String toString() {
    return "OrderDetail{" +
        "id=" + id +
        ", oid=" + oid +
        ", oi=" + oi +
        ", pid=" + pid +
        ", pi=" + pi +
        ", num=" + num +
        ", price=" + price +
        ", totalprice=" + totalprice +
        ", dishName='" + dishName + '\'' +
        ", dishType='" + dishType + '\'' +
        ", whichCanteen='" + whichCanteen + '\'' +
        '}';
  }
}

