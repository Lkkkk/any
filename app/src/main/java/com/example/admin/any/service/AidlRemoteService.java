package com.example.admin.any.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.example.admin.any.bookAidl;

/**
 * Created by kk on 2017/12/29.
 * 描述:通过服务实现aidl中的方法
 * 玄幻代码,切莫乱改
 */

public class AidlRemoteService extends Service {

  //logt快捷生成tag
  private static final String TAG = "AidlRemoteService";
  private int x = 0;

  //如果找不到aidl文件  使用build中的 make project
  private final bookAidl.Stub mBinder = new bookAidl.Stub() {

    @Override
    public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble,
        String aString) throws RemoteException {

    }

    @Override public void testMethod() throws RemoteException {
      x++;
      Log.d(TAG, "testMethod: " + "this is myAIDLTest " + (x));
    }
  };

  @Override public IBinder onBind(Intent intent) {
    return mBinder;
  }
}
