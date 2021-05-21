package com.longkai.canteen.json;

public class Inserted {
  private int num;
  private double price;
  private double totalprice;
  private int pid;

  public int getNum() {
    return num;
  }

  public void setNum(int num) {
    this.num = num;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public double getTotalprice() {
    return totalprice;
  }

  public void setTotalprice(double totalprice) {
    this.totalprice = totalprice;
  }

  public int getPid() {
    return pid;
  }

  public void setPid(int pid) {
    this.pid = pid;
  }

  @Override
  public String toString() {
    return "Inserted{" +
        "num=" + num +
        ", price=" + price +
        ", totalprice=" + totalprice +
        ", pid=" + pid +
        '}';
  }
}
