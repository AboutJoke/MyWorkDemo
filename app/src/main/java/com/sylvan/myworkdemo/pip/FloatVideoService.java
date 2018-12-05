package com.sylvan.myworkdemo.pip;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class FloatVideoService extends Service implements FloatCallBack {

    FloatWindowManager manager;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        FloatVideoController.getController().registerCallLittleMonk(this);
        initWindow();
    }

    private void initWindow() {
        manager = new FloatWindowManager();
        manager.createFloatWindow(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        release();
//        VideoDataInfo VideoInfo = intent.getParcelableExtra("VideoData");
        manager.add2WindowAndPlay(null);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (manager == null) return;
        manager.release();
    }

    @Override
    public void start() {
        if (manager == null) return;
        manager.start();
    }

    @Override
    public void pause() {
        if (manager == null) return;
        manager.pause();
    }

    @Override
    public void release() {
        if (manager == null) return;
        manager.release();
    }

    @Override
    public boolean isShow() {
        return manager != null && manager.isShow();
    }
}
