package com.example.shr3j.circularview.recyclercircularview;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public class CircularHorizontalMode implements ItemViewMode {

    private int mCircleOffset = 500;
//    private float mDegToRad = 1.0f / 180.0f * (float) Math.PI;
    private float mDegToRad = 0.017453294f;
    private float mScalingRatio = 0.001f;
//    private float mTranslationRatio = 0.15f;
    private float mTranslationRatio = 0.09f;

    public CircularHorizontalMode() {
    }

    public CircularHorizontalMode(int circleOffset, float degToRad, float scalingRatio, float translationRatio) {
        mCircleOffset = circleOffset;
        mDegToRad = degToRad;
        mScalingRatio = scalingRatio;
        mTranslationRatio = translationRatio;

    }


    @Override
    public void applyToView(View v, RecyclerView parent) {
        float halfWidth = v.getWidth() * 0.5f;
        float parentHalfWidth = parent.getWidth() * 0.5f;
        float x = v.getX();
        float rot = parentHalfWidth - halfWidth - x;

        v.setPivotY(0.0f);
        v.setPivotX(halfWidth);
        v.setRotation(-rot * 0.05f);
        v.setTranslationY( (float) (-Math.cos(rot * mTranslationRatio * mDegToRad) + 1) * mCircleOffset);

        float scale = 1.0f - Math.abs(parentHalfWidth - halfWidth - x) * mScalingRatio;
        v.setScaleX(scale);
        v.setScaleY(scale);
    }
}
