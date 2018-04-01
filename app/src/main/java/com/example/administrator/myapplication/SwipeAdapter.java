package com.example.administrator.myapplication;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

public class SwipeAdapter extends BaseQuickAdapter<String, SwipeAdapter.MyHolder> implements SwipeLayout.OnSwipeListener {

    private SwipeLayout mSwipeLayout;
    /**
     * 当前打开的item的下标
     */
    private int mSwipeIndex = -1;

    private int mLastAnimPos = 12;

    public SwipeAdapter(@Nullable List<String> data) {
        super(R.layout.item, data);
    }

    @Override
    protected void convert(MyHolder helper, String item) {
        helper.setText(R.id.tv_text, item);
        SwipeLayout layout = (SwipeLayout) helper.itemView;
        layout.setSwipeListener(this);

        layout.swipe(false);
        helper.addOnClickListener(R.id.tv_del);
        helper.addOnClickListener(R.id.tv_top);

    }

    @Override
    public void onViewAttachedToWindow(MyHolder holder) {
        super.onViewAttachedToWindow(holder);
        for (int i = 0; i < getRecyclerView().getChildCount(); i++) {
            getRecyclerView().getChildAt(i).clearAnimation();
        }
        if (holder.getAdapterPosition()> mLastAnimPos) {
            mLastAnimPos = holder.getAdapterPosition();
            holder.itemView.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(), android.R.anim.slide_in_left));
        }
    }

    @Override
    public void onSwipe(SwipeLayout layout) {
        this.mSwipeLayout = layout;
        mSwipeIndex = getRecyclerView().getChildAdapterPosition(this.mSwipeLayout);

    }

    @Override
    public void onClose() {
        mSwipeIndex = -1;
    }

    @Override
    public void onShouldCloseLastSwipe() {
        mSwipeIndex = -1;
        if (mSwipeLayout != null) {
            mSwipeLayout.smoothScrollTo(0, 0);
        }
    }

    public int getSwipeIndex() {
        return mSwipeIndex;
    }

    public static class MyHolder extends BaseViewHolder {

        public MyHolder(View view) {
            super(view);
        }
    }
}
