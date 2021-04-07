package com.longkai.canteen.json;

public class Orderinfo {
  private String ordertime;
  private int uid;
  private String status;
  private double orderprice;

  public String getOrdertime() {
    return ordertime;
  }

  public void setOrdertime(String ordertime) {
    this.ordertime = ordertime;
  }

  public int getUid() {
    return uid;
  }

  public void setUid(int uid) {
    this.uid = uid;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public double getOrderprice() {
    return orderprice;
  }

  public void setOrderprice(double orderprice) {
    this.orderprice = orderprice;
  }

  @Override
  public String toString() {
    return "Orderinfo{" +
        "ordertime='" + ordertime + '\'' +
        ", uid=" + uid +
        ", status=" + status +
        ", orderprice=" + orderprice +
        '}';
  }
}
