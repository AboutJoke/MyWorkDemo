package com.sylvan.myworkdemo.imagepick.edge;

import android.graphics.RectF;

/**
 * @ClassName: com.sylvan.myworkdemo.imagepick.edge
 * @Author: sylvan
 * @Date: 19-5-10
 */
public enum Edge { //裁剪框的四个角的坐标
    LEFT,
    TOP,
    RIGHT,
    BOTTOM;

    //裁剪框最小高度
    static final int MIN_CROP_HEIGHT = 80;

    //上下左右边界的坐标
    float mCoordinate;

    public void initCoordinate(float coordinate) {
        mCoordinate = coordinate;
    }

    public float getCoordinate() {
        return mCoordinate;
    }

    /**
     * 随着手指的移动而改变的坐标
     *
     * @param distance
     */
    public void offset(float distance) {
        mCoordinate += distance;
    }

    public void updateEdge(float x, float y, RectF imageRect) {
        switch (this) {
            case TOP:
                mCoordinate = adjustTop(y, imageRect);
                break;
            case LEFT:
                mCoordinate = adjustLeft(x, imageRect);
                break;
            case RIGHT:
                mCoordinate = adjustRight(x, imageRect);
                break;
            case BOTTOM:
                mCoordinate = adjustBottom(y, imageRect);
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
        return Edge.RIGHT.getCoordinate() - Edge.LEFT.getCoordinate();
    }

    /**
     * 获取裁剪框的高
     *
     * @return
     */
    public static float getHeight() {
        return Edge.BOTTOM.getCoordinate() - Edge.TOP.getCoordinate();
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
                result = mCoordinate - imageRect.top < 0;
                break;
            case LEFT:
                result = mCoordinate - imageRect.left < 0;
                break;
            case RIGHT:
                result = imageRect.right - mCoordinate < 0;
                break;
            case BOTTOM:
                result = imageRect.bottom - mCoordinate < 0;
                break;
        }
        return result;
    }
}
