package com.sylvan.myworkdemo;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;

/**
 * @ClassName: MyActivityLifecycle
 * @Author: sylvan
 * @Date: 19-3-19 下午2:04
 */
public class MyActivityLifecycle implements Application.ActivityLifecycleCallbacks {
    private boolean isForeground = false;//应用是否处于前端
    private int activeCount = 0;
    private long startMill;
    private long endMill;

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {
        if (activeCount == 0) {
            startMill = System.currentTimeMillis();
            Log.d("MyActivityLifecycle", "startMill = " + startMill);
        }
        activeCount++;
    }

    @Override
    public void onActivityResumed(Activity activity) {
        isForeground = true;
        Log.d("MyActivityLifecycle", "isForeground = true");
    }

    @Override
    public void onActivityPaused(Activity activity) {
        isForeground = false;
        Log.d("MyActivityLifecycle", "isForeground = false");
    }

    @Override
    public void onActivityStopped(Activity activity) {
        activeCount--;
        if (activeCount == 0) {
            endMill = System.currentTimeMillis();
            Log.d("MyActivityLifecycle", "endMill = " + endMill);
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

    public boolean isForeground() {
        return isForeground;
    }
}
