package com.sylvan.myworkdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.sylvan.myworkdemo.wiget.TrapezoidImageView;
import com.sylvan.myworkdemo.wiget.TrapezoidView;

/**
 * @ClassName: MyTrapezoidActivity
 * @Author: sylvan
 * @Date: 19-1-10 上午11:56
 */
public class MyTrapezoidActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        TrapezoidView view = new TrapezoidView(this);
        setContentView(R.layout.act_trapezoid);
//        TrapezoidView view =findViewById(R.id.text);
//        view.setRadiusArray(0,0,18,0);
        TrapezoidImageView view1 = new TrapezoidImageView(this)
                .setIncline(2)
                .setIncline(2)
                .setShadeColor();
//        view.setStartColor(Color.RED);
//        view.setEndColor(Color.BLUE);

        String str= "1,2,3,4,5,6,7,你好";
        String[] split = str.split(",");
        try {
            for (int i = 0; i < split.length; i++) {
                Log.d("onCreate","index_"+Integer.valueOf(split[i]));
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
