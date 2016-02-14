package com.example.katerina_panina.rssnews;

import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * View holder class.
 *
 * @author Ekaterina Panina panina.k.a@gmail.com
 */
public class ListViewHolder {

    private LinearLayout mTextViewWrap;
    private TextView mTitleTextView;
    private TextView mDescriptionTextView;

    /**
     * Constructor.
     *
     * @param aTitleTextView {@link TextView} title object for view holder.
     * @param aDescriptionTextView {@link TextView} description object for view holder.
     */
    public ListViewHolder(TextView aTitleTextView, TextView aDescriptionTextView) {
        super();
        mTitleTextView = aTitleTextView;
        mDescriptionTextView = aDescriptionTextView;
    }

    /**
     * Gets {@link TextView} title object.
     *
     * @return {@link TextView} object.
     */
    public TextView getTitleTextView() {
        return mTitleTextView;
    }

    /**
     * Gets {@link TextView} description object.
     *
     * @return {@link TextView} object.
     */
    public TextView getDescriptionTextView() {
        return mDescriptionTextView;
    }

    /**
     * Gets {@link LinearLayout} text wrap object.
     *
     * @return {@link LinearLayout} text wrap object.
     */
    public LinearLayout getTextViewWrap() {
        return mTextViewWrap;
    }

    /**
     * Sets {@link LinearLayout} text wrap object.
     *
     * @param aTextViewWrap {@link LinearLayout} text wrap object.
     */
    public void setTextViewWrap(LinearLayout aTextViewWrap) {
        this.mTextViewWrap = aTextViewWrap;
    }
}
