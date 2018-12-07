package com.example.peng.myapplicationfive;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UncaughtHandler implements Thread.UncaughtExceptionHandler{

    private static UncaughtHandler instance;
    private Context mContext;

    public static UncaughtHandler getInstance(Context context){
        if (instance==null){
            synchronized (UncaughtHandler.class){
                instance=new UncaughtHandler(context);
            }
        }
        return instance;
    }
    public UncaughtHandler(Context context) {
        init(context);
    }

    public void init(Context context) {
        Thread.setDefaultUncaughtExceptionHandler(this);
        mContext=context.getApplicationContext();
    }


    @Override
    public void uncaughtException(Thread t, Throwable e) {
        try {
            Preservation(e);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void Preservation(Throwable throwable) throws Exception {
        //判断sb
        //卡的状态是否挂载状态
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
            return;
        }
        //根据管理者获取手机信息
        PackageManager manager=mContext.getPackageManager();
        PackageInfo info=manager.getPackageInfo(mContext.getPackageName(),PackageManager.GET_ACTIVITIES);

        //获取手机版本的信息
        String versionName=info.versionName;
        int versionCode = info.versionCode;

        int sdkInt = Build.VERSION.SDK_INT;
        //Android版本号
        String release=Build.VERSION.RELEASE;
        //手机的型号
        String mobile=Build.MODEL;
        //手机制造商
        String mobileName=Build.MANUFACTURER;

        //存储到sd中
        String path=Environment.getDownloadCacheDirectory().getAbsolutePath();

        //时间
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");

        String time=simpleDateFormat.format(new Date());
        //找到文件的路径
        File f=new File(path,"exception");
        f.mkdirs();

        File file=new File(f.getAbsolutePath(),"exception"+time+".txt");
        //判断问价您是否存在，如果不存在的话则创建文件
        if (!file.exists()){
            file.createNewFile();
        }
        String data="\nMobile:"+mobile+"\nMobileName:"+mobileName+"\nSDK版本:"+sdkInt+"\n版本名称:"+versionName+"\n版本号:"+versionCode+"\nAndroid版本号:"+release+"\n异常信息:"+throwable.toString();


        byte[] bytes=data.trim().getBytes();
        FileOutputStream fileOutputStream=new FileOutputStream(file);
        fileOutputStream.write(bytes,0,bytes.length);
        fileOutputStream.flush();
        fileOutputStream.close();

        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
}
