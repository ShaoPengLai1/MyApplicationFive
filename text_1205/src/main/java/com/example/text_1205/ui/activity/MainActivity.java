package com.example.text_1205.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.text_1205.R;
import com.example.text_1205.bean.SignBean;
import com.example.text_1205.persenter.IPresenterImpl;
import com.example.text_1205.utils.NotNullUtls;
import com.example.text_1205.view.IView;


public class MainActivity extends AppCompatActivity implements IView,View.OnClickListener {

    private EditText phone_edit,pass_edit;
    private CheckBox remember,automatic;
    private Button submit;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private IPresenterImpl mIPresenter;
    private String urlStr="http://www.xieast.com/api/user/login.php?username=%s&password=%s";
    private String name;
    private String pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        phone_edit=findViewById(R.id.phone_edit);
        pass_edit=findViewById(R.id.pass_edit);
        remember=findViewById(R.id.remember);
        automatic=findViewById(R.id.automatic);
        submit=findViewById(R.id.submit);

        pass_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                submit.setEnabled((s.length()>=6));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        submit.setOnClickListener(this);

        /**
         * 密码显示和隐藏
         */
        findViewById(R.id.icon).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()==MotionEvent.ACTION_DOWN){
                    pass_edit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    findViewById(R.id.icon).setBackgroundResource(R.drawable.ic_action_eye);
                }else if (event.getAction()==MotionEvent.ACTION_UP){
                    pass_edit.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    findViewById(R.id.icon).setBackgroundResource(R.drawable.ic_action_name);
                }
                return false;
            }

        });
        mIPresenter=new IPresenterImpl(this);
        sharedPreferences=getSharedPreferences("loginData",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        //将记住密码状态值取出
        boolean remember_isCheck = sharedPreferences.getBoolean("remember_isCheck", false);
        if (remember_isCheck){
            //取出值
            name = sharedPreferences.getString("mName", null);
            pass = sharedPreferences.getString("mPass", null);
            //设值
            phone_edit.setText(name);
            pass_edit.setText(pass);
            remember.setChecked(true);
        }
        //自动状态取出
        boolean automatic_isCheck = sharedPreferences.getBoolean("automatic_isCheck", false);
        if (automatic_isCheck){
            Intent intent=new Intent(MainActivity.this,LoginActivity.class);
            String admin = sharedPreferences.getString("admin", null);
            intent.putExtra("userName",admin);
            startActivity(intent);
        }
        // 勾选自动登录同时勾选记住 密码
        automatic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    remember.setChecked(true);
                }else {
                    remember.setChecked(false);
                }
            }
        });
    }
    @Override
    public void success(Object o) {
        //成功方法里强转为bean类
        SignBean signBean= (SignBean) o;
        //存入bean类中
        editor.putString("admin",signBean.getData().getName());
        //提交
        editor.commit();
        Toast.makeText(MainActivity.this,signBean.getMsg(),Toast.LENGTH_LONG).show();
        //跳转
        Intent intent=new Intent(MainActivity.this,LoginActivity.class);
        intent.putExtra("userName",signBean.getData().getName());
        startActivity(intent);

    }

    @Override
    public void error(String str) {
        Toast.makeText(MainActivity.this,str,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();


        switch (id){

            case R.id.submit:
                //checkPermission();

                /**
                 * 获取输入框的值
                 */
                String mName = phone_edit.getText().toString();
                String mPass = pass_edit.getText().toString();
                //勾选记住密码选中
                if (remember.isChecked()){
                    //存入值
                    editor.putString("mName",mName);
                    editor.putString("mPass",mPass);
                    editor.putBoolean("remember_isCheck",true);
                    //提交
                    editor.commit();
                }else {
                    //清空
                    editor.clear();
                }

                if (NotNullUtls.getInstance().isNonNull(mName,mPass)){

                    //点击登录按钮调用p层
                    mIPresenter.getResult(String.format(urlStr,mName,mPass),null,SignBean.class);

                }else {
                    Toast.makeText(MainActivity.this,"账号密码错误...",Toast.LENGTH_LONG).show();
                }
                //自动登录勾选
                if (automatic.isChecked()){
                    editor.putBoolean("automatic_isCheck",true);
                    editor.commit();
                }

                break;
                    default:
                        break;
        }

    }
    /**
     * 解绑
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mIPresenter.onDetach();
    }


}
