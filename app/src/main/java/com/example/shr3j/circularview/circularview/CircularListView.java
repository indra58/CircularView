package com.example.shr3j.circularview.circularview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

public class CircularListView extends ListView implements AbsListView.OnScrollListener {
    private static final int DEFAULT_SCROLL_DURATION = 200;
    public static final int DEFAULT_SELECTION = 1073741823;
    private static final String TAG = "CircularListView";
    private final CenterRunnable mCenterRunnable = new CenterRunnable();
    private boolean mIsForceCentering;
    private OnItemCenteredListener mOnItemCenteredListener;
    private int mScrollDuration = DEFAULT_SCROLL_DURATION;
    private ViewModifier mViewModifier;

    public class CenterRunnable implements Runnable {
        private View mView;

        public void setView(View v) {
            this.mView = v;
        }

        public void run() {
            CircularListView.this.smoothScrollToView(this.mView);
            CircularListView.this.mIsForceCentering = true;
        }
    }

    public interface OnItemCenteredListener {
        void onItemCentered(View view);
    }

    public CircularListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        super.setOnScrollListener(this);
        setOverscrollFooter(null);
        setOverscrollHeader(null);
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        setSelection(DEFAULT_SELECTION);
    }

    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (this.mViewModifier != null) {
            int count = getChildCount();
            for (int i = 0; i < count; i++) {
                this.mViewModifier.applyToView(getChildAt(i), this);
            }
        }
    }

    public void setViewModifier(ViewModifier modifier) {
        this.mViewModifier = modifier;
    }

    public void setScrollDuration(int duration) {
        this.mScrollDuration = duration;
    }

    public void setOnItemCenteredListener(OnItemCenteredListener listener) {
        this.mOnItemCenteredListener = listener;
    }

    @SuppressLint({"NewApi"})
    public void smoothScrollToView(View v) {
        smoothScrollBy((int) ((v.getY() + (((float) v.getHeight()) * 0.5f)) - (((float) getHeight()) * 0.5f)), this.mScrollDuration);
    }

    public void setOnScrollListener(OnScrollListener l) {
        throw new UnsupportedOperationException();
    }

    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == 0 && !this.mIsForceCentering) {
            this.mIsForceCentering = true;
            View childView = findViewAtCenter();
            if (childView != null) {
                if (this.mOnItemCenteredListener != null) {
                    this.mOnItemCenteredListener.onItemCentered(childView);
                }
                this.mCenterRunnable.setView(childView);
            }
        }
    }

    public boolean onTouchEvent(MotionEvent ev) {
        removeCallbacks(this.mCenterRunnable);
        this.mIsForceCentering = false;
        return super.onTouchEvent(ev);
    }

    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
    }

    public View findViewAt(int x, int y) {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View v = getChildAt(i);
            int x0 = v.getLeft();
            int y0 = v.getTop();
            int x1 = v.getWidth() - x0;
            int y1 = v.getHeight() + y0;
            if (x >= x0 && x <= x1 && y >= y0 && y <= y1) {
                return v;
            }
        }
        return null;
    }

    public View findViewAtCenter() {
        return findViewAt(0, getHeight() / 2);
    }
}
