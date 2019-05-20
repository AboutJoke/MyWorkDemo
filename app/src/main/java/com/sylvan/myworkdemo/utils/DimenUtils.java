
package com.sylvan.myworkdemo.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import com.sylvan.myworkdemo.MyWorkDemoApplication;

import java.lang.reflect.Field;
import java.util.Locale;

public class DimenUtils {

    public static final int DENSITY_LOW = 120;
    public static final int DENSITY_MEDIUM = 160;
    public static final int DENSITY_HIGH = 240;
    public static final int DENSITY_XHIGH = 320;
    private static DisplayMetrics mMetrics = MyWorkDemoApplication.getInstance().getResources()
            .getDisplayMetrics();

    private static final int DP_TO_PX = TypedValue.COMPLEX_UNIT_DIP;
    private static final int SP_TO_PX = TypedValue.COMPLEX_UNIT_SP;
    private static final int PX_TO_DP = TypedValue.COMPLEX_UNIT_MM + 1;
    private static final int PX_TO_SP = TypedValue.COMPLEX_UNIT_MM + 2;


    public static int getDpi() {
        return mMetrics.densityDpi;
    }

    // -- dimens convert

    private static float applyDimension(int unit, float value, DisplayMetrics metrics) {
        switch (unit) {
            case DP_TO_PX:
            case SP_TO_PX:
                return TypedValue.applyDimension(unit, value, metrics);
            case PX_TO_DP:
                return value / metrics.density;
            case PX_TO_SP:
                return value / metrics.scaledDensity;
        }
        return 0;
    }

    public static int dp2px(float value) {
        return (int) (0.5f + applyDimension(DP_TO_PX, value, mMetrics));
    }

    public static int dip2px(float dpValue) {
        final float scale = MyWorkDemoApplication.getInstance().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int sp2px(float value) {
        return (int) applyDimension(SP_TO_PX, value, mMetrics);
    }

    public static int px2dp(float value) {
        return (int) applyDimension(PX_TO_DP, value, mMetrics);
    }

    public static int px2sp(float value) {
        return (int) applyDimension(PX_TO_SP, value, mMetrics);
    }

    // -- update layout

    public static void createLayout(View view, int w, int h) {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        if (w != -3)
            params.width = w;
        if (h != -3)
            params.height = h;
        view.setLayoutParams(params);
    }

    public static void updateLayout(View view, int w, int h) {
        LayoutParams params = view.getLayoutParams();
        if (params == null)
            return;
        if (w != -3)
            params.width = w;
        if (h != -3)
            params.height = h;
        view.setLayoutParams(params);
    }

    public static void updateLayoutMargin(View view, int l, int t, int r, int b) {
        LayoutParams params = view.getLayoutParams();
        if (params == null)
            return;
        if (params instanceof RelativeLayout.LayoutParams) {
            updateMargin(view, (RelativeLayout.LayoutParams) params, l, t, r, b);
        } else if (params instanceof LinearLayout.LayoutParams) {
            updateMargin(view, (LinearLayout.LayoutParams) params, l, t, r, b);
        } else if (params instanceof FrameLayout.LayoutParams) {
            updateMargin(view, (FrameLayout.LayoutParams) params, l, t, r, b);
        }
    }

    private static void updateMargin(View view, ViewGroup.MarginLayoutParams params, int l, int t,
                                     int r, int b) {
        if (l != -3)
            params.leftMargin = l;
        if (t != -3)
            params.topMargin = t;
        if (r != -3)
            params.rightMargin = r;
        if (b != -3)
            params.bottomMargin = b;
        view.setLayoutParams(params);
    }

    public static void createListviewLayout(View view, int w, int h) {
        ListView.LayoutParams lp = (ListView.LayoutParams) view.getLayoutParams();
        if (lp == null) {
            if (w == -3)
                w = ListView.LayoutParams.MATCH_PARENT;
            if (h == -3)
                h = ListView.LayoutParams.MATCH_PARENT;
            lp = new ListView.LayoutParams(w, h);
            view.setLayoutParams(lp);
        }
    }

    public static void updateRelativeLeftToRight(View leftview, int leftMargin, View rightview,
                                                 int rightMargin, int between) {
        RelativeLayout.LayoutParams lpLeft = (RelativeLayout.LayoutParams) leftview
                .getLayoutParams();
        lpLeft.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        lpLeft.leftMargin = leftMargin;
        lpLeft.rightMargin = between;
        // clear before
        lpLeft.addRule(RelativeLayout.RIGHT_OF, -1);
        leftview.setLayoutParams(lpLeft);

        RelativeLayout.LayoutParams lpRight = (RelativeLayout.LayoutParams) rightview
                .getLayoutParams();
        lpRight.addRule(RelativeLayout.RIGHT_OF, leftview.getId());
        lpRight.rightMargin = rightMargin;
        // clear before
        lpRight.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);
        lpRight.leftMargin = 0;
        rightview.setLayoutParams(lpRight);
    }

