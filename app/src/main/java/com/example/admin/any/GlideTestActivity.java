package com.example.admin.any;

import android.widget.ImageView;
import butterknife.BindView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;


public class GlideTestActivity extends BaseActivity {

  @BindView(R.id.image) ImageView image;
  private static final String TAG = "GlideTestActivity";

  @Override protected int getContentViewId() {
    return R.layout.activity_glide_test;
  }

  @Override protected void initView() {

  }

  @Override protected void initData() {
    String url ="http://p1.pstatp.com/large/166200019850062839d3";
    RequestOptions ro = new RequestOptions().centerCrop()
        .placeholder(R.mipmap.loading)//补位图
        .error(R.mipmap.ic_launcher)//加载失败图
        .priority(Priority.HIGH)//
        .diskCacheStrategy(DiskCacheStrategy.NONE);//缓存
    Glide.with(this).load(url).apply(ro).into(image);
  }
}
