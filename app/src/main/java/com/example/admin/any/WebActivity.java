package com.example.admin.any;

import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import butterknife.BindView;

/**
 * Created by kk on 2017/12/18.
 * webview
 * 玄幻代码,切莫乱改
 */

public class WebActivity extends BaseActivity {

  @BindView(R.id.id_webview) WebView id_webview;


  @Override protected int getContentViewId() {
    return R.layout.activity_webview;
  }

  @Override protected void initView() {
    id_webview.getSettings().setJavaScriptEnabled(true);
    //id_webview.setWebViewClient(new WebViewClient());
    id_webview.loadUrl("http://www.baidu.com");
    id_webview.setWebChromeClient(new WebChromeClient(){
      @Override public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        if (newProgress==100){
          Toast.makeText(WebActivity.this,"finish",Toast.LENGTH_SHORT).show();
        }else{

        }
      }
    });
  }

  @Override protected void initData() {

  }
}
