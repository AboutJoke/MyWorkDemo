package com.sylvan.myworkdemo.pip;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.FrameLayout;
import com.sylvan.myworkdemo.R;

public class FloatView extends FrameLayout {

    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mParams;

    public FloatView(@NonNull Context context, int x, int y) {
        super(context);
        floatX = x;
        floatY = y;
        init(context);
    }


    private void init(Context context) {
        setBackgroundResource(R.drawable.shape_float_window_background);
        int padding = dp2px(context, 1);
        setPadding(padding, padding, padding, padding);
        initWindow();
    }

    private void initWindow() {
        mWindowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        mParams = new WindowManager.LayoutParams();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            mParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        }
        mParams.format = PixelFormat.TRANSLUCENT;
        mParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        mParams.windowAnimations = R.style.FloatWindowAnimation;
        mParams.gravity = Gravity.START | Gravity.TOP;
        int width = dp2px(getContext(), 250);

        DisplayMetrics dm = new DisplayMetrics();
        mWindowManager.getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;
        mParams.width = width;
        mParams.height = width * 9 / 16;
        mParams.x = screenWidth;
        mParams.y = screenHeight;
    }

    public boolean addToWindow() {
        if (mWindowManager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                if (!isAttachedToWindow()) {
                    mWindowManager.addView(this, mParams);
                    return true;
                } else {
                    return false;
                }
            } else {
                try {
                    if (getParent() == null) {
                        mWindowManager.addView(this, mParams);
                    }
                    return true;
                } catch (Exception e) {
                    return false;
                }
            }
        } else {
            return false;
        }
    }

    public boolean removeFromWindow() {
        if (mWindowManager != null) {
                if (isAttachedToWindow()) {
                    mWindowManager.removeViewImmediate(this);
                    return true;
                } else {
                    return false;
                }
        } else {
            return false;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return ev.getAction() != MotionEvent.ACTION_DOWN;
    }

    private int floatX;
    private int floatY;
    private boolean firstTouch = true;
    private float startX;
    private float startY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int X = (int) event.getRawX();
        final int Y = (int) event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                startY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                int x = (int) event.getX();
                int width = mWindowManager.getDefaultDisplay().getWidth();
                int i = X - x;
                if (i < 250) {
                    mParams.x = 0;
                    mWindowManager.updateViewLayout(this, mParams);
                } else if (i > (300)) {
                    mParams.x = width;
                    mWindowManager.updateViewLayout(this, mParams);
                }
                firstTouch = true;
                break;
            case MotionEvent.ACTION_MOVE:
                if (firstTouch) {
                    floatX = (int) event.getX();
                    floatY = (int) (event.getY() + getStatusBarHeight(getContext()));
                    firstTouch = false;
                }
                mParams.x = (X - floatX);
                mParams.y = (Y - floatY);
                mWindowManager.updateViewLayout(this, mParams);
                break;
        }
        return super.onTouchEvent(event);
    }

    public static int dp2px(Context context, float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.getResources().getDisplayMetrics());
    }

    public static double getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }
}
