package com.example.katerina_panina.rssnews;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.AbsListView;

/**
 * Class for creating animation for news list.
 *
 * @author Ekaterina Panina panina.k.a@gmail.com
 */
public class ExpandingAnimation extends Animation {

    /**
     * Animation duration period.
     */
    private static final int ANIMATION_DURATION = 200;

    private View mView;
    private float mToHeight;
    private float mFromHeight;

    /**
     * Constructor.
     *
     * @param aView {@link View} to be updated.
     * @param aFromHeight start height to animate.
     * @param aToHeight end height to animate.
     */
    public ExpandingAnimation(View aView, float aFromHeight, float aToHeight) {
        mToHeight = aToHeight;
        mFromHeight = aFromHeight;
        mView = aView;
        setDuration(ANIMATION_DURATION);
    }

    @Override
    protected void applyTransformation(float aInterpolatedTime, Transformation aTransformation) {
        float height = (mToHeight - mFromHeight) * aInterpolatedTime + mFromHeight;
        AbsListView.LayoutParams layoutParams = (AbsListView.LayoutParams) mView.getLayoutParams();
        layoutParams.height = (int) height;
        mView.setLayoutParams(layoutParams);
    }
}