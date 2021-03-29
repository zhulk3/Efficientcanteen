package com.longkai.canteen.db;

import org.litepal.crud.LitePalSupport;

public class Type extends LitePalSupport {
  private int id;
  private String name;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "Type{" +
        "id=" + id +
        ", name='" + name + '\'' +
        '}';
  }
}
