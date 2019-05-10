package com.sylvan.myworkdemo.wiget;

import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import com.sylvan.myworkdemo.utils.DimenUtils;

/**
 * @ClassName: TrapezoidDrawable
 * @Author: sylvan
 * @Date: 19-2-28 下午1:42
 */
public class TrapezoidDrawable extends View {
    private Paint mPaint;
    private float mIncline;//梯度,即上底与下底长度差
    private float mRadius;//圆角半径
    private int[] mShadeColor = DEFAULT_SHADE_COLOR;//渐变颜色
    private PorterDuffXfermode mXfermodeSrc;
    private float[] mRadii;
    private Drawable mDrawable;

    private static final float DEFAULT_INCLINE = 3F;
    private static final float DEFAULT_RADIUS = 6F;
    private static final int[] DEFAULT_SHADE_COLOR = new int[]{0xFFFF42A7, 0xFFFFAC49};

    private int mWidth;
    private int mHeight;

    private boolean isHypotenuseInLeft = false;

    public TrapezoidDrawable(Context context) {
        super(context);
        init();
    }

    public TrapezoidDrawable(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TrapezoidDrawable(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init () {
        this.mIncline = DimenUtils.dp2px(DEFAULT_INCLINE);
        this.mRadius = DimenUtils.dp2px(DEFAULT_RADIUS);
//        this.mShadeColor = DEFAULT_SHADE_COLOR;
        Log.d("TrapezoidColor","init--"+mShadeColor[0]+"/"+mShadeColor[1]);
        initPaint();
        initRadii();
        // 图形绘制混合模式
        initXfermode();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvasTrapezoid(canvas);
    }

    private void canvasTrapezoid(Canvas canvas) {
        Shader mShader = new LinearGradient(0, 0, mWidth, 0,
                mShadeColor, null, Shader.TileMode.CLAMP);
        Log.d("TrapezoidColor","canvasTrapezoid"+mShadeColor[0]+"/"+mShadeColor[1]);
        mPaint.setShader(mShader);
        canvas.drawColor(Color.TRANSPARENT);
        int saveCount = canvas.saveLayer(0, 0, mWidth, mHeight, mPaint, Canvas.ALL_SAVE_FLAG);
        // 绘制圆角矩形
        canvas.drawPath(getRoundRectPath(), mPaint);
        // 图形绘制混合模式
        mPaint.setXfermode(mXfermodeSrc);
        // 绘制梯形
        canvas.drawPath(getTrapezoidPath(), mPaint);

        mPaint.setXfermode(null);
        canvas.restoreToCount(saveCount);
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
    }

    /**
     * 圆角的半径，依次为左上角xy半径，右上角，右下角，左下角
     */
    private void initRadii() {
        if (isHypotenuseInLeft) {
            if (DimenUtils.isLayoutRTL()){
                mRadii = new float[]{0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, mRadius, mRadius};
            }else {
                mRadii = new float[]{0.0f, 0.0f, 0.0f, 0.0f, mRadius, mRadius, 0.0f, 0.0f};
            }
        } else {
            if (DimenUtils.isLayoutRTL()) {
                mRadii = new float[]{0.0f, 0.0f, mRadius, mRadius, 0.0f, 0.0f, 0.0f, 0.0f};
            } else {
                mRadii = new float[]{mRadius, mRadius, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f};
            }
        }
    }

    private void initXfermode() {
        // 叠加处绘制源图,PorterDuff.Mode.SRC_IN只显示两层图像交集部分的上层图像
        // android p 上该模式会失效
        // https://issuetracker.google.com/issues/111819103
        mXfermodeSrc = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
    }

    private Path getRoundRectPath() {
        Path path = new Path();
        path.addRoundRect(new RectF(0, 0, mWidth, mHeight), mRadii, Path.Direction.CW);
        return path;
    }

    private Path getTrapezoidPath() {
        Path topPath = new Path();
        if (isHypotenuseInLeft) { // 斜边在左边
            if (DimenUtils.isLayoutRTL()){
                topPath.moveTo(mWidth -mIncline , 0);
                topPath.lineTo(0, 0);
                topPath.lineTo(0, mHeight);
                topPath.lineTo( mWidth , mHeight);
            }else {
                topPath.moveTo(mWidth, 0);
                topPath.lineTo(mIncline, 0);
                topPath.lineTo(0, mHeight);
                topPath.lineTo(mWidth, mHeight);
            }
        } else {
            if (DimenUtils.isLayoutRTL()) {
                topPath.moveTo(mWidth, 0);
                topPath.lineTo(0, 0);
                topPath.lineTo(mIncline, mHeight);
                topPath.lineTo(mWidth, mHeight);
            } else {
                topPath.moveTo(mWidth, 0);
                topPath.lineTo(0, 0);
                topPath.lineTo(0, mHeight);
                topPath.lineTo(mWidth - mIncline, mHeight);
            }
        }
        topPath.close();
        return topPath;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    public void setColor(int startColor, int endColor) {
        mShadeColor[0] = startColor;
        mShadeColor[1] = endColor;
//        setBackgroundColor(startColor);
        invalidate();
    }


    public void setmWidth(int mWidth) {
        this.mWidth = mWidth;
    }

    public void setmHeight(int mHeight) {
        this.mHeight = mHeight;
    }
}
