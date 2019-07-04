package com.example.admin.any;

import android.app.Application;
import android.text.TextUtils;
import com.example.admin.any.bean.User;

/**
 * Created by kk on 2017/12/20.
 * 玄幻代码,切莫乱改
 */

public class MyApplication extends Application {

  private static MyApplication instance;
  private User user;

  @Override public void onCreate() {
    super.onCreate();
    instance = this;
  }

  public static MyApplication  getInstance(){
    return instance;
  }

  public User getUser() {
    boolean isUserInfoNotComplete = user == null || user.getUserId() == 0 || TextUtils.isEmpty(user.getUserName());
    if (isUserInfoNotComplete) {
      return null;
    }
    if (user != null && (user.getUserId() == 0 || TextUtils.isEmpty(user.getUserName()))) {
      //收集bug上传到后台
      //Bugsnag.notify(new Throwable("User info: " + user.toString()), Severity.WARNING);
    }
    return user;
  }


}
