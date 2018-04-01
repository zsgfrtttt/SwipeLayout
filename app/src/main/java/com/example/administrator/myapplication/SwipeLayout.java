package com.example.administrator.myapplication;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.animation.OvershootInterpolator;
import android.widget.LinearLayout;
import android.widget.Scroller;

public class SwipeLayout extends LinearLayout {

    private Scroller mScroller ;

    private float mlastX ,mLastY;
    private int mTouchSlop ;
    /**
     * true:拦截滑动事件  false:父亲处理事件
     */
    private boolean mSwipe;
    /**
     * 不处理后续事件
     */
    private boolean mIntercept;
    /**
     * 滑动的最大距离
     */
    private int mSwipeLength;
    private OnSwipeListener mSwipeListener;

    public SwipeLayout(Context context) {
        this(context,null);
    }

    public SwipeLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SwipeLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        mScroller = new Scroller(getContext(),new OvershootInterpolator(.1f));

        LayoutInflater.from(getContext()).inflate(R.layout.swipe_item,this,true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mSwipeLength = getChildAt(1).getMeasuredWidth();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        int scrollX = getScrollX();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mlastX = x;
                mLastY = y;
                if (!(getParent() instanceof RecyclerView)) {
                   throw new IllegalArgumentException("parent must be recycleView !") ;
                }
                RecyclerView parent = (RecyclerView) getParent();
                int index = parent.findContainingViewHolder(this).getAdapterPosition();

                if (!(parent.getAdapter() instanceof SwipeAdapter)) {
                    throw new IllegalArgumentException("parent must setAdapter by SwipeAdapter !") ;
                }
                SwipeAdapter adapter = (SwipeAdapter) parent.getAdapter();
                if (adapter.getSwipeIndex()!=index) {
                    if (mSwipeListener != null && adapter.getSwipeIndex()!=-1) {
                        mSwipeListener.onShouldCloseLastSwipe();
                        getParent().requestDisallowInterceptTouchEvent(true);
                        mIntercept = true;
                    }
                }else{
                    mSwipe = true;
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                if (mIntercept){
                    return true;
                }
                if (!mSwipe) {
                    //判断是横向滑动还是纵向滑动,横向滑动自己处理，纵向滑动交给父亲处理
                    if (Math.abs(mlastX - x) > Math.abs(mLastY - y) && Math.abs(mlastX - x) > mTouchSlop) {
                        getParent().requestDisallowInterceptTouchEvent(true);
                        mSwipe = true;
                    } else {
                        getParent().requestDisallowInterceptTouchEvent(false);
                        return super.onTouchEvent(event);
                    }
                }

                float newScrollX = scrollX + mlastX - x;
                if (newScrollX < 0) {
                    newScrollX = 0;
                } else if (newScrollX > mSwipeLength) {
                    newScrollX = mSwipeLength;
                }
                scrollTo((int) (newScrollX + 0.5f), 0);
                mlastX = x;
                mLastY = y;
                return true;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                getParent().requestDisallowInterceptTouchEvent(false);
                mSwipe = false ;
                mIntercept = false ;
                if (scrollX > mSwipeLength/2){
                    newScrollX = mSwipeLength;
                    if (mSwipeListener!=null){
                        mSwipeListener.onSwipe(this);
                    }
                }else {
                    newScrollX = 0;
                    if (mSwipeListener!=null){
                        mSwipeListener.onClose();
                    }
                }
                mScroller.startScroll(scrollX,0, (int) (newScrollX - scrollX),0);
                invalidate();
                return super.onTouchEvent(event);
        }

        return super.onTouchEvent(event);
    }


    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            invalidate();
        }
    }

    public void swipe(boolean b){
        if (b){
            scrollTo(mSwipeLength,0);
        }else{
            scrollTo(0,0);
        }
    }

    public void smoothScrollTo(int x,int y){
        mScroller.startScroll(getScrollX(),getScrollY(), x-getScrollX(),y-getScrollY());
        invalidate();
    }

    public interface OnSwipeListener{
        void onSwipe(SwipeLayout layout);
        void onClose();
        void onShouldCloseLastSwipe();
    }

    public void setSwipeListener(OnSwipeListener listener){
        this.mSwipeListener = listener;
    }

}
