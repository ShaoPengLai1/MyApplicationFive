package com.example.text_1205.persenter;

import com.example.text_1205.bean.NewBean;
import com.example.text_1205.bean.SignBean;
import com.example.text_1205.callBack.MyCallBack;
import com.example.text_1205.model.IModelImpl;
import com.example.text_1205.utils.NetUtils;
import com.example.text_1205.view.IView;

/**
 * IPresenter实现接口类
 */
public class IPresenterImpl implements IPresenter{

    private IModelImpl model;
    private IView iView;

    public IPresenterImpl(IView iView) {
        this.iView = iView;
        model=new IModelImpl();
    }

    @Override
    public void getResult(String url, String params, Class clazz) {
        model.getResult(url, null, clazz, new MyCallBack() {
            @Override
            public void setData(Object data) {
                //判断当前的data是哪个bean
                if (data instanceof NewBean){
                    NewBean bean= (NewBean) data;
                    if (bean==null || !bean.isSuccess()){
                        iView.error(bean.getMsg());
                    }else {
                        iView.success(bean);
                    }
                }else if (data instanceof SignBean){
                    SignBean signBean= (SignBean) data;
                    if (signBean==null||!signBean.isSuccess()){
                        iView.error(signBean.getMsg());
                    }else {
                        iView.success(signBean);
                    }
                }
            }
        });
    }


    /**
     * 解绑
     */
    public void onDetach(){
        if (model!=null){
            model = null;
        }
        if (iView!=null){
            iView = null;
        }
    }



}
