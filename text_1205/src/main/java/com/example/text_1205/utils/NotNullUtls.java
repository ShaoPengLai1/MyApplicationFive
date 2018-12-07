package com.example.text_1205.utils;

import android.text.TextUtils;

public class NotNullUtls {
    private static NotNullUtls instance;

    public static NotNullUtls getInstance(){
        if (instance==null){
            instance=new NotNullUtls();
        }
        return instance;
    }
    /**
     * 判断是否为空
     */
    public boolean isNonNull(String mName,String mPass){
        return !TextUtils.isEmpty(mName)&&!TextUtils.isEmpty(mPass);
    }
}
