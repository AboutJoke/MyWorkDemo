package com.sylvan.myworkdemo.imagepick;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;
import com.sylvan.myworkdemo.imagepick.edge.Edge;
import com.sylvan.myworkdemo.imagepick.helper.CropWindowSelector;
import com.sylvan.myworkdemo.utils.CatchEdgeUtil;
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
    private PointF mOffestPoint = new PointF();

    //手指触摸到的边
    private CropWindowSelector mHandlPressEdge;


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
        Edge.TOP.initCoordinate(imageRect.top + verticalPadding);
        Edge.RIGHT.initCoordinate(imageRect.right - horizontalPadding);
        Edge.BOTTOM.initCoordinate(imageRect.bottom - verticalPadding);
    }

    //获取当前bitmap的rect
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
        float left = Edge.LEFT.getCoordinate();
        float top = Edge.TOP.getCoordinate();
        float right = Edge.RIGHT.getCoordinate();
        float bottom = Edge.BOTTOM.getCoordinate();

        final float lateralOffset = (mCornerThickness - mBorderThickness) / 2f;
        final float startOffset = mCornerThickness - (mBorderThickness / 2f);

        //左上角左面的短线
        canvas.drawLine(left - lateralOffset, top - startOffset, left - lateralOffset, top + mCornerLength, mCornerPaint);
        //左上角上面的短线
        canvas.drawLine(left - startOffset, top - lateralOffset, left + mCornerLength, top - lateralOffset, mCornerPaint);

        //右上角右面的短线
        canvas.drawLine(right + lateralOffset, top - startOffset, right + lateralOffset, top + mCornerLength, mCornerPaint);
        //右上角上面的短线
        canvas.drawLine(right + startOffset, top - lateralOffset, right - mCornerLength, top - lateralOffset, mCornerPaint);
        //左下角左面的短线
        canvas.drawLine(left - lateralOffset, bottom + startOffset, left - lateralOffset, bottom - mCornerLength, mCornerPaint);
        //左下角底部的短线
        canvas.drawLine(left - startOffset, bottom + lateralOffset, left + mCornerLength, bottom + lateralOffset, mCornerPaint);

        //右下角左面的短线
        canvas.drawLine(right + lateralOffset, bottom + startOffset, right + lateralOffset, bottom - mCornerLength, mCornerPaint);
        //右下角底部的短线
        canvas.drawLine(right + startOffset, bottom + lateralOffset, right - mCornerLength, bottom + lateralOffset, mCornerPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabled()) {
            return false;
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                onActionDown(event.getX(), event.getY());
                return true;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                onActionUp();
                getParent().requestDisallowInterceptTouchEvent(false);
                return true;

            case MotionEvent.ACTION_MOVE:
                onActionMove(event.getX(), event.getY());
                getParent().requestDisallowInterceptTouchEvent(true);
                return true;
            default:
                return false;
        }
    }

    private void onActionDown(float x, float y) {

        float left = Edge.LEFT.getCoordinate();
        float top = Edge.TOP.getCoordinate();
        float right = Edge.RIGHT.getCoordinate();
        float bottom = Edge.BOTTOM.getCoordinate();
        mHandlPressEdge = CatchEdgeUtil.getPressedHandle(x, y, left, top, right, bottom, mScaleRadius);
        if (mHandlPressEdge != null) {
            Log.d(CropImageView.this.getClass().getName(), mHandlPressEdge.name());
            CatchEdgeUtil.getOffest(mHandlPressEdge, x, y, left, right, top, bottom, mOffestPoint);
            invalidate();
        }

    }

    private void onActionUp() {
        if (mHandlPressEdge != null) {
            mHandlPressEdge = null;
            invalidate();
        }
    }

    private void onActionMove(float x, float y) {
        if (mHandlPressEdge == null) {
            return;
        }

        x += mOffestPoint.x;
        y += mOffestPoint.y;


        mHandlPressEdge.updateCropWindow(x, y, mBitmapRect);
        invalidate();
    }
}