    // -- window dimens

    public static boolean isLowDensity() {
        float densityDpi = mMetrics.densityDpi;
        if (densityDpi == DENSITY_LOW || densityDpi == DENSITY_MEDIUM) {
            // Log.d("show", "low density");
            return true;
        }
        return false;
    }

    public static int getStatusBarHeight(Activity activity) {
        Rect rect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        return rect.top;
    }

    public static int getStatusBarHeight2() {

        if (isXiaoMiNavigationGestureEnabled(MyWorkDemoApplication.getInstance())) {
            return 0;
        }
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = MyWorkDemoApplication.getInstance().getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }


    public static int getWindowWidth() {
        return mMetrics.widthPixels;
    }

    public static int getWindowHeight() {
        return mMetrics.heightPixels;
    }



    public static int getWindowWidthForSMG_9500(Context activity){
        DisplayMetrics dm = activity.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    public static int getContentHeightForSMG_9500(Context activity){
        DisplayMetrics dm = activity.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    public static int getContentHeight(Activity activity) {
        int height = mMetrics.heightPixels - getStatusBarHeight(activity);
        return height;
    }

    public static int getContentHeight2() {
        int height = mMetrics.heightPixels - getStatusBarHeight2();
        return height;
    }

    //判断小米手机有没有开启全面屏手势
    public static boolean isXiaoMiNavigationGestureEnabled(Context context) {
        return Settings.Global.getInt(context.getContentResolver(), "force_fsg_nav_bar", 0) != 0 ;
    }

//    public static int getSmartBarHeight(Context context) {
//        try {
//            if(DeviceUtils.isMeizu()){
//                Class c = Class.forName("com.android.internal.R$dimen");
//                Object obj = c.newInstance();
//                Field field = c.getField("mz_action_button_min_height");
//                int height = Integer.parseInt(field.get(obj).toString());
//                return context.getResources().getDimensionPixelSize(height);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return 0;
//    }

    public static int getContentHeight3(Context context) {
        int height = mMetrics.heightPixels - getStatusBarHeight2() - getNavigationHeight(context);
        return height;
    }

    public static int getNavigationHeight(Context context) {
        int resourceId;
        int rid = context.getResources().getIdentifier("config_showNavigationBar", "bool", "android");
        if (rid != 0) {
            resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
            return context.getResources().getDimensionPixelSize(resourceId);
        } else {
            return 0;
        }
    }

    /**
     * 获取当前的布局方向
     *
     * @return {@link android.view.View#LAYOUT_DIRECTION_LTR} or
     * {@link android.view.View#LAYOUT_DIRECTION_RTL}.
     */
    private static int getLayoutDirection() {
//        if (ABTestUtil.isTestGroup2()) {
//            return View.LAYOUT_DIRECTION_LTR;
//        }
        return TextUtils.getLayoutDirectionFromLocale(Locale.getDefault());
    }

    /**
     * 判断布局方向是否是从右到左
     */
    public static boolean isLayoutRTL() {
//        if (ABTestUtil.isTestGroup2()) {
//            return false;
//        }
        return getLayoutDirection() == View.LAYOUT_DIRECTION_RTL;
    }
}
