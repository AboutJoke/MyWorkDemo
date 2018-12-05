package com.sylvan.myworkdemo.pip;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.sylvan.myworkdemo.R;

public class PipViewActivity  extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_pip);
        findViewById(R.id.show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FloatVideoController.getController().checkOp(PipViewActivity.this,24)){
                    FloatVideoController.getController().startFloatWindow(PipViewActivity.this, null);
                }
            }
        });

    }
}
