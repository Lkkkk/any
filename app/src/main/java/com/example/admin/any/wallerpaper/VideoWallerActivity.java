package com.example.admin.any.wallerpaper;

import android.view.View;
import android.widget.Button;
import butterknife.BindView;
import com.example.admin.any.BaseActivity;
import com.example.admin.any.R;

/**
 * Created by kk on 2017/12/1.
 * 视频墙纸
 */

public class VideoWallerActivity extends BaseActivity {

  @BindView(R.id.waller_bt) Button waller_bt;

  @Override protected int getContentViewId() {
    return R.layout.activity_waller;
  }

  @Override protected void initView() {
    waller_bt.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        VideoWallerService.setToWallPaper(VideoWallerActivity.this);
      }
    });
  }

  @Override protected void initData() {
  }
}
