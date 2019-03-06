package com.sylvan.myworkdemo.wiget;

import android.annotation.TargetApi;
import android.graphics.Outline;
import android.graphics.RectF;
import android.os.Build;
import android.view.View;
import android.view.ViewOutlineProvider;

/**
 * @ClassName: ClipOutlineProvider
 * @Author: sylvan
 * @Date: 19-2-26 下午3:39
 */
//@TargetApi(Build.VERSION_CODES.LOLLIPOP)
//public class ClipOutlineProvider extends ViewOutlineProvider {
//    @Override
//    public void getOutline(View view, Outline outline) {
//        final int margin = Math.min(view.getWidth(), view.getHeight()) / 10;
////        outline.setRoundRect(margin, margin, view.getWidth() - margin,
////                view.getHeight() - margin, margin / 2);
////        outline.setRoundRect(new RectF(0, 0, mWidth, mHeight), mRadii);
//    }
//}