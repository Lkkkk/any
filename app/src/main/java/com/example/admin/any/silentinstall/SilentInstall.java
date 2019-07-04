package com.example.admin.any.silentinstall;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * Created by Admin on 2017/11/30.
 */

public class SilentInstall {

  /*
   * android系统命令 pm install 执行安装
   * */
  public boolean install(String apkPath) {
    boolean isInstall = false;
    DataOutputStream dataOutputStream = null;
    BufferedReader bufferedReader = null;
    try {
      //申请su权限
      Process process = Runtime.getRuntime().exec("su");
      dataOutputStream = new DataOutputStream(process.getOutputStream());
      String command = "pm install -r" + apkPath + "\n";
      dataOutputStream.write(command.getBytes(Charset.forName("utf-8")));
      dataOutputStream.flush();
      dataOutputStream.writeBytes("exit\n");
      dataOutputStream.flush();
      process.waitFor();
      //读取命令执行结果
      bufferedReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
      String msg = "";
      String line = bufferedReader.readLine();
      while (line != null) {
        msg += line;
      }
      if (!msg.contains("Failure")) {
        isInstall = true;
      }
    } catch (IOException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      try {
        //不管任何情况 需要关闭输入输出流
        if (dataOutputStream != null) {
          dataOutputStream.close();
        }
        if (bufferedReader != null) {
          bufferedReader.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return isInstall;
  }


}
