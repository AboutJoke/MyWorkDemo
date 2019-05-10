package com.sylvan.myworkdemo.pip;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.util.Log;

import java.lang.reflect.Method;

public class FloatVideoController {

    private static FloatVideoController controller;
    private FloatCallBack mFloatCallBack;
    private static String TAG="FloatVideoController";

    public static FloatVideoController getController() {
        if (controller == null) {
            synchronized (FloatVideoController.class) {
                if (controller == null) {
                    controller = new FloatVideoController();
                }
            }
        }
        return controller;
    }

    private FloatVideoController() {
    }

    public void registerCallLittleMonk(FloatCallBack callLittleMonk) {
        mFloatCallBack = callLittleMonk;
    }

    public void startFloatWindow(Context context, String videoDataInfo) {
        Intent intent = new Intent(context, FloatVideoService.class);
        intent.putExtra("VideoData", videoDataInfo);
        context.startService(intent);
    }

    public void stopFloatWindow(Context context) {
        Intent intent = new Intent(context, FloatVideoService.class);
        context.stopService(intent);
    }

    public void removeFloatWindow() {
        if (mFloatCallBack == null) return;
        mFloatCallBack.release();
    }

    public boolean isShow() {
        return mFloatCallBack != null && mFloatCallBack.isShow();
    }

    public void start() {
        if (mFloatCallBack == null) return;
        mFloatCallBack.start();
    }

    public boolean checkOp(Context context, int op) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 19) {
            AppOpsManager manager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            try {
                Class clazz = AppOpsManager.class;
                Method method = clazz.getDeclaredMethod("checkOp", int.class, int.class, String.class);
                return AppOpsManager.MODE_ALLOWED == (int)method.invoke(manager, op, Binder.getCallingUid(), context.getPackageName());
            } catch (Exception e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }
        } else {
            Log.e(TAG, "Below API 19 cannot invoke!");
        }
        return false;
    }
}
