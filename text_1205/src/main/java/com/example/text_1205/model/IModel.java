package com.example.text_1205.model;

import com.example.text_1205.callBack.MyCallBack;

public interface IModel {
    /**
     *数据展示
     */
    void getResult(String url,String params , Class clazz,MyCallBack myCallBack);
}
