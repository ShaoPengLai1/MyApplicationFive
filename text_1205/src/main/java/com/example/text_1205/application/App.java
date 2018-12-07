package com.example.text_1205.application;

import android.app.Application;
import android.graphics.Bitmap;

import com.example.text_1205.R;
import com.example.text_1205.utils.Global_capture;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //全局配置
        Global_capture.getInstance(getApplicationContext()).init(getApplicationContext());
        ImageLoader.getInstance().init(new ImageLoaderConfiguration.Builder(this)

                .memoryCacheSizePercentage(10)
                .diskCacheSize(50*1024*1024)
                .defaultDisplayImageOptions(new DisplayImageOptions.Builder()
                        .bitmapConfig(Bitmap.Config.RGB_565)
                        .cacheOnDisk(true)
                        .cacheInMemory(true)
                        .showImageOnFail(R.mipmap.ic_launcher)
                        .showImageOnLoading(R.mipmap.ic_launcher)
                        .showImageForEmptyUri(R.mipmap.ic_launcher)
                        .build())
                .build());
    }
}
