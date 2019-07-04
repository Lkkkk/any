package com.example.admin.any.splash;

import com.example.admin.any.userinfo.UserPresenter;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

/**
 * Created by kk on 2018/1/26.
 * 描述 欢迎界面业务逻辑
 * 玄幻代码,切莫乱改
 */

public class SplashPresenter extends MvpBasePresenter<SplashView> implements ISplash {

  private UserPresenter userPresenter;

  public SplashPresenter(UserPresenter userPresenter) {
    this.userPresenter = userPresenter;
  }

  @Override
  public void login(String username, String password, String fingerprint, String fingerprint2,
      String captcha, String actionlogin, String x, String y, String referer) {
    userPresenter.login(username, password, fingerprint, fingerprint2, captcha, actionlogin, x, y,
        referer);
  }
}
