package com.example.admin.any.bean;

/**
 * Created by kk on 2018/1/26.
 * 描述：用户基类
 * 玄幻代码,切莫乱改
 */

public class User {
  private String userName;
  private boolean isLogin;
  private int userId;
  private String status;
  private String lastLoginTime;
  private String lastLoginIP;

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public boolean isLogin() {
    return isLogin;
  }

  public void setLogin(boolean login) {
    isLogin = login;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getLastLoginTime() {
    return lastLoginTime;
  }

  public void setLastLoginTime(String lastLoginTime) {
    this.lastLoginTime = lastLoginTime;
  }

  public String getLastLoginIP() {
    return lastLoginIP;
  }

  public void setLastLoginIP(String lastLoginIP) {
    this.lastLoginIP = lastLoginIP;
  }
}
