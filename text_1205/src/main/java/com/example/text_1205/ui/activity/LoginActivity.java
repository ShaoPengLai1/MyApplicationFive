package com.example.text_1205.ui.activity;


import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.example.text_1205.R;
import com.example.text_1205.ui.adapter.MainPageAdaper;

public class LoginActivity extends AppCompatActivity {

    private ViewPager contents;
    private TabLayout tabLayout;
    private MainPageAdaper adaper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        contents=findViewById(R.id.contents);
        tabLayout=findViewById(R.id.t_tab);
        adaper=new MainPageAdaper(getSupportFragmentManager());
        contents.setAdapter(adaper);
        tabLayout.setupWithViewPager(contents);
    }
}
