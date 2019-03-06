package com.sylvan.myworkdemo.wiget;

import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import com.sylvan.myworkdemo.utils.DimenUtils;

/**
 * @ClassName: DemoLayout
 * @Author: sylvan
 * @Date: 19-1-14 下午6:31
 */
public class DemoLayout extends RelativeLayout {
    private Paint mPaint;//画梯形的画笔
    private Drawable mDrawable = null;//通过src设置的图片
    private int mWidth, mHeight;//控件的宽度和高度
    private float mIncline;//梯度,即上底与下底长度差
    private float mRadius;//圆角半径
    private int[] mShadeColor;//遮罩颜色
    private PorterDuffXfermode mXfermodeSrc;
    private PorterDuffXfermode mXfermodeShape;
    private float[] mRadii;

    private static final float DEFAULT_INCLINE = 65F;
    private static final float DEFAULT_RADIUS = 8F;
    private static final int[] DEFAULT_SHADE_COLOR = new int[]{0xff373737, 0xffffffff, 0xff373737};

    public DemoLayout(Context context) {
        super(context);
        init();
    }

    public DemoLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DemoLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化操作
     */
    private void init() {

        this.mIncline = DimenUtils.dp2px(DEFAULT_INCLINE);
        this.mRadius = DimenUtils.dp2px(DEFAULT_RADIUS);
        this.mShadeColor = DEFAULT_SHADE_COLOR;

        // 初始化画笔
        initPaint();
        // 圆角矩形
        initRadii();
        // 图形绘制混合模式
        initXfermode();


//        setDrawingCacheEnabled(true);
//        setWillNotDraw(false);
//        setText("ssssssssssssssssssssssss");
//        setTextColor(Color.BLACK);
//        setGravity(Gravity.CENTER);

        setBackground(new Drawable() {
            @Override
            public void draw(@NonNull Canvas canvas) {
                canvasTrapezoid(canvas);
            }

            @Override
            public void setAlpha(int alpha) {

            }

            @Override
            public void setColorFilter(ColorFilter colorFilter) {

            }

            @Override
            public int getOpacity() {
                return PixelFormat.TRANSLUCENT;
            }
        });
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
//        Shader mShader = new LinearGradient(mWidth, mHeight, 0, 0,
//                mShadeColor, null, Shader.TileMode.CLAMP);
//        mPaint.setShader(mShader);
    }

    private void initRadii() {
        /* 向路径中添加圆角矩形。radii数组定义圆角矩形的四个圆角的x,y半径。*/
        /* 圆角的半径，依次为左上角xy半径，右上角，右下角，左下角。*/
        mRadii = new float[]{0.0f, 0.0f, mRadius, mRadius, 0.0f, 0.0f, 0.0f, 0.0f};
    }

    private void initXfermode() {
        // 叠加处绘制源图,PorterDuff.Mode.SRC_IN只显示两层图像交集部分的上层图像
        mXfermodeSrc = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
        // 绘制遮罩,使用PorterDuff.Mode.MULTIPLY
        mXfermodeShape = new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        canvasTrapezoid(canvas);
//        setBackground(new Drawable() {
//            @Override
//            public void draw(@NonNull Canvas canvas) {
//                canvasTrapezoid(canvas);
//            }
//
//            @Override
//            public void setAlpha(int alpha) {
//
//            }
//
//            @Override
//            public void setColorFilter(ColorFilter colorFilter) {
//
//            }
//
//            @Override
//            public int getOpacity() {
//                return PixelFormat.TRANSLUCENT;
//            }
//        });
//        canvasTrapezoid(canvas);
    }

    private void canvasTrapezoid(Canvas canvas) {
        Shader mShader = new LinearGradient(0, 0, mWidth, 0,
                new int[]{Color.RED,Color.BLUE}, null, Shader.TileMode.CLAMP);
        mPaint.setShader(mShader);
        // 背景透明
        canvas.drawColor(Color.TRANSPARENT);
        // 保存为单独的层
        int saveCount = canvas.saveLayer(0, 0, mWidth, mHeight, mPaint, Canvas.ALL_SAVE_FLAG);

        // 绘制圆角矩形
        canvas.drawPath(getRoundRectPath(), mPaint);

        // 图形绘制混合模式
        mPaint.setXfermode(mXfermodeSrc);

        // 绘制梯形路径
        canvas.drawPath(getTrapezoidPath(), mPaint);

        // 画遮罩
//        mPaint.setXfermode(mXfermodeShape);
//        canvas.drawBitmap(getShapeBitmap(), new Matrix(), mPaint);

        mPaint.setXfermode(null);
        canvas.restoreToCount(saveCount);
    }

    private Path getRoundRectPath() {
        Path path = new Path();
        // 向路径中添加圆角矩形
        path.addRoundRect(new RectF(0, 0, mWidth, mHeight), mRadii, Path.Direction.CW);
        return path;
    }

    private Path getTrapezoidPath() {
        Path topPath = new Path();


        topPath.moveTo(mWidth -mIncline , 0);
        topPath.lineTo(0, 0);
        topPath.lineTo(0, mHeight);
        topPath.lineTo( mWidth , mHeight);

//        topPath.moveTo(mWidth , 0);
//        topPath.lineTo(mIncline, 0);
//        topPath.lineTo(0 , mHeight);
//        topPath.lineTo(mWidth , mHeight);

//        topPath.moveTo(0, 0);
//        topPath.lineTo(mWidth, 0);
//        topPath.lineTo(mWidth , mHeight);
//        topPath.lineTo(mIncline, mHeight);

//        topPath.moveTo(500, 0);
//        topPath.lineTo(300,0);
//        topPath.lineTo(200,100);
//        topPath.lineTo(500, 100);

        topPath.close();
        return topPath;
    }
}
