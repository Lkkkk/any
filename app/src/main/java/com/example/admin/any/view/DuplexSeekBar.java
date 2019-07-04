package com.example.admin.any.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by liukai on 2018/3/9.
 * 自定义双向滑动进度条
 */

public class DuplexSeekBar extends View {

  private Context context;
  private Drawable hasScrollBarBg;//滑动条滑动后背景图
  private Drawable notScrollBarBg;//滑动条滑动前背景图
  private Drawable mScrollStart;//
  private Drawable mScrollEnd;

  public DuplexSeekBar(Context context) {
    this(context,null);
  }

  public DuplexSeekBar(Context context, @Nullable AttributeSet attrs) {
    this(context, attrs,0);
  }

  public DuplexSeekBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    this.context = context;
  }
}
