package com.mas.showgalaxy.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.mas.showgalaxy.R;
import com.mas.showgalaxy.ui.content.ShowCategoryDetailFragment;


public class ShowCategoryViewPagerAdapter extends FragmentPagerAdapter {
    private Context mContext = null;
    private String mShowType = null;

    private static final int TOTAL_PAGES = 4;

    public static final int NOW_PLAYING_PAGE = 0;
    public static final int POPULAR_PAGE = 1;
    public static final int UPCOMING_PAGE = 2;
    public static final int TOP_RATE_PAGE = 3;

    public ShowCategoryViewPagerAdapter(FragmentManager fragmentManager, String showType,
                                        Context context) {
        super(fragmentManager);
        this.mShowType = showType;
        this.mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case NOW_PLAYING_PAGE:
                return ShowCategoryDetailFragment.newInstance(NOW_PLAYING_PAGE, mShowType);
            case UPCOMING_PAGE:
                return ShowCategoryDetailFragment.newInstance(UPCOMING_PAGE, mShowType);
            case TOP_RATE_PAGE:
                return ShowCategoryDetailFragment.newInstance(TOP_RATE_PAGE, mShowType);
            case POPULAR_PAGE:
                return ShowCategoryDetailFragment.newInstance(POPULAR_PAGE, mShowType);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return TOTAL_PAGES;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(position == NOW_PLAYING_PAGE)
            return mContext.getString(R.string.now_playing);
        else if(position == UPCOMING_PAGE)
            return mContext.getString(R.string.coming_soon);
        else if(position == POPULAR_PAGE)
            return mContext.getString(R.string.popular);
        else if(position == TOP_RATE_PAGE)
            return mContext.getString(R.string.top_rate);
        return "";
    }

}
