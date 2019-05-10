package com.sylvan.myworkdemo;

import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.VideoView;
import com.sylvan.myworkdemo.wiget.PorgressLayout;

import java.io.IOException;
import java.lang.ref.WeakReference;

public class ProgressActivity extends AppCompatActivity {
    private PorgressLayout mProgressBar;
    protected MediaPlayer mMediaPlayer;
    protected SurfaceHolder mSurfaceHolder;
//    protected SurfaceHolderCallback mSurfaceCallback;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_progress_layout);
        initView();
    }

    private void initView() {
        mProgressBar = (PorgressLayout) findViewById(R.id.progress_bar);
        mProgressBar.setProgres(50);
//        mProgressBar.setText("100/1000");


//        mSurfaceCallback = new SurfaceHolderCallback(this);
        mMediaPlayer = new MediaPlayer();
        VideoView videoView = findViewById(R.id.video);
//        String uri = "android.resource://" + getPackageName() + "/" + ;
//        AssetFileDescriptor afd = null;
//        try {
//            afd = getAssets().openFd("flash/video_before_splash.mp4");
//            videoView.setVideoPath(afd.);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }



        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.bg_movie);
        mSurfaceHolder = surfaceView.getHolder();
        mSurfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

                AssetFileDescriptor afd = null;
                try {
                    afd = getAssets().openFd("flash/video_before_splash.mp4");
                    mMediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                    mMediaPlayer.prepare();
                    mMediaPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });

    }

//    private static class SurfaceHolderCallback implements SurfaceHolder.Callback {
//
//        private WeakReference<ProgressActivity> mActRef;
//
//        public SurfaceHolderCallback(ProgressActivity act) {
//            mActRef = new WeakReference<>(act);
//        }
//
//        @Override
//        public void surfaceCreated(SurfaceHolder holder) {
//            ProgressActivity act = null;
//            if (mActRef != null) {
//                act = mActRef.get();
//            }
//
//            if (act == null)
//                return;
//
//            MediaPlayer player = act.mMediaPlayer;
//            try {
//                if (player != null) {
//                    // 设置显示视频显示在SurfaceView上, 必须在Surface创建后才能初始化MediaPlayer,否则不会显示图像
//                    player.reset();
//                    player.setAudioStreamType(AudioManager.STREAM_MUSIC);
//                    player.setDisplay(act.mSurfaceHolder);
//                    player.setVolume(0, 0);
//                    player.setLooping(true);
//                    AssetFileDescriptor afd = act.getAssets().openFd("flash/video_before_splash.mp4");
//                    if (afd == null) {
//                        return;
//                    }
//                    player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
//                    player.prepare();
//                    player.start();
////                    act.isMediaPaused = false;
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        @Override
//        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
//
//        }
//
//        @Override
//        public void surfaceDestroyed(SurfaceHolder holder) {
//            ProgressActivity act = null;
//            if (mActRef != null) {
//                act = mActRef.get();
//            }
//
//            if (act == null)
//                return;
//
//            MediaPlayer player = act.mMediaPlayer;
//
//            try {
//                if (player != null) {
//                    if (player.isPlaying()) {
//                        player.stop();
//                    }
//                }
//            } catch (IllegalStateException e) {
//                e.printStackTrace();
//            }
//            //mMediaPlayer.release();
//        }
//    }
}
