package com.sylvan.myworkdemo.imagepick.helper;

import android.graphics.RectF;
import com.sylvan.myworkdemo.imagepick.edge.Edge;

/**
 * @ClassName: com.sylvan.myworkdemo.imagepick.helper
 * @Author: sylvan
 * @Date: 19-5-10
 * <p>
 * 表示手指在裁剪框的哪里，如四角可以控制裁剪框缩放，四边可以控制裁剪框上下左右缩放，中间可控制裁剪框移动
 */
public enum CropWindowSelector {

    //左上角
    TOP_LEFT(new CropWindowScaleHelper(Edge.TOP, Edge.LEFT)),

    //右上角
    TOP_RIGHT(new CropWindowScaleHelper(Edge.TOP, Edge.RIGHT)),

    //左下角
    BOTTOM_LEFT(new CropWindowScaleHelper(Edge.BOTTOM, Edge.LEFT)),

    //右下角
    BOTTOM_RIGHT(new CropWindowScaleHelper(Edge.BOTTOM, Edge.RIGHT)),

    /**
     * 还可以扩展到四边
     * RIGHT(new CropWindowScaleHelper(null,Edge.Right))
     */

    //中间，控制裁剪框移动
    CENTER(new CropWindowMoveHelper());


    private CropWindowScaleHelper helper;

    CropWindowSelector(CropWindowScaleHelper helper) {
        this.helper = helper;
    }

    public void updateCropWindow(float x, float y, RectF rectF) {
        helper.updateCropWindow(x, y, rectF);
    }
}
