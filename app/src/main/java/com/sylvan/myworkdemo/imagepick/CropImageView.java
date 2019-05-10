package com.sylvan.myworkdemo.imagepick;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.sylvan.myworkdemo.imagepick.edge.Edge;
import com.sylvan.myworkdemo.utils.DimenUtils;

/**
 * @ClassName: com.sylvan.myworkdemo.imagepick
 * @Author: sylvan
 * @Date: 19-5-10
 */
@SuppressLint("AppCompatCustomView")
public class CropImageView extends ImageView {
    private Paint mBorderPaint; //边框
    private Paint mGuidelinePaint; //参考线
    private Paint mCornerPaint; //边角

    private int mScaleRadius;
    private int mBorderThickness;
    private int mCornerThickness;
    private int mCornerLength;

    private RectF mBitmapRect;


    public CropImageView(Context context) {
        super(context);
        init();
    }

    public CropImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CropImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mBorderPaint = new Paint();
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setStrokeWidth(DimenUtils.dp2px(3));
        mBorderPaint.setColor(Color.parseColor("#AAFFFFFF"));

        mGuidelinePaint = new Paint();
        mGuidelinePaint.setStyle(Paint.Style.STROKE);
        mGuidelinePaint.setStrokeWidth(DimenUtils.dp2px(1));
        mGuidelinePaint.setColor(Color.parseColor("#AAFFFFFF"));


        mCornerPaint = new Paint();
        mCornerPaint.setStyle(Paint.Style.STROKE);
        mCornerPaint.setStrokeWidth(DimenUtils.dp2px(5));
        mCornerPaint.setColor(Color.WHITE);


        mScaleRadius = DimenUtils.dp2px(24);
        mBorderThickness = DimenUtils.dp2px(3);
        mCornerThickness = DimenUtils.dp2px(5);
        mCornerLength = DimenUtils.dp2px(20);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        mBitmapRect = getBitmapRect();
        initWindowCrop(mBitmapRect);
    }

    private void initWindowCrop(RectF imageRect) {

        //裁剪框距离图片的padding
        float horizontalPadding = imageRect.width() * 0.01f;
        float verticalPadding = imageRect.height() * 0.01f;

        //初始化四条边的边界
        Edge.LEFT.initCoordinate(imageRect.left + horizontalPadding);
        Edge.RIGHT.initCoordinate(imageRect.right + horizontalPadding);
        Edge.TOP.initCoordinate(imageRect.top + verticalPadding);
        Edge.BOTTOM.initCoordinate(imageRect.bottom + verticalPadding);
    }

    private RectF getBitmapRect() {

        final Drawable drawable = getDrawable();
        if (drawable == null) {
            return new RectF();
        }

        final float[] matrixValues = new float[9];
        getImageMatrix().getValues(matrixValues);

        final float scaleX = matrixValues[Matrix.MSCALE_X];
        final float scaleY = matrixValues[Matrix.MSCALE_Y];
        final float transX = matrixValues[Matrix.MTRANS_X];
        final float transY = matrixValues[Matrix.MTRANS_Y];

        final int drawableIntrinsicWidth = drawable.getIntrinsicWidth();
        final int drawableIntrinsicHeight = drawable.getIntrinsicHeight();

        final int drawableDisplayWidth = Math.round(drawableIntrinsicWidth * scaleX);
        final int drawableDisplayHeight = Math.round(drawableIntrinsicHeight * scaleY);

        final float left = Math.max(transX, 0);
        final float top = Math.max(transY, 0);
        final float right = Math.min(left + drawableDisplayWidth, getWidth());
        final float bottom = Math.min(top + drawableDisplayHeight, getHeight());

        return new RectF(left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制参考线
//        drawGuideLine(canvas);
        //绘制裁剪框
        drawCropEdge(canvas);
        //绘制裁剪框的四角
        drawCropConner(canvas);
    }

    private void drawCropEdge(Canvas canvas) {
        canvas.drawRect(Edge.LEFT.getCoordinate(),
                Edge.TOP.getCoordinate(),
                Edge.RIGHT.getCoordinate(),
                Edge.BOTTOM.getCoordinate(),
                mBorderPaint);
    }

    private void drawCropConner(Canvas canvas) {

    }
}
