package com.example.admin.any.silentinstall;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import com.example.admin.any.BaseActivity;
import com.example.admin.any.R;
import java.io.File;

/**
 * Created by kk on 2017/11/30.
 * 静默安装
 */

public class SilentActivity extends BaseActivity implements View.OnClickListener{

  @BindView(R.id.select_bt) Button select_bt;
  @BindView(R.id.fast_bt) Button fast_bt;
  @BindView(R.id.file_name) TextView file_name;
  private String apkPath;

  @Override protected int getContentViewId() {
    return R.layout.activity_silentinstall;
  }

  @Override protected void initView() {
    select_bt.setOnClickListener(this);
    fast_bt.setOnClickListener(this);
  }

  @Override protected void initData() {

  }

  @Override public void onClick(View v) {
    switch (v.getId()){
      case R.id.select_bt:
        Intent fileIntent = new Intent();
        fileIntent.setClass(this,FileExploreActivity.class);
        startActivityForResult(fileIntent,0);
        break;
      case R.id.fast_bt:
          fastInstall();
        break;
    }
  }


  private void fastInstall(){
    if (!isRoot()){
      Toast.makeText(SilentActivity.this,"手机未root,无法急速安装",Toast.LENGTH_SHORT).show();
      return;
    }
    if (file_name.getText().toString().equals("")){
      Toast.makeText(SilentActivity.this,"请选择安装包",Toast.LENGTH_SHORT).show();
      return;
    }
    fast_bt.setText("安装中");
    new Thread(new Runnable() {
      @Override public void run() {
        SilentInstall silentInstall = new SilentInstall();
        final boolean install = silentInstall.install(apkPath);
        runOnUiThread(new Runnable() {
          @Override public void run() {
            if (install) {
              Toast.makeText(SilentActivity.this, "安装成功！", Toast.LENGTH_SHORT).show();
            } else {
              Toast.makeText(SilentActivity.this, "安装失败！", Toast.LENGTH_SHORT).show();
            }
            fast_bt.setText("急速安装");
          }
        });
      }
    });

  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode==0&&resultCode==RESULT_OK){
      apkPath = data.getStringExtra("apk_path");
      file_name.setText(apkPath);
    }
  }

  /**
   * 判断手机是否拥有Root权限。
   * @return 有root权限返回true，否则返回false。
   */
  public boolean isRoot() {
    boolean bool = false;
    try {
      bool = new File("/system/bin/su").exists() || new File("/system/xbin/su").exists();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return bool;
  }
}
