package com.example.admin.any;

import android.Manifest;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import java.io.File;
import java.io.IOException;

/**
 * Created by kk on 2017/12/8.
 * 通知响应
 * 玄幻代码,切莫乱改
 */

public class NotificationActivity extends BaseActivity {

  @BindView(R.id.iv_photo) ImageView iv_photo;
  private Uri imageUri;
  private static final String TAG = "NotificationActivity";
  private static final String STATUS = "com.example.admin.any.fileprovider";
  private static final int TAKE_PHOTO = 1;
  private static final int TAKE_ALBUM = 2;

  @Override protected int getContentViewId() {
    return R.layout.activity_notification;
  }

  @Override protected void initView() {
  }

  @Override protected void initData() {

  }

  @OnClick({ R.id.bt_photo, R.id.bt_album, R.id.bt_start, R.id.bt_pause, R.id.bt_stop })
  public void myClick(View v) {
    switch (v.getId()){
      case R.id.bt_photo:
        openPhoto();
        break;
      case R.id.bt_album:
        // 先动态申请权限
        if (ContextCompat.checkSelfPermission(NotificationActivity.this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
          ActivityCompat.requestPermissions(NotificationActivity.this,
              new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE }, 1);
        } else {
          openAlbum();
        }
        break;
      case R.id.bt_start:
        break;
      case R.id.bt_pause:
        break;
      case R.id.bt_stop:
        break;
    }
  }

  private void openPhoto() {
    //file对象储存拍照后保存的图片
    File file = new File(getExternalCacheDir(), "output_image.jpg");
    try {
      if (file.exists()) {
        file.delete();
      }
      file.createNewFile();
    } catch (IOException e) {
      e.printStackTrace();
    }
    if (Build.VERSION.SDK_INT >= 24) {
      imageUri = FileProvider.getUriForFile(NotificationActivity.this, STATUS, file);
    } else {
      imageUri = Uri.fromFile(file);
    }
    //启动相机
    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
    startActivityForResult(intent, TAKE_PHOTO);
  }

  private void openAlbum() {
    Intent albumIntent = new Intent("android.intent.action.GET_CONTENT");
    albumIntent.setType("iamge/*");
    startActivityForResult(albumIntent, TAKE_ALBUM);
  }

  @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    switch (requestCode) {
      case 1:
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          openAlbum();
        } else {
          Toast.makeText(NotificationActivity.this, "permission failure", Toast.LENGTH_SHORT)
              .show();
        }
        break;
    }
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    switch (requestCode) {
      case TAKE_PHOTO:
        if (resultCode == RESULT_OK) {
          Glide.with(NotificationActivity.this).load(imageUri).into(iv_photo);
        }
        break;
      case TAKE_ALBUM:
        if (resultCode == RESULT_OK) {
          if (Build.VERSION.SDK_INT >= 19) {
            //4.4版本以上的用这个处理图片,因为对返回的uri做了封装
            handImageOnKitKat(data);
          } else {
            handImageBeforeKitKat(data);
          }
        }
        break;
    }
  }

  private void handImageOnKitKat(Intent data) {
    String imagePath = null;
    Uri uri = data.getData();
    if (DocumentsContract.isDocumentUri(NotificationActivity.this, uri)) {
      //如果是document类型的uri
      String docId = DocumentsContract.getDocumentId(uri);
      if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
        String id = docId.split(":")[1];//解析出数字格式的id
        String selection = MediaStore.Images.Media._ID + "=" + id;
        imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
      } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
        Uri contentUri =
            ContentUris.withAppendedId(uri.parse("content://downloads/public_downloads"),
                Long.valueOf(docId));
        imagePath = getImagePath(contentUri, null);
      }
    } else if ("content".equalsIgnoreCase(uri.getScheme())) {
      //如果是content类型的uri,使用普通方式
      imagePath = getImagePath(uri, null);
    } else if ("file".equalsIgnoreCase(uri.getScheme())) {
      //如果是file类型的uri,直接获取图片路径
      imagePath = uri.getPath();
    }
    displayImage(imagePath);
  }

  private void handImageBeforeKitKat(Intent data) {
    Uri uri = data.getData();
    String imagePath = getImagePath(uri, null);
    displayImage(imagePath);
  }

  private String getImagePath(Uri uri, String selection) {
    String path = null;
    Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
    if (cursor != null) {
      if (cursor.moveToFirst()) {//遍历媒体中的图片文件
        path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
      }
      cursor.close();
    }
    return path;
  }

  private void displayImage(String path) {
    //通过连接加载图片
    if (path != null) {
      Glide.with(NotificationActivity.this).load(path).into(iv_photo);
    } else {
      Toast.makeText(NotificationActivity.this, "failed to get image", Toast.LENGTH_SHORT).show();
    }
  }

}
