package com.sylvan.myworkdemo.pip;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;
import com.sylvan.myworkdemo.R;

public class FloatController extends FrameLayout implements View.OnClickListener {

    private FrameLayout container;

    private ControllerListener controllerListener;
    public interface ControllerListener {
        void fullScreen(View view);
        void closeVideo(View view);
    }
    public void setControllerListener(ControllerListener controllerListener) {
        this.controllerListener = controllerListener;
    }

    public FloatController(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public FloatController(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public FloatController(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    View view;
    public void initView(Context context){
        view=View.inflate(context, R.layout.video_float_controller,null);
        view.findViewById(R.id.btn_close).setOnClickListener(this);
        view.findViewById(R.id.btn_full).setOnClickListener(this);
        container = view.findViewById(R.id.container);
        this.addView(view);
    }

//    public void setVideoHolder(KewlPlayerVideoHolder videoHolder){
//        videoHolder.setParentView(container);
//    }

    public void showLoading(boolean show){
        view.findViewById(R.id.loading).setVisibility(show?VISIBLE:GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_close:
                controllerListener.closeVideo(v);
                break;
            case R.id.btn_full:
                controllerListener.fullScreen(v);
                break;
        }
    }

    private float downX;
    private float downY;
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = ev.getX();
                downY = ev.getY();
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                float absDeltaX = Math.abs(ev.getX() - downX);
                float absDeltaY = Math.abs(ev.getY() - downY);
                if (absDeltaX > ViewConfiguration.get(getContext()).getScaledTouchSlop() ||
                        absDeltaY > ViewConfiguration.get(getContext()).getScaledTouchSlop()) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}
