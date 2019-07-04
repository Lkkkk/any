package com.example.admin.any.userinfo;

/**
 * Created by kk on 2018/1/26.
 * 描述：
 * 玄幻代码,切莫乱改
 */

public interface IUserBase {
  /**
   * @param username 用户名
   * @param password 密码
   * @param fingerprint 人机识别码
   * @param fingerprint2 人机识别码
   * @param captcha 验证码
   * @param actionlogin 登录
   * @param x x坐标
   * @param y y坐标
   * @return ob
   */

  void login(String username, String password, String fingerprint, String fingerprint2,
      String captcha, String actionlogin, String x, String y, String referer);

}
