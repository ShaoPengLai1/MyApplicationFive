package com.example.text_1205.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.text_1205.R;
import com.example.text_1205.bean.NewBean;
import com.example.text_1205.callBack.MyCallBack;
import com.example.text_1205.persenter.IPresenterImpl;
import com.example.text_1205.ui.adapter.DataFragmentAdapter;
import com.example.text_1205.utils.NetUtils;
import com.example.text_1205.view.IView;

import me.maxwin.view.XListView;

public class DataFragment extends Fragment implements IView {

    private XListView contents;
    private String urlStr="http://www.xieast.com/api/news/news.php?page=%d";
    private int mPage;
    private DataFragmentAdapter adapter;
    private IPresenterImpl mPresenter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_datafragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPage=1;
        contents=view.findViewById(R.id.contents);
        mPresenter=new IPresenterImpl(this);
        adapter=new DataFragmentAdapter(getActivity());
        contents.setAdapter(adapter);
        contents.setPullLoadEnable(true);
        contents.setPullRefreshEnable(true);
        contents.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                mPage=1;
                loadData();
            }

            @Override
            public void onLoadMore() {
                loadData();
            }
        });
        loadData();
    }

    private void loadData() {
        mPresenter.getResult(String.format(urlStr,mPage),null,NewBean.class);
    }
    @Override
    public void success(Object o) {

        NewBean bean= (NewBean) o;
        if (mPage==1){
            adapter.setData(bean.getData());
        }else {
            adapter.addData(bean.getData());
        }
        mPage++;
        contents.stopRefresh();
        contents.stopLoadMore();
    }



    @Override
    public void error(String str) {
        Toast.makeText(getActivity(),str,Toast.LENGTH_LONG).show();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDetach();
    }
}
