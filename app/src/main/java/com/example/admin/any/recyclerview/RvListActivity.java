package com.example.admin.any.recyclerview;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.example.admin.any.BaseActivity;
import com.example.admin.any.MainActivity;
import com.example.admin.any.R;

/**
 * Created by kk on 2018/6/11.
 * recyclerview相关功能列表
 */

public class RvListActivity extends BaseActivity {

  @Override protected int getContentViewId() {
    return R.layout.activity_rv_list;
  }

  @Override protected void initView() {

  }

  @Override protected void initData() {

  }

  @OnClick({R.id.base_tv})
  public void OnClick(View v){
    switch (v.getId()){
      case R.id.base_tv:
        Intent recyclerIntent = new Intent();
        recyclerIntent.setClass(RvListActivity.this, RVActivity.class);
        toOtherActivity(recyclerIntent);
        break;
      case R.id.refresh_tv:
        break;
    }
  }

}
