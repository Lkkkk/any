package com.example.admin.any.silentinstall;

import android.content.Intent;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import butterknife.BindView;
import com.example.admin.any.BaseActivity;
import com.example.admin.any.R;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Admin on 2017/11/30.
 */

public class FileExploreActivity extends BaseActivity {

  @BindView(R.id.file_listview) ListView file_listview;
  private List<Map<String, Object>> fileList;
  private SimpleAdapter fileAdapter;
  private String rootPath = Environment.getExternalStorageDirectory().getPath();
  private String currentPath = rootPath;

  @Override protected int getContentViewId() {
    return R.layout.activity_file_explore;
  }

  @Override protected void initView() {
    fileList = new ArrayList<>();
    fileAdapter =
        new SimpleAdapter(this, fileList, R.layout.item_file, new String[] { "name", "img" },
            new int[] { R.id.name, R.id.img });
    file_listview.setAdapter(fileAdapter);
    file_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        currentPath = (String) fileList.get(position).get("currentPath");
        File file = new File(currentPath);
        if (file.isDirectory()) {
          requestFileData(currentPath);
        } else {
          Intent selectIntent = new Intent();
          selectIntent.putExtra("apk_path", file.getPath());
          setResult(RESULT_OK, selectIntent);
          finish();
        }
      }
    });
  }

  @Override protected void initData() {
    requestFileData(rootPath);
  }

  private void requestFileData(String path) {
    setTitle(path);
    File[] files = new File(path).listFiles();
    fileList.clear();
    if (files != null) {
      Map<String, Object> map = new HashMap<>();
      for (File file : files) {
        if (file.isDirectory()) {
          //  文件夹
          map.put("img", R.mipmap.laoge);
        } else {
          map.put("img", R.mipmap.fire);
        }
        map.put("name", file.getName());
        map.put("currentPath", file.getPath());
        fileList.add(map);
      }
    }
    fileAdapter.notifyDataSetChanged();
  }

  @Override public void onBackPressed() {
    if (rootPath.equals(currentPath)) {
      super.onBackPressed();
    } else {
      File file = new File(currentPath);
      currentPath = file.getParentFile().getPath();
      requestFileData(currentPath);
    }
  }
}
