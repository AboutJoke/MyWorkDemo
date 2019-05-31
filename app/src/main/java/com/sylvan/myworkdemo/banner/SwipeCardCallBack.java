package com.sylvan.myworkdemo.banner;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import java.util.List;

public class SwipeCardCallBack extends ItemTouchHelper.SimpleCallback {
    private List<SwipeCardBean> mDatas;
    private UniversalAdapter adapter;
    private RecyclerView mRv;

    public SwipeCardCallBack(List<SwipeCardBean> mDatas, UniversalAdapter adapter, RecyclerView mRv) {
        super(0,
                ItemTouchHelper.LEFT
        );
        this.mDatas = mDatas;
        this.adapter = adapter;
        this.mRv = mRv;
    }

    public SwipeCardCallBack(int dragDirs, int swipeDirs) {
        super(dragDirs, swipeDirs);
    }

    public SwipeCardCallBack() {
        super(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.UP |
                        ItemTouchHelper.RIGHT | ItemTouchHelper.DOWN
        );
    }

    @Override
    public boolean onMove(RecyclerView recyclerView,
                          RecyclerView.ViewHolder viewHolder,
                          RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        SwipeCardBean remove = mDatas.remove(viewHolder.getLayoutPosition());
        mDatas.add(0, remove);
        adapter.notifyDataSetChanged();
        if (onMoveListener != null) {
            onMoveListener.onSwip(mDatas.get(viewHolder.getLayoutPosition()).title);
        }
    }



    @Override
    public float getSwipeThreshold(RecyclerView.ViewHolder viewHolder) {
        return 0.4f;
    }

    @Override
    public float getMoveThreshold(RecyclerView.ViewHolder viewHolder) {
        return 0.4f;
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        double maxDistance = recyclerView.getWidth() * 0.5f;
        double distance = Math.sqrt(dX * dX + dY * dY);
        double fraction = distance / maxDistance;
        if (fraction > 1) {
            fraction = 1;
        }
        int itemcount = recyclerView.getChildCount();
//        Log.d("SwipeCardLayoutManager", dX + "/" + fraction);
        if (onMoveListener != null) {
            onMoveListener.onMove(dX,dY,fraction,viewHolder.getAdapterPosition());
        }

    }

    @Override
    public void onMoved(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, int fromPos, RecyclerView.ViewHolder target, int toPos, int x, int y) {
        super.onMoved(recyclerView, viewHolder, fromPos, target, toPos, x, y);
//        Log.d("SwipeCardLayoutManager", x +"");
    }

    public interface OnMoveListener{
        void onMove(float dx, float dy, double v, int position);
        void onSwip(String position);
    }

    private OnMoveListener onMoveListener;
    public void setListener(OnMoveListener listener) {
        this.onMoveListener=listener;
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        int itemcount = recyclerView.getChildCount();
    }

    @Override
    public boolean canDropOver(RecyclerView recyclerView, RecyclerView.ViewHolder current, RecyclerView.ViewHolder target) {
        return true;
    }



    @Override
    public int interpolateOutOfBoundsScroll(RecyclerView recyclerView, int viewSize, int viewSizeOutOfBounds, int totalSize, long msSinceStartScroll) {
        return super.interpolateOutOfBoundsScroll(recyclerView, viewSize, viewSizeOutOfBounds, totalSize, msSinceStartScroll);
    }
}
