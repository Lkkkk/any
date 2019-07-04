package com.example.admin.any.userinfo;

/**
 * Created by kk on 2018/1/27.
 * 描述：继承base 增加register抽象方法
 * 玄幻代码,切莫乱改
 */

public interface IUser extends IUserBase {

  void register(String next, String username, String password1, String password2, String email,
      String captchaInput, String fingerprint, String vip, String actionSignup, String submitX,
      String submitY, String ipAddress, String referer);

}
