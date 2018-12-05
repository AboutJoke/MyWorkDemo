package com.sylvan.myworkdemo.wiget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;
import com.sylvan.myworkdemo.utils.DimenUtils;

import java.util.concurrent.atomic.AtomicBoolean;

public class NewMarqueeText extends AppCompatTextView implements Runnable {
    private int currentScrollX = 0;// 当前滚动的位置
    private AtomicBoolean isStop = new AtomicBoolean(true);
    private int textWidth;
    private boolean isMeasure = false;
    private int maxWidth = -1;
    private int count = 0;
    private int step = DimenUtils.dp2px(2);

    public NewMarqueeText(Context context) {
        super(context);

    }

    public NewMarqueeText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NewMarqueeText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isMeasure) {
            getTextWidth();
            isMeasure = true;
        }
    }

    private void getTextWidth() {
        Paint paint = this.getPaint();
        String str = this.getText().toString();
        textWidth = (int) paint.measureText(str);
    }

    @Override
    public void setText(CharSequence text, TextView.BufferType type) {
        super.setText(text, type);
        this.isMeasure = false;
    }

    @Override
    public void run() {
        if (DimenUtils.isLayoutRTL()){
            currentScrollX -= step;// 滚动速度
            scrollTo(currentScrollX, 0);
            Log.d("NewMarqueeText","is_ar");
        }else {
            currentScrollX += step;// 滚动速度
            scrollTo(currentScrollX, 0);
            Log.d("NewMarqueeText","not_ar");
        }
        if (isStop.get()) {
            return;
        }

        if (maxWidth > textWidth) {
            stopScroll();
        }
        postDelayed(this, 100);
    }

    public void startScroll(int maxWidth) {
        if (isStop.compareAndSet(true, false)) {
            this.maxWidth = maxWidth;
            count = 0;
            this.removeCallbacks(this);
            post(this);
        }
    }

    public void stopScroll() {
        if (isStop.compareAndSet(false, true)) {
            currentScrollX = 0;
            count = 0;
        }
    }

    public void startFor0(int maxWidth) {
        setGravity(Gravity.LEFT);
        if (DimenUtils.isLayoutRTL()){
            currentScrollX = getScrollX();
        }else {
            currentScrollX = getScrollX();
        }
        startScroll(maxWidth);
    }

    public boolean needMarquee(int maxWidth) {
        this.maxWidth = maxWidth;
        getTextWidth();
        if (textWidth >= maxWidth && isStop.get()) {
            return true;
        }
        return false;
    }

    /**
     * 获取滚动完成的时间(ms),不需要滚动时返回0
     */
    public float getScrollTime() {
        int scrollRange = DimenUtils.getWindowWidth() - DimenUtils.dp2px(102);
        if (textWidth == 0) {
            getTextWidth();
        }
        if (textWidth <= scrollRange) {
            return 0;
        }
        return (textWidth - scrollRange) * 1.0F * (100 * 1.0F / step);
    }

    public void setStep(int step) {
        this.step = step;
    }
}