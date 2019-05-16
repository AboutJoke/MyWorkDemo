package com.sylvan.myworkdemo.imagepick.helper;

import android.graphics.RectF;
import com.sylvan.myworkdemo.imagepick.edge.Edge;

/**
 * @ClassName: com.sylvan.myworkdemo.imagepick.helper
 * @Author: sylvan
 * @Date: 19-5-10
 */
public class CropWindowMoveHelper  extends CropWindowScaleHelper{

    public CropWindowMoveHelper() {
        super(null, null);
    }

    @Override
    public void updateCropWindow(float x, float y, RectF imageRect) {
        super.updateCropWindow(x, y, imageRect);

        //获取裁剪框的四个坐标位置
        float left = Edge.LEFT.getCoordinate();
        float top = Edge.TOP.getCoordinate();
        float right = Edge.RIGHT.getCoordinate();
        float bottom = Edge.BOTTOM.getCoordinate();

        //获取裁剪框的中心位置
        final float currentCenterX = (left + right) / 2;
        final float currentCenterY = (top + bottom) / 2;

        //判断手指移动的距离
        final float offsetX = x - currentCenterX;
        final float offsetY = y - currentCenterY;

        //更新裁剪框四条边的坐标
        Edge.LEFT.offset(offsetX);
        Edge.TOP.offset(offsetY);
        Edge.RIGHT.offset(offsetX);
        Edge.BOTTOM.offset(offsetY);

        //////////////裁剪框越界处理/////////////////

        if (Edge.LEFT.isOutsideMargin(imageRect)) {
            //获取此时x越界时的坐标位置
            float currentCoordinate = Edge.LEFT.getCoordinate();

            //重新指定左边的值为初始值
            Edge.LEFT.initCoordinate(imageRect.left);

            //越界的距离
            float offset = Edge.LEFT.getCoordinate() - currentCoordinate;

            //修正最右边的偏移量
            Edge.RIGHT.offset(offset);
        } else if (Edge.RIGHT.isOutsideMargin(imageRect)) {

            float currentCoordinate = Edge.RIGHT.getCoordinate();

            Edge.RIGHT.initCoordinate(imageRect.right);

            float offset = Edge.RIGHT.getCoordinate() - currentCoordinate;

            Edge.LEFT.offset(offset);
        }


        if (Edge.TOP.isOutsideMargin(imageRect)) {

            float currentCoordinate = Edge.TOP.getCoordinate();

            Edge.TOP.initCoordinate(imageRect.top);

            float offset = Edge.TOP.getCoordinate() - currentCoordinate;

            Edge.BOTTOM.offset(offset);

        } else if (Edge.BOTTOM.isOutsideMargin(imageRect)) {

            float currentCoordinate = Edge.BOTTOM.getCoordinate();

            Edge.BOTTOM.initCoordinate(imageRect.bottom);

            float offset = Edge.BOTTOM.getCoordinate() - currentCoordinate;

            Edge.TOP.offset(offset);
        }
    }
}
