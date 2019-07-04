package com.example.admin.any.userinfo;

import com.example.admin.any.BaseView;

/**
 * Created by kk on 2018/1/26.
 * 描述：用户操作界面
 * 玄幻代码,切莫乱改
 */

public interface UserView extends BaseView {

  public void loginSuccess();

  public void loginError(String message);

  public void registerSuccess();

  public void registerFailure(String message);
}
