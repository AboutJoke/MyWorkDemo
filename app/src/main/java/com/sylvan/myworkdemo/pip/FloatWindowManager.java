package com.sylvan.myworkdemo.pip;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

public class FloatWindowManager implements FloatController.ControllerListener {
//    private KewlPlayerVideoHolder mPlayerHolder;
    private FloatView floatView;
    private FloatController controller;
    private boolean isShow=false;
//    private VideoDataInfo mVideoInfo;
    private Context context;


    public void createFloatWindow(Context context){
        this.context=context;

        floatView = new FloatView(context, 0, 0);
        controller=new FloatController(context);

        controller.setControllerListener(this);
//        mPlayerHolder=new KewlPlayerVideoHolder(context,true, IRenderView.AR_ASPECT_FIT_PARENT);
//        mPlayerHolder.setVolume(0, 0);
//        mPlayerHolder.setCallback(this);
//        mPlayerHolder.setLoadingCallback(new IKewlPlayerLoadingCallback() {
//            @Override
//            public void onPlayerLoadingShouldShow(boolean show) {
//                    controller.showLoading(show);
//            }
//        });
    }

    public void add2WindowAndPlay(String VideoInfo){
//        this.mVideoInfo=VideoInfo;
        if (isShow)return;
        removeFloatFromParent();
        floatView.addView(controller);
        floatView.addToWindow();
        isShow=true;
//        controller.setVideoHolder(mPlayerHolder);
//        mPlayerHolder.setVideoPath(VideoInfo.getVideosrc());
//        mPlayerHolder.start();
    }

    private void removeFloatFromParent() {
        ViewParent parent = controller.getParent();
        if (parent != null && parent instanceof ViewGroup) {
            ((ViewGroup) parent).removeView(controller);
        }
    }

    public void start(){
        if (isShow) return;
//        mPlayerHolder.start();
    }

    public void pause(){
        if (isShow) return;
//        mPlayerHolder.pause();
    }

    public void release(){
        if (!isShow) return;
        isShow=false;
        floatView.removeFromWindow();
        removeFloatFromParent();
//        mPlayerHolder.releaseWithoutStop();
    }

    public boolean isShow(){
        return isShow;
    }

//    @Override
//    public void onPlayerPrepared() {
//    }
//
//    @Override
//    public void onPlayerEnd() {
//
//    }
//
//    @Override
//    public void onPlayerError(int what, int extra) {
//
//    }
//
//    @Override
//    public void onPlayerInfo(int what, int extra) {
//
//    }
//
//    @Override
//    public void onPlayerPlayingTick(long current, long total) {
//    }
//
    @Override
    public void fullScreen(View view) {
//        CMVideoPlayerFragment.start(context, mVideoInfo, null, null, CMVideoPlayerFragment.STEM_FROM_GAME_CHANNEL);
    }

    @Override
    public void closeVideo(View view) {
        release();
        FloatVideoController.getController().stopFloatWindow(context);
    }
}
