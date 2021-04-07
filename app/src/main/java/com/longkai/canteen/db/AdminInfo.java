package com.longkai.canteen.db;


import java.sql.Time;

import org.litepal.crud.LitePalSupport;

public class AdminInfo extends LitePalSupport {
  private int id;
  private String name;
  private String pwd;
  private String office;//办公地址
  private String beginTime;
  private String endTime;
  private String telephone;
  private String email;


  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getPwd() {
    return pwd;
  }


  public void setId(int id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setPwd(String pwd) {
    this.pwd = pwd;
  }

  public String getBeginTime() {
    return beginTime;
  }

  public void setBeginTime(String beginTime) {
    this.beginTime = beginTime;
  }

  public String getEndTime() {
    return endTime;
  }

  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }

  public String getTelephone() {
    return telephone;
  }

  public void setTelephone(String telephone) {
    this.telephone = telephone;
  }

  public String getOffice() {
    return office;
  }

  public void setOffice(String office) {
    this.office = office;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public String toString() {
    return "AdminInfo{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", pwd='" + pwd + '\'' +
        ", office='" + office + '\'' +
        ", beginTime=" + beginTime +
        ", endTime=" + endTime +
        ", telephone='" + telephone + '\'' +
        ", email='" + email + '\'' +
        '}';
  }
}


