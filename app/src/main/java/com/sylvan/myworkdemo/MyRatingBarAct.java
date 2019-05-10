package com.sylvan.myworkdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.RatingBar;
import android.widget.Toast;

/**
 * @ClassName: MyRatingBarAct
 * @Author: sylvan
 * @Date: 19-3-19 上午11:32
 */
public class MyRatingBarAct extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_ratingbar);
        RatingBar bar = findViewById(R.id.ratingBar);
        bar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Toast.makeText(getApplicationContext(),"评分："+rating,Toast.LENGTH_SHORT).show();
            }
        });
    }
}
