package com.sylvan.myworkdemo.banner;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;


public class SwipeCardLayoutManager extends LinearLayoutManager {
    Context context;
    int TRANS_Y_GAP;

    public SwipeCardLayoutManager(Context context) {
        super(context);
    }


    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
        detachAndScrapAttachedViews(recycler);
        int itemCount = getItemCount();//
        for(int i=0;i<itemCount;i++){
            View view=recycler.getViewForPosition(i);
            addView(view);
            measureChildWithMargins(view, 0, 0);
            int widthSpace = getWidth() - getDecoratedMeasuredWidth(view);
            int heightSpace = getWidth() - getDecoratedMeasuredHeight(view);
            layoutDecorated(view,
                    widthSpace / 2,
                    heightSpace / 2,
                    widthSpace / 2 + getDecoratedMeasuredWidth(view),
                    heightSpace / 2 + getDecoratedMeasuredHeight(view));
        }



    }

//    @Override
//    public void scrollToPosition(int position) {
//        if (position > getItemCount() - 1) {
//            Log.i(TAG, "position is " + position + " but itemCount is " + getItemCount());
//            return;
//        }
//        int currPosition = mTotalOffset / mUnit;
//        int distance = (position - currPosition) * mUnit;
//        int dur = computeSettleDuration(Math.abs(distance), 0);
//        brewAndStartAnimator(dur, distance);
//    }

}
