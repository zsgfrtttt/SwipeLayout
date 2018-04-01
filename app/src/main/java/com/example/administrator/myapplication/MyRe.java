package com.example.administrator.myapplication;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

public class MyRe extends RecyclerView {
    public MyRe(Context context) {
        super(context);
    }

    public MyRe(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRe(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        boolean b=super.dispatchTouchEvent(ev);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Log.i("csz","   Rv   dispatchTouchEvent  down   "+b+"\n");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i("csz","   Rv   dispatchTouchEvent  move   "+b+"\n");
                break;
            case MotionEvent.ACTION_UP:
                Log.i("csz","   Rv   dispatchTouchEvent  up   "+b+"\n");
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.i("csz","   Rv   dispatchTouchEvent  cancel   "+b+"\n");
                break;
        }
        return b;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        boolean b=super.onInterceptTouchEvent(ev);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Log.i("csz","   Rv   onInterceptTouchEvent  down   "+b+"\n");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i("csz","   Rv   onInterceptTouchEvent  move   "+b+"\n");
                break;
            case MotionEvent.ACTION_UP:
                Log.i("csz","   Rv   onInterceptTouchEvent  up   "+b+"\n");
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.i("csz","   Rv   onInterceptTouchEvent  cancel   "+b+"\n");
                break;
        }
        return b;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        boolean b=super.onTouchEvent(ev);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Log.i("csz","   Rv   onTouchEvent  down   "+b+"\n");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i("csz","   Rv   onTouchEvent  move   "+b+"\n");
                break;
            case MotionEvent.ACTION_UP:
                Log.i("csz","   Rv   onTouchEvent  up   "+b+"\n");
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.i("csz","   Rv   onTouchEvent  cancel   "+b+"\n");
                break;
        }
        return b;
    }
}
