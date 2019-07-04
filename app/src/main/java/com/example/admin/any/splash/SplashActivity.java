package com.example.admin.any.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Base64;
import com.example.admin.any.MainActivity;
import com.example.admin.any.MyApplication;
import com.example.admin.any.R;
import com.example.admin.any.bean.User;
import com.example.admin.any.userinfo.UserPresenter;
import com.example.admin.any.utils.KeyUtils;
import com.example.admin.any.utils.SPUtils;
import com.example.admin.any.utils.UserHelper;
import com.hannesdorfmann.mosby3.mvp.MvpActivity;
import com.orhanobut.logger.Logger;

/**
 * Created by kk on 2018/1/26.
 * 描述：欢迎界面
 * 玄幻代码,切莫乱改
 */

public class SplashActivity extends MvpActivity<SplashView, SplashPresenter> implements SplashView {

  private static final String TAG = "SplashActivity";
  private String userName;
  private String userPsw;
  private String randowCode = "1111";

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);
    //防止重复开启程序造成多次登录
    if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
      Logger.t(TAG).d("重复打开了应用。。。。。。");
      finish();
      return;
    }

    User user = MyApplication.getInstance().getUser();
    if (user != null) {
      toMainActivity();
    }
    userName = (String) SPUtils.get(SplashActivity.this, KeyUtils.KEY_SP_USER_LOGIN_USERNAME, "");
    String psw = (String) SPUtils.get(SplashActivity.this, KeyUtils.KEY_SP_USER_LOGIN_PASSWORD, "");
    if (!TextUtils.isEmpty(psw)) {
      userPsw = new String(Base64.decode(psw.getBytes(), Base64.DEFAULT));//对加码的密码进行解码
    }
    //判断是否自动登录
    boolean isAutoLogin = (boolean) SPUtils.get(this, KeyUtils.KEY_SP_USER_AUTO_LOGIN, false);
    if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(userPsw) && isAutoLogin) {
      randowCode = UserHelper.randomCaptcha();
      autoLogin(userName, userPsw, randowCode);
    } else {
      toMainActivity();
    }
  }

  @Override public void loginSuccess() {
    toMainActivity();
  }

  @Override public void loginError(String message) {
    toMainActivity();
  }

  @Override public void showLoading(boolean pullToRefresh) {

  }

  @Override public void showContent() {

  }

  @Override public void showError(Throwable e, boolean pullToRefresh) {

  }

  @Override public void setData(Object data) {

  }

  @Override public void loadData(boolean pullToRefresh) {

  }

  @NonNull @Override public SplashPresenter createPresenter() {
    UserPresenter userPresenter = new UserPresenter();
    return new SplashPresenter(userPresenter);
  }

  private void autoLogin(String name, String password, String code) {

  }

  private void toMainActivity() {
    Intent intent = new Intent();
    intent.setClass(SplashActivity.this, MainActivity.class);
    startActivity(intent);
    finish();
  }
}
