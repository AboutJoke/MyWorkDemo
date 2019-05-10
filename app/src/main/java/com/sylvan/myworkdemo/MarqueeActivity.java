package com.sylvan.myworkdemo;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.sylvan.myworkdemo.utils.DimenUtils;
import com.sylvan.myworkdemo.wiget.NewMarqueeText;

public class MarqueeActivity extends AppCompatActivity {

    private ObjectAnimator mAnimation1;
    private NewMarqueeText mTipsTv;
    private View mTipsView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTipsTv=findViewById(R.id.tv_uplive_tips_ar);
        mTipsView = findViewById(R.id.layout_uplive_tips_ar);
        findViewById(R.id.show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTips("عزيزي %1$s، مرحباُ بك لتصبح أحد المُضيفين في LiveMe !","");
            }
        });
    }

    private void showTips(final String text, String next) {
//        if (mTipsTv == null) {
//            initView();
//        }
        mTipsTv.setText(text);
        float scrollTime = mTipsTv.getScrollTime();
//        mTipsTv.setStep(DimenUtils.dp2px(6));
        if (mAnimation1 == null) {
            mAnimation1 = ObjectAnimator.ofFloat(mTipsView, View.TRANSLATION_X, DimenUtils.getWindowWidth() - DimenUtils.dp2px(56), 0);
            mAnimation1.setDuration(200);
            mAnimation1.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    mTipsView.setVisibility(View.VISIBLE);
                    //mTipsTv.setScrollX(-mTipsTv.getWidth());
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    mTipsTv.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            int width = DimenUtils.getWindowWidth() - DimenUtils.dp2px(71);
                            if (mTipsTv.needMarquee(width)) {
                                mTipsTv.startFor0(width);
                            }
                        }
                    }, 200);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }
        mAnimation1.start();
        handler.sendEmptyMessageDelayed(1, (long) scrollTime);
//        int time = TIME_TIPS_SHOW_SHORT;
//
//        Message message = Message.obtain();
//        message.obj = !TextUtils.isEmpty(next);
//        message.what = MSG_TIPS_HIDE;
//        mHandler.sendMessageDelayed(message, time);
//
//        if (!TextUtils.isEmpty(next)) {
//            Message msg = Message.obtain();
//            msg.obj = next;
//            msg.what = MSG_TIPS_SHOW;
//            mHandler.sendMessageDelayed(msg, time + 500);
//        }
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mTipsView.setVisibility(View.INVISIBLE);
        }
    };
}
