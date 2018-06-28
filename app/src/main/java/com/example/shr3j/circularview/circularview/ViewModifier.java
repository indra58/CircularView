package com.example.shr3j.circularview.circularview;

import android.view.View;
import android.widget.AbsListView;

public abstract class ViewModifier {
    abstract void applyToView(View view, AbsListView absListView);
}