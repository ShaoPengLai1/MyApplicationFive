package com.example.text_1205.application;

import android.app.Application;
import android.graphics.Bitmap;

import com.example.text_1205.R;
import com.example.text_1205.utils.Global_capture;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //全局配置
        UMConfigure.init(this,"5a12384aa40fa3551f0001d1","umeng",UMConfigure.DEVICE_TYPE_PHONE,"");
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
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
