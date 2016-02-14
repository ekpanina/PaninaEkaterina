package com.example.katerina_panina.rssnews;

import android.app.ActionBar;
import android.app.ListActivity;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.view.View;
import android.view.animation.Animation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends ListActivity implements Handler.Callback {

    private Handler mHandler;
    ArrayList<NewsPiece> mNews;
    private ListAdapter mAdapter;
    private boolean mIsViewOpened = false;
    private View mPrevView;

    /**
     * Message to handler to parse news sources.
     */
    private final static int WHAT_START_PARSING = 0;

    /**
     * URL of the first news source.
     */
    private static final String LENTA_URL = "http://lenta.ru/rss";

    /**
     * URL of the second news source.
     */
    private static final String GAZETA_URL = "http://www.gazeta.ru/export/rss/lenta.xml";

    /**
     * Source name of the first news source.
     */
    private static final String LENTA_SOURCE = "(lenta.ru)";

    /**
     * Source name of the second news source.
     */
    private static final String GAZETA_SOURCE = "(gazeta.ru)";

    /**
     * Handler name.
     */
    private static final String HANDLER_NAME = "RssHandler";

    @Override
    protected void onCreate(Bundle aSavedInstanceState) {
        super.onCreate(aSavedInstanceState);
        setContentView(R.layout.content_main);

        ActionBar actionBar = getActionBar();
        actionBar.setTitle(getResources().getString(R.string.action_bar_title));
        actionBar.setIcon(R.drawable.news_icon);

        HandlerThread mRssThread = new HandlerThread(HANDLER_NAME);
        mRssThread.start();
        mHandler = new Handler(mRssThread.getLooper(), this);
        mHandler.sendEmptyMessage(WHAT_START_PARSING);

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                toggle(view);
            }
        });
    }

    /**
     * Toggles view by position.
     *
     * @param aView {@link View} view to be toggled.
     */
    private void toggle(View aView) {
        final Resources resources = getApplicationContext().getResources();
        int collapsedHeight =
                resources.getDimensionPixelSize(R.dimen.collapsed_view_height);
        int expandedHeight =
                resources.getDimensionPixelSize(R.dimen.expanded_view_height);
        if ((mPrevView != null) && (mPrevView != aView)) {
            mIsViewOpened = false;

            toggleAnimation(mPrevView, expandedHeight, collapsedHeight);
        }
        TextView descriptionView = (TextView) aView.findViewById(R.id.list_item_description_text_view);
        if (mIsViewOpened) {
            collapsedHeight =
                    resources.getDimensionPixelSize(R.dimen.expanded_view_height);
            expandedHeight =
                    resources.getDimensionPixelSize(R.dimen.collapsed_view_height);
            mIsViewOpened = false;
            descriptionView.setVisibility(View.GONE);
        } else {
            collapsedHeight =
                    resources.getDimensionPixelSize(R.dimen.collapsed_view_height);
            expandedHeight =
                    resources.getDimensionPixelSize(R.dimen.expanded_view_height);
            mIsViewOpened = true;
            descriptionView.setVisibility(View.VISIBLE);
        }
        toggleAnimation(aView, collapsedHeight, expandedHeight);
    }

    /**
     * Toggles animation for view block.
     *
     * @param aView {@link View} view to be toggled.
     * @param aStartHeight start height (from what height view block should be resized).
     * @param aEndHeight end height (to what height view block should be resized).
     */
    private void toggleAnimation(final View aView, final int aStartHeight, final int aEndHeight) {

        final ExpandingAnimation resizeAnimation = new ExpandingAnimation(aView,
                aStartHeight, aEndHeight);
        resizeAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                AbsListView.LayoutParams params =
                        (AbsListView.LayoutParams) aView.getLayoutParams();
                params.height = aEndHeight;
                aView.setLayoutParams(params);
            }
        });
        mPrevView = aView;
        aView.startAnimation(resizeAnimation);
    }

    @Override
    public boolean handleMessage(Message message) {
        switch (message.what) {
            case WHAT_START_PARSING:
                try {
                    RssParser parser = new RssParser(LENTA_URL, GAZETA_URL,
                            LENTA_SOURCE, GAZETA_SOURCE);
                    mNews = parser.parse();
                    mAdapter = new ListAdapter(this, R.layout.list_item, mNews);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setListAdapter(mAdapter);
                    }
                });
                return true;

            default:
                return false;
        }
    }
}
