package com.example.admin.any;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.app.NotificationCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import butterknife.BindView;
import com.example.admin.any.dialog.SampleActivity;
import com.example.admin.any.lock.ACache;
import com.example.admin.any.lock.CreateGestureActivity;
import com.example.admin.any.lock.GestureLoginActivity;
import com.example.admin.any.recyclerview.RVActivity;
import com.example.admin.any.recyclerview.RvListActivity;
import com.example.admin.any.service.AidlRemoteService;
import com.example.admin.any.silentinstall.SilentActivity;
import com.example.admin.any.wallerpaper.VideoWallerActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener {

  @BindView(R.id.recycler_bt) Button recycler_bt;
  @BindView(R.id.glide_bt) Button glide_bt;
  @BindView(R.id.immesive_bt) Button immesive_bt;
  @BindView(R.id.silent_bt) Button silent_bt;
  @BindView(R.id.waller_bt) Button waller_bt;
  @BindView(R.id.file_bt) Button file_bt;
  @BindView(R.id.bt_bindService) Button bt_bindService;
  @BindView(R.id.bt_startMethod) Button bt_startMethod;
  @BindView(R.id.bt_dialog) Button bt_dialog;
  @BindView(R.id.bt_lock) Button bt_lock;
  @BindView(R.id.bt_pdf) Button bt_pdf;

  private static final String TAG = "MainActivity";
  private bookAidl mbookAidl;
  private ServiceConnection mServiceConnection;
  private boolean isServiceBind;
  private ACache aCache;

  @Override protected int getContentViewId() {
    return R.layout.activity_main;
  }

  protected void initView() {
    recycler_bt.setOnClickListener(this);
    glide_bt.setOnClickListener(this);
    immesive_bt.setOnClickListener(this);
    silent_bt.setOnClickListener(this);
    waller_bt.setOnClickListener(this);
    file_bt.setOnClickListener(this);
    bt_bindService.setOnClickListener(this);
    bt_startMethod.setOnClickListener(this);
    bt_dialog.setOnClickListener(this);
    bt_lock.setOnClickListener(this);
    bt_pdf.setOnClickListener(this);

    mServiceConnection = new ServiceConnection() {
      @Override public void onServiceConnected(ComponentName name, IBinder service) {
        mbookAidl = bookAidl.Stub.asInterface(service);
        isServiceBind = true;
      }

      @Override public void onServiceDisconnected(ComponentName name) {
        mbookAidl = null;
        isServiceBind = false;
      }
    };
    aCache = ACache.get(MainActivity.this);
  }

  @Override protected void initData() {

  }

  @Override public void onClick(View v) {
    switch (v.getId()) {
      case R.id.recycler_bt:
        Intent recyclerIntent = new Intent();
        recyclerIntent.setClass(MainActivity.this, RvListActivity.class);
        toOtherActivity(recyclerIntent);
        break;
      case R.id.glide_bt:
        Intent glideIntent = new Intent();
        glideIntent.setClass(MainActivity.this, GlideTestActivity.class);
        toOtherActivity(glideIntent);
        break;
      case R.id.immesive_bt:
        Intent immerIntent = new Intent();
        immerIntent.setClass(MainActivity.this, ImmersiveActivity.class);
        toOtherActivity(immerIntent);
        break;
      case R.id.silent_bt:
        Intent silentIntent = new Intent();
        silentIntent.setClass(MainActivity.this, SilentActivity.class);
        toOtherActivity(silentIntent);
        break;
      case R.id.waller_bt:
        Intent wallerIntent = new Intent();
        wallerIntent.setClass(MainActivity.this, VideoWallerActivity.class);
        toOtherActivity(wallerIntent);
        break;
      case R.id.file_bt:
        Intent fileIntent = new Intent();
        fileIntent.setClass(MainActivity.this, FileTestActivity.class);
        toOtherActivity(fileIntent);
        break;
      case R.id.bt_bindService:
        //启动绑定服务
        Intent intent = new Intent(MainActivity.this, AidlRemoteService.class);
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
        break;
      case R.id.bt_startMethod:
        //开始aidl中的方法
        try {
          mbookAidl.testMethod();
        } catch (RemoteException e) {
          e.printStackTrace();
          Toast.makeText(MainActivity.this, "服务被异常杀死，请重新开启。", Toast.LENGTH_SHORT).show();
        }
        break;
      case R.id.bt_dialog:
        //自定义dialog控件
        Intent dialogIntent = new Intent();
        dialogIntent.setClass(MainActivity.this, SampleActivity.class);
        toOtherActivity(dialogIntent);
        break;
      case R.id.bt_lock:
        String gesturePassword = aCache.getAsString("GesturePassword");
        if(gesturePassword == null || "".equals(gesturePassword)) {
          Intent creatIntent = new Intent(MainActivity.this, CreateGestureActivity.class);
          startActivity(creatIntent);
          finish();
        } else {
          Intent lockLoginIntent = new Intent(MainActivity.this, GestureLoginActivity.class);
          startActivity(lockLoginIntent);
          finish();
        }
        break;
      case R.id.bt_pdf:
        //打开pdf
        Intent pdfIntent = new Intent();
        pdfIntent.setClass(MainActivity.this, PdfTestActivity.class);
        toOtherActivity(pdfIntent);
        break;
    }
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    if (isServiceBind) {
      unbindService(mServiceConnection);
    }
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.item_add:
        Intent intent = new Intent(this, NotificationActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(this).setContentTitle("快去拍照吧")
            .setContentText("多拍点裸照")
            .setWhen(System.currentTimeMillis())
            .setSmallIcon(R.mipmap.ic_launcher)
            .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.laoge))
            .setContentIntent(pi)
            .setAutoCancel(true)//点击之后通知消失
            .setVibrate(new long[] { 0, 1000, 1000, 1000 })//震动,需要申明权限
            .setLights(Color.RED, 1000, 1000)//led灯 颜色 亮起时间 熄灭时间
            .setPriority(NotificationCompat.PRIORITY_MAX)//设置通知重要登记
            //.setDefaults(NotificationCompat.DEFAULT_ALL)//默认效果
            .build();
        manager.notify(1, notification);
        //Toast.makeText(MainActivity.this,"点你个鸡儿",Toast.LENGTH_SHORT).show();
        break;
      case R.id.item_remove:
        Intent webIntent = new Intent();
        webIntent.setClass(MainActivity.this, WebActivity.class);
        toOtherActivity(webIntent);
        //Toast.makeText(MainActivity.this,"鸡儿被你点",Toast.LENGTH_SHORT).show();
        break;
      default:
    }
    return true;
  }

  @Override public void onBackPressed() {
    super.onBackPressed();
    ActivityCollector.removeAll();
  }
}
