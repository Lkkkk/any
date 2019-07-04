package com.example.admin.any.wallerpaper;

import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;
import java.io.IOException;

/**
 * Created by kk on 2017/12/1.
 */

public class VideoWallerService extends WallpaperService {

  @Override public Engine onCreateEngine() {
    return new VideoEngine();
  }

  public static void setToWallPaper(Context context) {
    final Intent intent = new Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
    intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
        new ComponentName(context, VideoWallerService.class));
    context.startActivity(intent);
  }

  class VideoEngine extends Engine {
    private MediaPlayer mMediaPlayer;

    @Override public void onSurfaceCreated(SurfaceHolder holder) {
      super.onSurfaceCreated(holder);
      mMediaPlayer = new MediaPlayer();
      mMediaPlayer.setSurface(holder.getSurface());
      try {
        AssetManager assetMg = getApplicationContext().getAssets();
        AssetFileDescriptor fileDescriptor = assetMg.openFd("test1.mp4");
        mMediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(),
            fileDescriptor.getStartOffset(), fileDescriptor.getLength());
        mMediaPlayer.setLooping(true);
        mMediaPlayer.setVolume(0, 0);
        mMediaPlayer.prepare();
        mMediaPlayer.start();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    @Override
    public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
      super.onSurfaceChanged(holder, format, width, height);
    }

    @Override public void onSurfaceDestroyed(SurfaceHolder holder) {
      super.onSurfaceDestroyed(holder);
      //注意回收资源
      mMediaPlayer.release();
      mMediaPlayer = null;
    }

    @Override public void onVisibilityChanged(boolean visible) {
      super.onVisibilityChanged(visible);
      //桌面可见时才开始播放
      if (visible){
        mMediaPlayer.start();
      }else{
        mMediaPlayer.pause();
      }
    }
  }

}
