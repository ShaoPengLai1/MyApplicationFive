package com.example.text_1205.utils;

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

public class Global_capture implements Thread.UncaughtExceptionHandler{

    private static Global_capture instance;
    private Context mContext;

    /**
     * 写一个单例
     */
    public static Global_capture getInstance(Context context){
        if (instance==null){
            synchronized (Global_capture.class){
                instance=new Global_capture(context);
            }
        }
        return instance;
    }

    public Global_capture(Context context) {
        init(context);
    }

    public void init(Context context) {
        /**
         * 获取默认的系统异常捕获器
         * 把当前的crash捕获器设置成默认的crash捕获器
         */
        Thread.setDefaultUncaughtExceptionHandler(this);
        mContext=context.getApplicationContext();
    }


    /**
     * 把抛出的异常保存到SD卡
     * @param t
     * @param e
     */

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        try {
            Save_localSD(e);
        } catch (Exception e1){
            e1.printStackTrace();
        }
    }

    /**
     * 保存到sd卡
     * @param
     */
    private void Save_localSD(Throwable throwable) throws Exception {
        //判断sd卡状态
        //MEDIA_MOUNTED 存储媒体已经挂载，并且挂载点可读/写
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            //退出
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
            return;
        }
        /**
         * 获取手机的一些信息
         */
        PackageManager manager=mContext.getPackageManager();
        PackageInfo info=manager.getPackageInfo(mContext.getPackageName(),PackageManager.GET_ACTIVITIES);

        /**
         * 获取手机版本的一些信息
         */
        String versionName = info.versionName;
        int versionCode = info.versionCode;

        int sdkInt = Build.VERSION.SDK_INT;

        /**
         * Android版本号
         */
        String release = Build.VERSION.RELEASE;

        /**
         * 手机型号
         */
        String model = Build.MODEL;

        /**
         * 手机制造商
         */
        String modelName = Build.MANUFACTURER;

        /**
         * 储存到sd卡中
         */
        String path=Environment.getDownloadCacheDirectory().getAbsolutePath();

        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        String time = simpleDateFormat.format(new Date());

        /**
         * 找到文件夹路径，创建exception文件夹
         */
        File file=new File(path,"Exception");
        file.mkdirs();

        /**
         * 找到文件夹路径，查找exception + time的txt文件夹
         */
        File file1=new File(file.getAbsolutePath(),"Exception"+time+".txt");

        /**
         * 判断文件是否存在，如果不存在就创建
         */
        if (!file1.exists()){
            file1.createNewFile();
        }

        String data="\nMobile型号:"+model+"\nModelName:"+modelName+"\nSDK版本:"+sdkInt+"\n版本名称:"+
                versionName+"\n版本号:"+versionCode+"\nAndroid版本号:"+release+"\n异常信息:"+throwable.toString();


        Log.i("TAG",data);
        /**
         * 写入文件
         */
        byte[] bytes=data.trim().getBytes();
        FileOutputStream fileOutputStream=new FileOutputStream(file1);
        /**
         * 开始写数据到这个文件中
         */
        fileOutputStream.write(bytes,0,bytes.length);
        //停止
        fileOutputStream.flush();
        //关闭流
        fileOutputStream.close();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }


}
