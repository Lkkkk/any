package com.example.admin.any.lock;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.OnClick;
import com.example.admin.any.BaseActivity;
import com.example.admin.any.R;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kk on 2018/4/12.
 * 手机解锁
 */

public class CreateGestureActivity extends BaseActivity {

  @BindView(R.id.lockPatterIndicator) LockPatternIndicator lockPatternIndicator;
  @BindView(R.id.lockPatternView) LockPatternView lockPatternView;
  @BindView(R.id.resetBtn) Button resetBtn;
  @BindView(R.id.messageTv) TextView messageTv;
  private ACache aCache;
  private static final long DELAYTIME = 600L;
  private static final String TAG = "CreateGestureActivity";
  private List<LockPatternView.Cell> mChosenPattern = null;

  @Override protected int getContentViewId() {
    return R.layout.activity_lock;
  }

  @Override protected void initView() {
    aCache = ACache.get(CreateGestureActivity.this);
    lockPatternView.setOnPatternListener(patternListener);
  }

  @Override protected void initData() {

  }

  /**
   * 手势监听
   */
  private LockPatternView.OnPatternListener patternListener =
      new LockPatternView.OnPatternListener() {

        @Override public void onPatternStart() {
          lockPatternView.removePostClearPatternRunnable();
          //updateStatus(Status.DEFAULT, null);
          lockPatternView.setPattern(LockPatternView.DisplayMode.DEFAULT);
        }

        @Override public void onPatternComplete(List<LockPatternView.Cell> pattern) {
          //Log.e(TAG, "--onPatternDetected--");
          if (mChosenPattern == null && pattern.size() >= 4) {
            mChosenPattern = new ArrayList<LockPatternView.Cell>(pattern);
            updateStatus(Status.CORRECT, pattern);
          } else if (mChosenPattern == null && pattern.size() < 4) {
            updateStatus(Status.LESSERROR, pattern);
          } else if (mChosenPattern != null) {
            if (mChosenPattern.equals(pattern)) {
              updateStatus(Status.CONFIRMCORRECT, pattern);
            } else {
              updateStatus(Status.CONFIRMERROR, pattern);
            }
          }
        }
      };

  /**
   * 更新状态
   */
  private void updateStatus(Status status, List<LockPatternView.Cell> pattern) {
    messageTv.setTextColor(getResources().getColor(status.colorId));
    messageTv.setText(status.strId);
    switch (status) {
      case DEFAULT:
        lockPatternView.setPattern(LockPatternView.DisplayMode.DEFAULT);
        break;
      case CORRECT:
        updateLockPatternIndicator();
        lockPatternView.setPattern(LockPatternView.DisplayMode.DEFAULT);
        break;
      case LESSERROR:
        lockPatternView.setPattern(LockPatternView.DisplayMode.DEFAULT);
        break;
      case CONFIRMERROR:
        lockPatternView.setPattern(LockPatternView.DisplayMode.ERROR);
        lockPatternView.postClearPatternRunnable(DELAYTIME);
        break;
      case CONFIRMCORRECT:
        saveChosenPattern(pattern);
        lockPatternView.setPattern(LockPatternView.DisplayMode.DEFAULT);
        setLockPatternSuccess();
        break;
    }
  }

  /**
   * 更新 Indicator
   */
  private void updateLockPatternIndicator() {
    if (mChosenPattern == null) return;
    lockPatternIndicator.setIndicator(mChosenPattern);
  }

  /**
   * 重新设置手势
   */
  @OnClick(R.id.resetBtn) void resetLockPattern() {
    mChosenPattern = null;
    lockPatternIndicator.setDefaultIndicator();
    updateStatus(Status.DEFAULT, null);
    lockPatternView.setPattern(LockPatternView.DisplayMode.DEFAULT);
  }

  /**
   * 成功设置了手势密码(跳到首页)
   */
  private void setLockPatternSuccess() {
    Toast.makeText(this, "create gesture success", Toast.LENGTH_SHORT).show();
  }

  /**
   * 保存手势密码
   */
  private void saveChosenPattern(List<LockPatternView.Cell> cells) {
    byte[] bytes = LockPatternUtil.patternToHash(cells);
    aCache.put("GesturePassword", bytes);
  }

  private enum Status {
    //默认的状态，刚开始的时候（初始化状态）
    DEFAULT(R.string.create_gesture_default, R.color.grey_a5a5a5), //第一次记录成功
    CORRECT(R.string.create_gesture_correct,
        R.color.grey_a5a5a5), //连接的点数小于4（二次确认的时候就不再提示连接的点数小于4，而是提示确认错误）
    LESSERROR(R.string.create_gesture_less_error, R.color.red_f4333c), //二次确认错误
    CONFIRMERROR(R.string.create_gesture_confirm_error, R.color.red_f4333c), //二次确认正确
    CONFIRMCORRECT(R.string.create_gesture_confirm_correct, R.color.grey_a5a5a5);

    private Status(int strId, int colorId) {
      this.strId = strId;
      this.colorId = colorId;
    }

    private int strId;
    private int colorId;
  }
}
