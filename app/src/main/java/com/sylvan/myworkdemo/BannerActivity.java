package com.sylvan.myworkdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.sylvan.myworkdemo.banner.*;

import java.util.ArrayList;

/**
 * @ClassName: com.sylvan.myworkdemo
 * @Author: sylvan
 * @Date: 19-5-31
 */
public class BannerActivity extends AppCompatActivity {

    private RecyclerView mActivity_review;
    private UniversalAdapter mAdatper;
    private ArrayList<SwipeCardBean> mList;
    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;
    private RelativeLayout image_layout;

    int i;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_banner);

        initView();
        initData();
        setData();
        i = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
    }

    private void initView() {
        mList = new ArrayList<>();
        mActivity_review = (RecyclerView) findViewById(R.id.activity_review);
        imageView1= (ImageView) findViewById(R.id.image1);
        imageView2= (ImageView) findViewById(R.id.image2);
        imageView3= (ImageView) findViewById(R.id.image3);
        image_layout = (RelativeLayout) findViewById(R.id.image_layout);

        mActivity_review.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });



        mActivity_review.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d("setOnTouchListener",event.getX()+"/" + event.getY());
                return false;
            }
        });
        mActivity_review.postDelayed(new Runnable() {
            @Override
            public void run() {
                mActivity_review.scrollToPosition(4);
            }
        },5000);
    }

    private void initData() {
        int[] intimage = {R.drawable.k_diamond, R.drawable.k_diamond, R.drawable.k_diamond, R.drawable.k_diamond,
                R.drawable.k_diamond, R.drawable.k_diamond, R.drawable.k_diamond, R.drawable.k_diamond};
        for (int i = 0; i < 8; i++) {
            SwipeCardBean swpe = new SwipeCardBean();
            swpe.resoutimage = intimage[i];
            swpe.title = "" + i;
            mList.add(swpe);
        }
    }

    SwipeCardLayoutManager swmanamger;
    private void setData() {
        swmanamger = new SwipeCardLayoutManager(this);
        mActivity_review.setLayoutManager(swmanamger);
//        Config config = new Config();
//        config.secondaryScale = 0.8f;
//        config.scaleRatio = 0.4f;
//        config.maxStackCount = 4;
//        config.initialStackCount = mList.size() - 1;
//        config.space = 15;
//        config.align = Align.LEFT;
//        StackLayoutManager manager = new StackLayoutManager(config);
//        mActivity_review.setLayoutManager(manager);
//        MyLinearLayout myLinearLayout = new MyLinearLayout(this);
//        mActivity_review.setLayoutManager(myLinearLayout);

//        Collections.reverse(mList);
        mAdatper = new UniversalAdapter(mList, this);
        mActivity_review.setAdapter(mAdatper);
        CardConfig.initConfig(this);
        ItemTouchHelper.Callback callback=new SwipeCardCallBack(mList,mAdatper,mActivity_review);
        final ItemTouchHelper helper=new ItemTouchHelper(callback);
        helper.attachToRecyclerView(mActivity_review);
        ((SwipeCardCallBack) callback).setListener(new SwipeCardCallBack.OnMoveListener() {
            @Override
            public void onMove(float dx, float dy, double v, int position) {
                int childCount = image_layout.getChildCount();
                View childAt = image_layout.getChildAt(childCount - 1);
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) childAt.getLayoutParams();
                int leftMargin = layoutParams.leftMargin;
                if (leftMargin > 0){
                    childAt.setTranslationX(-(float) ((v * 150) + i) );
                } else {
                    childAt.setTranslationX(-(float) ((v * 150)) );
                }
                double v1 = v * i;
                if (v1 < 100) {
                    View childA2t = image_layout.getChildAt(childCount - 2);
                    childA2t.setTranslationX(-(float) v1);
                }
            }

            @Override
            public void onSwip( String position) {
//                Log.d("SwipeCardLayoutManager",position+"/");
                int childCount = image_layout.getChildCount();
                View childAt = image_layout.getChildAt(childCount - 1);
//                View childAt1 = image_layout.getChildAt(0);

                image_layout.removeView(childAt);
                childAt.setTranslationX(0);
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) childAt.getLayoutParams();
                layoutParams.leftMargin = i;
                childAt.setLayoutParams(layoutParams);
                image_layout.addView(childAt, 0);
                childAt.setVisibility(View.VISIBLE);
            }
        });

    }


}

