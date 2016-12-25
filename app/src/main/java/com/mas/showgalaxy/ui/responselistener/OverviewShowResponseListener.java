package com.mas.showgalaxy.ui.responselistener;

import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.mas.showgalaxy.adapter.ShowBackdropViewPagerAdapter;
import com.mas.showgalaxy.adapter.ShowListViewAdapter;

import org.json.JSONObject;


public class OverviewShowResponseListener extends ShowResponseListener {
    private ViewPager mBackdropViewPager = null;
    private FragmentManager mFragmentManager = null;
    private boolean mNeedUpdateBackdrop = false;


    public OverviewShowResponseListener(String showType, ShowListViewAdapter showListViewAdapter,
                ViewPager backdropViewPager, FragmentManager fragmentManager){
        super(showType, showListViewAdapter);
        this.mBackdropViewPager = backdropViewPager;
        this.mFragmentManager = fragmentManager;
        this.mNeedUpdateBackdrop = true;
    }

    public OverviewShowResponseListener(String showType, ShowListViewAdapter showListViewAdapter){
        super(showType, showListViewAdapter);
        this.mNeedUpdateBackdrop = false;
    }


    @Override
    public void onResponse(JSONObject response) {
        constructShowInfo(response);
        if(mNeedUpdateBackdrop) {
            ShowBackdropViewPagerAdapter showBackdropViewPagerAdapter = new
                    ShowBackdropViewPagerAdapter(mFragmentManager, mShowType, mShowInfoList);
            mBackdropViewPager.setAdapter(showBackdropViewPagerAdapter);
        }

        mShowListViewAdapter.setTotalPages(mTotalPages);
        mShowListViewAdapter.nextPage(mShowInfoList);
    }
}
