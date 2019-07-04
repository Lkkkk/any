package com.example.admin.any;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by kk on 2017/12/6.
 * 文件操作
 */

public class FileTestActivity extends AppCompatActivity {

  private static final String TAG = "FileTestActivity";
  private EditText et_content;
  private Button bt_save;
  private Button bt_share;
  private TextView tv_userinfo;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_file_test);
    initView();
    if (savedInstanceState != null) {
      et_content.setText(savedInstanceState.getString("content"));
      Log.d(TAG, "onCreate: savedInstanceState");
    } else {
      if (!loadData().equals("")) {
        et_content.setText(loadData());
        et_content.setSelection(loadData().length());//将输入光标移到内容末尾，以便继续输入
      }
    }
    loadDataFromShare();
  }

  private void initView() {
    et_content = (EditText) findViewById(R.id.et_content);
    bt_save = (Button) findViewById(R.id.bt_save);
    bt_share = (Button) findViewById(R.id.bt_share);
    tv_userinfo = (TextView) findViewById(R.id.tv_userinfo);
    bt_save.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        String content = et_content.getText().toString();
        if (!content.equals("")) {
          saveData(content);
        }
      }
    });
    bt_share.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        String content = et_content.getText().toString();
        if (!content.equals("")) {
          SharedPreferences.Editor editor = getSharedPreferences("shareData", MODE_PRIVATE).edit();
          editor.putString("name", "超帅大帅哥kk");
          editor.putInt("age", 25);
          editor.putBoolean("married", false);
          editor.apply();
        }
      }
    });
  }

  private void saveData(String content) {
    FileOutputStream out = null;
    BufferedWriter writer = null;
    try {
      out = openFileOutput("mimi", Context.MODE_PRIVATE);
      writer = new BufferedWriter(new OutputStreamWriter(out));
      writer.write(content);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (writer != null) {
        try {
          writer.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  private String loadData() {
    FileInputStream input = null;
    BufferedReader reader = null;
    StringBuilder builder = new StringBuilder();
    try {
      input = openFileInput("mimi");
      reader = new BufferedReader(new InputStreamReader(input));
      String line = "";
      while ((line = reader.readLine()) != null) {
        builder.append(line);
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (reader != null) {
        try {
          reader.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return builder.toString();
  }

  private void loadDataFromShare() {
    SharedPreferences sharedPreferences = getSharedPreferences("shareData", MODE_PRIVATE);
    String name = sharedPreferences.getString("name", "kk");
    int age = sharedPreferences.getInt("age", 0);
    boolean married = sharedPreferences.getBoolean("married", false);
    tv_userinfo.setText("姓名:"+name +" 年龄:"+ age +" 是否结婚： "+ married);
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    String content = et_content.getText().toString();
    if (!content.equals("")) {
      saveData(content);
    }
  }

  @Override public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
    super.onSaveInstanceState(outState, outPersistentState);
    outState.putString("content", et_content.getText().toString());
    Log.d(TAG, "onSaveInstanceState: ");
  }
}
