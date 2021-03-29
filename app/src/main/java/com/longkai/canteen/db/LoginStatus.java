package com.longkai.canteen.db;

import org.litepal.crud.LitePalSupport;

public class LoginStatus extends LitePalSupport {
  private String success;
  private String message;

  public String getSuccess() {
    return success;
  }

  public void setSuccess(String success) {
    this.success = success;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
