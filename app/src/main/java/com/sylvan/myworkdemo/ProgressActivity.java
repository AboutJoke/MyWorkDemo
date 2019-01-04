package com.sylvan.myworkdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.sylvan.myworkdemo.wiget.PorgressLayout;

public class ProgressActivity extends AppCompatActivity {
    private PorgressLayout mProgressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_progress_layout);
        initView();
    }

    private void initView() {
        mProgressBar = (PorgressLayout) findViewById(R.id.progress_bar);
        mProgressBar.setProgres(50);
//        mProgressBar.setText("100/1000");

    }
}
