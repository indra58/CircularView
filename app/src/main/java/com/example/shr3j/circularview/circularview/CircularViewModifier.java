package com.example.shr3j.circularview.circularview;

import android.view.View;
import android.widget.AbsListView;

public class CircularViewModifier extends ViewModifier {
    private static final float ALPHA_RATIO = 0.0015f;
    private static final int CIRCLE_OFFSET = 500;
    private static final float DEGTORAD = 0.017453294f;
    private static final float SCALING_RATIO = 0.001f;
    private static final float TRANSLATION_RATIO = 0.09f;

    void applyToView(View v, AbsListView parent) {
        float halfHeight = ((float) v.getHeight()) * 0.5f;
        float parentHalfHeight = ((float) parent.getHeight()) * 0.5f;
        float y = v.getY();
        float rot = (parentHalfHeight - halfHeight) - y;
        v.setPivotX(0.0f);
        v.setPivotY(halfHeight);
        v.setRotation((-rot) * 0.05f);
        v.setTranslationX((float) (((double) (v.getWidth() / 6)) - (((-Math.cos((double) ((TRANSLATION_RATIO * rot) * DEGTORAD))) + 1.0d) * 500.0d)));
        float scale = 1.0f - (Math.abs((parentHalfHeight - halfHeight) - y) * SCALING_RATIO);
        float alpha = 1.0f - (Math.abs((parentHalfHeight - halfHeight) - y) * ALPHA_RATIO);
        v.setScaleX(scale);
        v.setScaleY(scale);
        v.setAlpha(alpha);
    }
}
