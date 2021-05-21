package com.longkai.canteen.db;

import org.litepal.crud.LitePalSupport;

import android.graphics.drawable.Drawable;

public class Dish extends LitePalSupport {

  private int id;
  private String code;
  private String name;
  private String type;
  private String pic;
  private Drawable dishName;
  private int num;
  private double price;
  private String intro;
  private int status;
  private double priceFrom;
  private double priceTo;
  private int whichCanteens;
  private int whichday;
  private int whichMeal;

  public Drawable getDishName() {
    return dishName;
  }

  public void setDishName(Drawable dishName) {
    this.dishName = dishName;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getPic() {
    return pic;
  }

  public void setPic(String pic) {
    this.pic = pic;
  }

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

  public String getIntro() {
    return intro;
  }

  public void setIntro(String intro) {
    this.intro = intro;
  }

  public int getWhichCanteens() {
    return whichCanteens;
  }

  public void setWhichCanteens(int whichCanteens) {
    this.whichCanteens = whichCanteens;
  }

  public int getWhichday() {
    return whichday;
  }

  public void setWhichday(int whichday) {
    this.whichday = whichday;
  }

  public int getWhichMeal() {
    return whichMeal;
  }

  public void setWhichMeal(int whichMeal) {
    this.whichMeal = whichMeal;
  }

  @Override
  public String toString() {
    return "Dish{" +
        "id=" + id +
        ", code='" + code + '\'' +
        ", name='" + name + '\'' +
        ", type=" + type +
        ", pic='" + pic + '\'' +
        ", num=" + num +
        ", price=" + price +
        ", intro='" + intro + '\'' +
        ", status=" + status +
        ", priceFrom=" + priceFrom +
        ", priceTo=" + priceTo +
        ", whichCanteens=" + whichCanteens +
        ", whichday=" + whichday +
        '}';
  }
}
