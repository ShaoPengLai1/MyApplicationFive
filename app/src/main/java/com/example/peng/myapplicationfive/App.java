package com.example.peng.myapplicationfive;

import android.app.Application;

import com.tencent.bugly.crashreport.CrashReport;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        UncaughtHandler.getInstance(getApplicationContext()).init(getApplicationContext());
        CrashReport.initCrashReport(getApplicationContext(),"1180d09b51",true);
    }
}
