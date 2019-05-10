package com.sylvan.myworkdemo.wiget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.sylvan.myworkdemo.R;

public class PorgressLayout extends FrameLayout {
    private ProgressBar mProgressBar;
    private TextView mFirstRechargeGiftNum;

    public PorgressLayout(Context context) {
        super(context);
        init(context);
    }

    public PorgressLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PorgressLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_detail_progress, this);
        initView();
    }

    private void initView() {
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bg);
//        mFirstRechargeGiftNum = (TextView) findViewById(R.id.first_recharge_gift_num);
    }

    public void setProgres(int progres){
        mProgressBar.setProgress(progres);
    }

    public void setText(String text){
        mFirstRechargeGiftNum.setText(text);
    }
}
