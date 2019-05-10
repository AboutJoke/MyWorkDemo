package com.sylvan.myworkdemo.imagepick.helper;

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

    public void updateCropWindow() {

    }
}
