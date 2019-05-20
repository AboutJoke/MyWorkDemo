package com.sylvan.myworkdemo;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.sylvan.myworkdemo.imagepick.CropImageView;

/**
 * @ClassName: com.sylvan.myworkdemo
 *
 * 受Android版本影响，不同版本号得到bitmap的dpi不一样会导致计算不一致从而错误出现错误，
 * compileSdkVersion 25
 *     buildToolsVersion '25.0.3'
 *
 *     defaultConfig {
 *         applicationId "cn.com.gyq.crop"
 *         minSdkVersion 14
 *         targetSdkVersion 19
 *         versionCode 1
 *         versionName "1.0"
 *         testInstrumentationRunner "android.support.test.runner.AndroidJUnitRunner"
 *     }
 *     此版本号下表现正常
 * @Author: sylvan
 * @Date: 19-5-10
 */
public class ImagePickActivity extends AppCompatActivity {
    CropImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_image_pick);
        imageView = findViewById(R.id.crop_image);
    }

    public void Crop(View view) {
        Bitmap croppedImage = imageView.getCroppedImage();
        imageView.setImageBitmap(croppedImage);
    }
}
