package com.example.text_1205.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.text_1205.R;
import com.example.text_1205.bean.NewBean;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class DataFragmentAdapter extends BaseAdapter {

    private List<NewBean.DataBean> mData;
    private Context mContext;

    public DataFragmentAdapter(Context mContext) {
        this.mContext = mContext;
        mData=new ArrayList<>();
    }

    public void setData(List<NewBean.DataBean> datas) {
        mData.clear();
        if (datas!=null){
            mData.addAll(datas);
        }
        notifyDataSetChanged();
    }
    public void addData(List<NewBean.DataBean> datas) {
        if (datas!=null){
            mData.addAll(datas);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData.size();
    }


    @Override
    public NewBean.DataBean getItem(int position) {
        return mData.get(position);
    }
    private final int ITEM_COUNT = 2;
    private final int IMAGE_TYPR = 0;
    private final int IMAGES_TYPR = 1;

    @Override
    public int getViewTypeCount() {
        return ITEM_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position).hasMastIcon()?IMAGES_TYPR:IMAGE_TYPR;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView==null){
            convertView=LayoutInflater.from(mContext).inflate(
                    getItemViewType(position)==IMAGE_TYPR?R.layout.fragment_image_item:R.layout.fragment_images_item,
                    parent,false
            );
            holder=new ViewHolder(convertView);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        holder.bindData(getItem(position));
        return convertView;
    }
    class ViewHolder{
        TextView title;
        ImageView image1,image2,image3,data_image;

        public ViewHolder(View convertView) {
            title=convertView.findViewById(R.id.title);
            image1=convertView.findViewById(R.id.data_image);
            image2=convertView.findViewById(R.id.data_image2);
            image3=convertView.findViewById(R.id.data_image3);
            data_image=convertView.findViewById(R.id.data_image);
            convertView.setTag(this);
        }

        public void bindData(NewBean.DataBean item) {
            title.setText(item.getTitle());
            if (image1!=null&&image2!=null&&image3!=null&&data_image!=null){
                ImageLoader.getInstance().displayImage(item.getThumbnail_pic_s03(),data_image);
                ImageLoader.getInstance().displayImage(item.getThumbnail_pic_s(),image1);
                ImageLoader.getInstance().displayImage(item.getThumbnail_pic_s02(),image2);
                ImageLoader.getInstance().displayImage(item.getThumbnail_pic_s03(),image3);
            }
        }
    }
}
