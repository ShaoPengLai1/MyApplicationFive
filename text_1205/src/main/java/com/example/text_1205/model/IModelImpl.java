package com.example.text_1205.model;

import com.example.text_1205.callBack.MyCallBack;
import com.example.text_1205.utils.NetUtils;


public class IModelImpl implements IModel {

    private MyCallBack myCallBack;


    @Override
    public void getResult(String url, String params, Class clazz, final MyCallBack callBack) {
        this.myCallBack=callBack;

        NetUtils.getIntance().getRequest(url, clazz, new NetUtils.CallBack() {
            @Override
            public void onSuccess(Object o) {
                callBack.setData(o);
            }
        });
    }


}
