package com.example.admin.any.userinfo;

import android.util.Log;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

/**
 * Created by kk on 2018/1/26.
 * 描述：用户登录/注册的业务逻辑
 * 玄幻代码,切莫乱改
 */

public class UserPresenter extends MvpBasePresenter<UserView> implements IUser {

  public UserPresenter() {
    Log.d("UserPresenter","create-----------UserPresenter");
  }

  @Override
  public void login(String username, String password, String fingerprint, String fingerprint2,
      String captcha, String actionlogin, String x, String y, String referer) {
    login(username, password, fingerprint, fingerprint2, captcha, actionlogin, x, y, referer, null);
  }

  @Override public void register(String next, String username, String password1, String password2,
      String email, String captchaInput, String fingerprint, String vip, String actionSignup,
      String submitX, String submitY, String ipAddress, String referer) {
  }

  private void login(String username, String password, String fingerprint, String fingerprint2,
      String captcha, String actionlogin, String x, String y, String referer,
      final LoginListener loginListener) {

  }




  //登录监听
  public interface LoginListener {
    void loginSuccess();

    void loginFailure(String message);
  }
}
