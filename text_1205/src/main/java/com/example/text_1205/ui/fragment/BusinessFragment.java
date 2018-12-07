package com.example.text_1205.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.text_1205.R;
import com.example.text_1205.ui.activity.MainActivity;

import java.lang.ref.WeakReference;

import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;

import static android.content.Context.MODE_PRIVATE;

public class BusinessFragment extends Fragment {
    private Button button;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private ImageView mImageView;
    SharedPreferences userName;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_business,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        button=view.findViewById(R.id.Sign_out);
        mImageView=view.findViewById(R.id.image);
        //退出登录
        sharedPreferences=getActivity().getSharedPreferences("loginData", MODE_PRIVATE);
        editor=sharedPreferences.edit();
        userName = getActivity().getSharedPreferences("userName", MODE_PRIVATE);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.clear();
                editor.commit();
                Intent intent=new Intent(getActivity(),MainActivity.class);

                startActivity(intent);
                getActivity().finish();
            }
        });
        QRTask qrTask=new QRTask(getActivity(),mImageView,userName);
        qrTask.execute(String.valueOf(userName));

    }
    static class QRTask extends AsyncTask<String,Void,Bitmap>{

        private WeakReference<Context> mContextWeakReference;
        private WeakReference<ImageView> mImageViewWeakReference;
        public QRTask(Context context, ImageView imageView, SharedPreferences userName){
            mContextWeakReference=new WeakReference<>(context);
            mImageViewWeakReference=new WeakReference<>(imageView);
        }
        @Override
        protected Bitmap doInBackground(String... strings) {
            String str=strings[0];
            if (TextUtils.isEmpty(str)){
                return null;
            }
            int size = mContextWeakReference.get().getResources().getDimensionPixelOffset(R.dimen.qr_code_size);
            return QRCodeEncoder.syncEncodeQRCode(str,size);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (bitmap!=null){
                mImageViewWeakReference.get().setImageBitmap(bitmap);
            }else {
                Toast.makeText(mContextWeakReference.get(),"二维码生成失败",Toast.LENGTH_LONG).show();
            }
        }
    }




}
