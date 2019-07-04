package com.example.admin.any;

import android.graphics.Canvas;
import butterknife.BindView;
import com.lidong.pdf.PDFView;
import com.lidong.pdf.listener.OnDrawListener;
import com.lidong.pdf.listener.OnLoadCompleteListener;
import com.lidong.pdf.listener.OnPageChangeListener;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by kk on 2018/4/25.
 */

public class PdfTestActivity extends BaseActivity
    implements OnPageChangeListener, OnLoadCompleteListener, OnDrawListener {

  @BindView(R.id.pdf_view) PDFView pdf_view;
  private String pdfName;

  @Override protected int getContentViewId() {
    return R.layout.activity_pdf_test;
  }

  @Override protected void initView() {
    final String path = "http://zd-flat-test-oss.oss-cn-shenzhen.aliyuncs.com/file_1523436382973.pdf";
    URL url = null;
    try {
      url = new URL(path);
      String urlPath = url.getPath();
      pdfName = urlPath.substring(urlPath.lastIndexOf("/") + 1);
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
    pdf_view.setSwipeVertical(true);
    pdf_view.fileFromLocalStorage(this,
        this,
        this, path, pdfName);
  }

  @Override protected void initData() {

  }

  @Override
  public void onLayerDrawn(Canvas canvas, float pageWidth, float pageHeight, int displayedPage) {

  }

  @Override public void loadComplete(int nbPages) {

  }

  @Override public void onPageChanged(int page, int pageCount) {

  }
}
