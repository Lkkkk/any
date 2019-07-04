package com.example.admin.any;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import butterknife.ButterKnife;

/**
 * Created by kk on 2017/11/29.
 */

public abstract class BaseActivity extends AppCompatActivity {

  protected abstract int getContentViewId();

  protected abstract void initView();

  protected abstract void initData();

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(getContentViewId());
    ButterKnife.bind(this);
    ActivityCollector.addActivity(this);
    initView();
    initData();
  }

  /**
   * 重载toOtherActivity
   *
   * @param cls 跳转activity
   */
  protected <T> void toOtherActivity(Class<?> cls, Bundle bdl) {
    Intent intent = new Intent();
    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
    intent.setClass(getBaseContext(), cls);
    intent.putExtras(bdl);
    startActivity(intent);
  }

  /**
   * 重载toOtherActivity
   *
   * @param cls 跳转activity
   */
  protected <T> void toOtherActivity(Class<?> cls) {
    Intent intent = new Intent();
    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
    intent.setClass(getBaseContext(), cls);
    startActivity(intent);
  }

  /**
   * 重载toOtherActivity
   *
   * @param intent 跳转activity
   */
  protected <T> void toOtherActivity(Intent intent) {
    startActivity(intent);
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    ActivityCollector.removeActivity(this);
  }
}
