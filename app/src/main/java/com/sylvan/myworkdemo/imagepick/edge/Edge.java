package com.sylvan.myworkdemo.imagepick.edge;

import android.graphics.RectF;

/**
 * @ClassName: com.sylvan.myworkdemo.imagepick.edge
 * @Author: sylvan
 * @Date: 19-5-10
 */
public enum Edge {
    LEFT,
    TOP,
    RIGHT,
    BOTTOM;

    //裁剪框最小高度
    static final int MIN_CROP_HEIGHT = 80;
    float mBoundary;

    public void initBoundary(int boundary) {
        mBoundary = boundary;
    }

    public float getBoundary() {
        return mBoundary;
    }

    /**
     * 随着手指的移动而改变的坐标
     *
     * @param distance
     */
    public void offset(float distance) {
        mBoundary += distance;
    }

    public void updateEdge(float x, float y, RectF imageRect) {
        switch (this) {
            case TOP:
                mBoundary = adjustTop(y, imageRect);
                break;
            case LEFT:
                mBoundary = adjustLeft(x, imageRect);
                break;
            case RIGHT:
                mBoundary = adjustRight(x, imageRect);
                break;
            case BOTTOM:
                mBoundary = adjustBottom(y, imageRect);
                break;
        }
    }

    public static float adjustLeft(float x, RectF imageRect) {
        return x;
    }

    public static float adjustRight(float x, RectF imageRect) {
        return x;
    }

    public static float adjustTop(float y, RectF imageRect) {
        return y;
    }

    public static float adjustBottom(float y, RectF imageRect) {
        return y;
    }

    /**
     * 获取裁剪框的宽
     *
     * @return
     */
    public static float getWidth() {
        return Edge.RIGHT.getBoundary() - Edge.LEFT.getBoundary();
    }

    /**
     * 获取裁剪框的高
     *
     * @return
     */
    public static float getHeight() {
        return Edge.BOTTOM.getBoundary() - Edge.TOP.getBoundary();
    }

    /**
     * 判断裁剪框是否超出图片的边界
     *
     * @param imageRect
     * @return
     */
    public boolean isOutsideMargin(RectF imageRect) {
        boolean result = false;
        switch (this) {
            case TOP:
                result = mBoundary - imageRect.top < 0;
                break;
            case LEFT:
                result = mBoundary - imageRect.left < 0;
                break;
            case RIGHT:
                result = imageRect.right - mBoundary < 0;
                break;
            case BOTTOM:
                result = imageRect.bottom - mBoundary < 0;
                break;
        }
        return result;
    }
}
