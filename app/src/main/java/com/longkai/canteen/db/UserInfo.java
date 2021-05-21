package com.longkai.canteen.db;

import org.litepal.crud.LitePalSupport;

public class UserInfo extends LitePalSupport {
    private int id;
    private String userName;
    private String password;
    private String sex;
    private String realName;
    private String address;
    private String email;
    private String regDate;
    private int status;
    private String school;
    private String sno;
    private String balance;

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getSex() {
        return sex;
    }

    public String getRealName() {
        return realName;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getRegDate() {
        return regDate;
    }

    public int getStatus() {
        return status;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
            "id=" + id +
            ", userName='" + userName + '\'' +
            ", password='" + password + '\'' +
            ", sex='" + sex + '\'' +
            ", realName='" + realName + '\'' +
            ", address='" + address + '\'' +
            ", email='" + email + '\'' +
            ", regDate='" + regDate + '\'' +
            ", status=" + status +
            ", school='" + school + '\'' +
            ", sno='" + sno + '\'' +
            ", balance='" + balance + '\'' +
            '}';
    }
}
