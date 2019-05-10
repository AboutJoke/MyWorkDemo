package com.sylvan.myworkdemo.utils;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;

public class DrawableUtils {

    private static final String TAG = "DrawableUtils";

    /**
     * 动态生成一个带有圆角的背景
     *
     * @param topLeft     左上角半径
     * @param topRight    右上角半径
     * @param bottomRight 右下角半径
     * @param bottomLeft  左下角半径
     * @param color       背景色
     * @return
     */
    public static Drawable getBackgroundDrawable(float topLeft, float topRight, float bottomRight, float bottomLeft, int color) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        float[] radii = new float[]{topLeft, topLeft, topRight, topRight, bottomRight, bottomRight, bottomLeft, bottomLeft};
        gradientDrawable.setCornerRadii(radii);
        gradientDrawable.setColor(color);
        return gradientDrawable;
    }

    /**
     * 动态生成一个带有渐变色圆角的背景
     *
     * @param topLeft     左上角半径
     * @param topRight    右上角半径
     * @param bottomRight 右下角半径
     * @param bottomLeft  左下角半径
     * @param colors      渐变背景色
     * @return
     */
    public static Drawable getBackgroundGradientDrawable(float topLeft, float topRight, float bottomRight, float bottomLeft, @NonNull String colors) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        float[] radii = new float[]{topLeft, topLeft, topRight, topRight, bottomRight, bottomRight, bottomLeft, bottomLeft};
        gradientDrawable.setCornerRadii(radii);
        gradientDrawable.setOrientation(GradientDrawable.Orientation.BL_TR); // 渐变色方向从左到右
        String[] split = colors.split(",");
        try {
            if (split.length == 1) {
                gradientDrawable.setColor(Color.parseColor(split[0].trim()));
            } else {
                int colorArray[] = new int[split.length];
                for (int i = 0; i < split.length; i++) {
                    colorArray[i] = Color.parseColor(split[i].trim());
                }
                gradientDrawable.setColors(colorArray);
            }
        } catch (Exception e) {
            gradientDrawable.setColor(Color.parseColor("#99000000"));
            e.printStackTrace();
        }
        return gradientDrawable;
    }
}