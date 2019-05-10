package com.sylvan.myworkdemo.wiget;

import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import com.sylvan.myworkdemo.utils.DimenUtils;


/**
 * @ClassName: TrapezoidView
 * @Author: sylvan
 * @Date: 19-1-10 上午11:57
 */
public class TrapezoidView extends AppCompatTextView {
    private Paint paint;
    private Path path;
    private int mWidth;
    private int mHeight;
    private float mIncline;//梯度,即上底与下底长度差
    private float mRadius;//圆角半径

    int startColor;
    int endColor;

    private float[] radiusArray = new float[]{0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f};

    public TrapezoidView(Context context) {
        super(context);
        init();
    }

    public TrapezoidView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TrapezoidView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        path = new Path();
        mWidth = DimenUtils.dp2px(56);
        mHeight = DimenUtils.dp2px(20);

        startColor = Color.parseColor("#FFFF42A7");
        endColor = Color.parseColor("#FFFFAC49");

//        Shader mShader = new LinearGradient(0, 0, 0, 0,
//                new int[]{startColor, endColor},
//                null, Shader.TileMode.CLAMP);
//        paint.setShader(mShader);
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
//        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
//        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
//        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
//        if (widthMode == MeasureSpec.AT_MOST || heightMode == MeasureSpec.AT_MOST) {
//            setMeasuredDimension((int) mWidth, (int) mHeight);
//        } else {
//            setMeasuredDimension(widthSize, heightSize);
//        }
//    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        handler.sendEmptyMessage(0);

//        path.moveTo(0, 500);
//        path.lineTo(0,300);
//        path.lineTo(100,200);
//        path.lineTo(100, 500);


//        int width = getWidth();
//        int height = getHeight();
//        path.moveTo(width, 0);
//        path.lineTo(0, 0);
//        path.lineTo(0, height);
//        path.lineTo(width - DimenUtils.dp2px(3), height);

//        path.close();
//        canvas.drawPath(path, paint);
//        setBackground(new Drawable() {
//            @Override
//            public void draw(@NonNull Canvas canvas) {
//
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

//        setText("sssssssssssssssssssssss");
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            drawBg();
        }
    };

    public void drawBg(){
        int width = getWidth();
        int height = getHeight();

//        path.moveTo(500, 0); //rtl
//        path.lineTo(0,0);
//        path.lineTo(200,100);
//        path.lineTo(500, 100);

//        path.moveTo(500, 0);//ltr
//        path.lineTo(0,0);
//        path.lineTo(0,100);
//        path.lineTo(300, 100);
//        path.arcTo();


//        RectF rectF = new RectF(100,200,500,400);
//        path.addRoundRect(rectF,radiusArray,Path.Direction.CW);
        path.addRect(100,200,500,400,Path.Direction.CW);
//
//        Path path1 = new Path();
//        path1.addCircle(120,220,18,Path.Direction.CW);
//        path.op(path1,Path.Op.DIFFERENCE);
        path.setLastPoint(200, 400);

//        if (DimenUtils.isLayoutRTL()){
//            path.moveTo(width , 0);
//            path.lineTo(0, 0);
//            path.lineTo(DimenUtils.dp2px(3), height);
//            path.lineTo(width, height);
//        }else {
//            path.moveTo(width, 0);
//            path.lineTo(0, 0);
//            path.lineTo(0, height);
//            path.lineTo(width - DimenUtils.dp2px(3), height);
//
//        }



        Shader mShader = new LinearGradient(0, 0, width, height,
                new int[]{startColor, endColor},
                null, Shader.TileMode.CLAMP);
        paint.setShader(mShader);

        setBackground(new Drawable() {
            @Override
            public void draw(@NonNull Canvas canvas) {
                path.close();
//                canvas.clipPath(path);
                canvas.drawPath(path, paint);
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

    public void setStartColor(int color){
        startColor = color;
//        invalidate();
    }

    public void setEndColor(int color){
        endColor = color;
//       drawBg();
        invalidate();
    }

    public void setRadiusArray(float leftBottom, float rightBottom, float leftTop, float rightTop){
        radiusArray[0] = leftTop;
        radiusArray[1] = leftTop;
        radiusArray[2] = rightTop;
        radiusArray[3] = rightTop;
        radiusArray[4] = rightBottom;
        radiusArray[5] = rightBottom;
        radiusArray[6] = leftBottom;
        radiusArray[7] = leftBottom;

//        invalidate();
        drawBg();
    }
}
