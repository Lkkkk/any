package com.example.admin.any;

import android.app.Activity;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kk on 2017/12/6.
 */

public class ActivityCollector {
  public static List<Activity> activityList = new ArrayList<>();

  public static void addActivity(Activity activity) {
    activityList.add(activity);
  }

  public static void removeActivity(Activity activity) {
    activityList.remove(activity);
  }

  /*
  * 销毁所有activity，彻底退出应用
  * */
  public static void removeAll() {
    for (Activity activity : activityList) {
      if (!activity.isFinishing()) {
        activity.finish();
      }
    }
  }
}
