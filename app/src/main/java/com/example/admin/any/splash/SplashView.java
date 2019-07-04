package com.example.admin.any.splash;

import com.example.admin.any.BaseView;

/**
 * Created by kk on 2018/1/26.
 * 描述：欢迎界面lce接口
 * 玄幻代码,切莫乱改
 */

public interface SplashView extends BaseView {

  void loginSuccess();

  void loginError(String message);


}
