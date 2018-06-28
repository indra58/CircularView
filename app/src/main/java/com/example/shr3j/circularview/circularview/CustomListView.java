package com.example.shr3j.circularview.circularview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class CustomListView extends ListView {
    private static final float OVERSCROLL_THRESHOLD_IN_PIXELS = 100.0f;
    private float downY;

    public CustomListView(Context context) {
        super(context);
        onCreate(context, null, null);
    }

    public CustomListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        onCreate(context, attrs, null);
    }

    public CustomListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        onCreate(context, attrs, Integer.valueOf(defStyleAttr));
    }

    private void onCreate(Context context, AttributeSet attrs, Integer defStyle) {
    }
}
