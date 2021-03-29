package com.longkai.canteen.pojo;

import java.time.DayOfWeek;

public class ChooseDate {
  private String weekday;
  private String date;

  public String getWeekday() {
    return weekday;
  }

  public void setWeekday(String weekday) {
    this.weekday = weekday;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  @Override
  public String toString() {
    return "ChooseDate{" +
        "weekday='" + weekday + '\'' +
        ", date='" + date + '\'' +
        '}';
  }
}
