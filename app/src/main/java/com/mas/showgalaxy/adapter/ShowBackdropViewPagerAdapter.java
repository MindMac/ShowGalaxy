package com.mas.showgalaxy.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.mas.showgalaxy.model.ShowInfo;
import com.mas.showgalaxy.ui.content.ShowOverviewBackdropFragment;

import java.util.ArrayList;


public class ShowBackdropViewPagerAdapter extends FragmentPagerAdapter {
    private static final int SHOW_DROPBACK_LIMIT = 5;
    private ArrayList<ShowInfo> mShowInfoList = new ArrayList<ShowInfo>();
    private String mShowType;
    private int mPageCount = 0;

    public ShowBackdropViewPagerAdapter(FragmentManager fragmentManager, String showType,
                                        ArrayList<ShowInfo> showInfoList) {
        super(fragmentManager);
        this.mShowInfoList = showInfoList;
        this.mShowType = showType;
        if(this.mShowInfoList.size() > SHOW_DROPBACK_LIMIT)
            mPageCount = SHOW_DROPBACK_LIMIT;
        else
            mPageCount = mShowInfoList.size();
    }

    @Override
    public Fragment getItem(int position) {
        if(position+1 > mPageCount)
            return null;
        ShowInfo showInfo = mShowInfoList.get(position);
        return ShowOverviewBackdropFragment.newInstance(mShowType, showInfo.getShowId(),
                showInfo.getBackdropPath(), showInfo.getShowTitle());
    }

    @Override
    public int getCount() {
        return mPageCount;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "";
    }

}
