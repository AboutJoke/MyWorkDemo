package com.sylvan.myworkdemo.utils;

import android.graphics.PointF;
import com.sylvan.myworkdemo.imagepick.helper.CropWindowSelector;

/**
 * @ClassName: com.sylvan.myworkdemo.utils
 * @Author: sylvan
 * @Date: 19-5-15
 */
public class CatchEdgeUtil {

    public static CropWindowSelector getPressedHandle(
            float x, float y, float left, float top, float right, float bottom, int targetRadius) {
        CropWindowSelector handleEdge = null;
        float nearestDistance = Float.POSITIVE_INFINITY;

        // hands in top left conner
        float distanceLeftTop = calculateDistance(x, y, left, top);
        if (distanceLeftTop < nearestDistance) {
            nearestDistance = distanceLeftTop;
            handleEdge = CropWindowSelector.TOP_LEFT;
        }

        float distanceRightTop = calculateDistance(x, y, right, top);
        if (distanceRightTop < nearestDistance) {
            nearestDistance = distanceRightTop;
            handleEdge = CropWindowSelector.TOP_RIGHT;
        }

        float distanceLeftBottom = calculateDistance(x, y, left, bottom);
        if (distanceLeftBottom < nearestDistance) {
            nearestDistance = distanceLeftBottom;
            handleEdge = CropWindowSelector.BOTTOM_LEFT;
        }

        float distanceRightBottom = calculateDistance(x, y, right, bottom);
        if (distanceRightBottom < nearestDistance) {
            nearestDistance = distanceRightBottom;
            handleEdge = CropWindowSelector.BOTTOM_RIGHT;
        }

        if (nearestDistance <= targetRadius) {
            return handleEdge;
        }

//        if (isHorizontalTarget(x, y, left, right, top, targetRadius)) {
//            handleEdge = CropWindowSelector.to
//        }

        if (isInRect(x, y, left, right, top, bottom)) {
            return CropWindowSelector.CENTER;
        }

        return null;
    }

    /**
     * 计算两个坐标之间的距离
     *
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    private static float calculateDistance(float x1, float y1, float x2, float y2) {
        float x = x2 - x1;
        float y = y2 - y1;

        return (float) Math.sqrt((x * x) + (y * y));
    }

    private static boolean isHorizontalTarget(
            float x, float y, float handleXStart, float handleXEnd, float handleY, int targetRadius) {
        return (x > handleXStart && x < handleXEnd && Math.abs(y - handleY) <= targetRadius);
    }

    private static boolean isVerticalTarget(
            float x, float y, float handleX, float handleYStart, float handleYEnd, float targetRadius) {
        return Math.abs(x - handleX) <= targetRadius && y > handleYStart && y < handleYEnd;
    }

    private static boolean isInRect(float x, float y, float left, float right, float top, float bottom) {
        return x >= left && x <= right && y >= top && y <= bottom;
    }

    public static void getOffest(CropWindowSelector cropWindowEdgeSelector,
                                  float x, float y,
                                  float left, float right,
                                  float top, float bottom,
                                  PointF touchOffsetOutput) {
        float touchOffsetX = 0;
        float touchOffsetY = 0;

        switch (cropWindowEdgeSelector) {

            case TOP_LEFT:
                touchOffsetX = left - x;
                touchOffsetY = top - y;
                break;
            case TOP_RIGHT:
                touchOffsetX = right - x;
                touchOffsetY = top - y;
                break;
            case BOTTOM_LEFT:
                touchOffsetX = left - x;
                touchOffsetY = bottom - y;
                break;
            case BOTTOM_RIGHT:
                touchOffsetX = right - x;
                touchOffsetY = bottom - y;
                break;
//            case LEFT:
//                touchOffsetX = left - x;
//                touchOffsetY = 0;
//                break;
//            case TOP:
//                touchOffsetX = 0;
//                touchOffsetY = top - y;
//                break;
//            case RIGHT:
//                touchOffsetX = right - x;
//                touchOffsetY = 0;
//                break;
//            case BOTTOM:
//                touchOffsetX = 0;
//                touchOffsetY = bottom - y;
//                break;
            case CENTER:
                final float centerX = (right + left) / 2;
                final float centerY = (top + bottom) / 2;
                touchOffsetX = centerX - x;
                touchOffsetY = centerY - y;
                break;
        }

        touchOffsetOutput.x = touchOffsetX;
        touchOffsetOutput.y = touchOffsetY;
    }
}
