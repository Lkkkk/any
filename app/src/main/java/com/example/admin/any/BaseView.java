package com.example.admin.any;

import com.hannesdorfmann.mosby3.mvp.MvpView;

/**
 * Created by kk on 2018/1/26.
 * 描述：lce模式中基础视图接口
 * 玄幻代码,切莫乱改
 */

public interface BaseView<M> extends MvpView {

  /**
   * 显示加载视图，加载视图的id必须为R.id.loadingView
   */
  public void showLoading(boolean pullToRefresh);

  /**
   * 显示内容视图，内容视图的id必须为R.id.contentView
   *
   * <b>The content view must have the id = R.id.contentView</b>
   */
  public void showContent();

  /**
   * 显示错误视图，错误视图必须是TextView，id必须是R.id.errorView
   */
  public void showError(Throwable e, boolean pullToRefresh);

  /**
   * 设置将在showContent()中显示的数据
   */
  public void setData(M data);

  /**
   * 加载数据，此方法中常需要调用Presenter的对应方法。因此此方法不可在Presenter
   * 中使用，避免循环调用。
   * 参数pullToRefresh代表此次加载是否由下拉刷新触发。
   */
  public void loadData(boolean pullToRefresh);
}
