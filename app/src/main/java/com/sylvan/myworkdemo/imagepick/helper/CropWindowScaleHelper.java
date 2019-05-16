package com.sylvan.myworkdemo.imagepick.helper;

import android.graphics.RectF;
import com.sylvan.myworkdemo.imagepick.edge.Edge;

/**裁剪框缩放的帮助类
 * @ClassName: com.sylvan.myworkdemo.imagepick.helper
 * @Author: sylvan
 * @Date: 19-5-10
 */
public class CropWindowScaleHelper {

    private Edge mHorizontalEdge;
    private Edge mVerticalEdge;

    public CropWindowScaleHelper(Edge mHorizontalEdge, Edge mVerticalEdge) {
        this.mHorizontalEdge = mHorizontalEdge;
        this.mVerticalEdge = mVerticalEdge;
    }

    /**
     * 随着手指的移动而更新边的坐标
     * @param x
     * @param y
     * @param imageRect
     */
    public void updateCropWindow(float x, float y, RectF imageRect) {
        if (mHorizontalEdge != null) {
            mHorizontalEdge.updateEdge(x, y, imageRect);
        }

        if (mVerticalEdge != null) {
            mVerticalEdge.updateEdge(x, y, imageRect);
        }
    }
}
