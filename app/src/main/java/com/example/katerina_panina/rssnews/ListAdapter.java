package com.example.katerina_panina.rssnews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Custom list adapter.
 *
 * @author Ekaterina Panina panina.k.a@gmail.com
 */
public class ListAdapter extends ArrayAdapter<NewsPiece> {

    private ArrayList<NewsPiece> mNewsList;
    private Context mContext;
    private int mCollapsedHeight;

    /**
     * Constructor.
     *
     * @param aContext application context.
     * @param aTextViewResId {@link TextView} objecr resource id.
     * @param aNewsList News list.
     */
    public ListAdapter(Context aContext, int aTextViewResId, ArrayList aNewsList) {
        super(aContext, aTextViewResId, aNewsList);
        mContext = aContext;
        mNewsList = aNewsList;
        mCollapsedHeight = mContext.getResources().getDimensionPixelSize(R.dimen.collapsed_view_height);
    }

    @Override
    public View getView(int aPosition, View aConvertView, ViewGroup aParent) {
        ListViewHolder listViewHolder;
        final NewsPiece newsPiece = mNewsList.get(aPosition);
        AbsListView.LayoutParams params;

        if (aConvertView == null) {
            final LayoutInflater layoutInflater =
                    (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            aConvertView = layoutInflater.inflate(R.layout.list_item, null);

            final LinearLayout textViewWrap = (LinearLayout) aConvertView.findViewById(R.id.list_item_layout);
            final TextView titleTextView = (TextView) aConvertView.findViewById(R.id.list_item_title_text_view);
            final TextView descriptionTextView =
                    (TextView) aConvertView.findViewById(R.id.list_item_description_text_view);

            listViewHolder = new ListViewHolder(titleTextView, descriptionTextView);
            listViewHolder.setTextViewWrap(textViewWrap);

            params = (AbsListView.LayoutParams) aConvertView.getLayoutParams();

            if (params != null) {
                listViewHolder.getTextViewWrap()
                        .setLayoutParams(new AbsListView.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT,
                                params.height));
            } else {
                listViewHolder.getTextViewWrap()
                        .setLayoutParams(new AbsListView.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT,
                                mCollapsedHeight));
            }
        } else {
            listViewHolder = (ListViewHolder) aConvertView.getTag();
            params = (AbsListView.LayoutParams) aConvertView.getLayoutParams();
            if (params != null) {
                listViewHolder.getTextViewWrap()
                        .setLayoutParams(new AbsListView.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT,
                                mCollapsedHeight));
            }
        }

        listViewHolder.getTitleTextView().setText(newsPiece.getTitle());
        listViewHolder.getDescriptionTextView().setText(newsPiece.getDate() + newsPiece.getDescription());
        aConvertView.setTag(listViewHolder);

        return aConvertView;
    }
}