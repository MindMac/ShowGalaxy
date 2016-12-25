package com.mas.showgalaxy.adapter;

import android.content.Context;
import android.widget.AbsListView;

import com.mas.showgalaxy.R;
import com.mas.showgalaxy.model.ShowInfo;
import com.mas.showgalaxy.moviedb.DataQuery;
import com.mas.showgalaxy.ui.responselistener.OverviewShowResponseListener;

import java.util.ArrayList;

public class OverviewShowListViewAdapter extends ShowListViewAdapter implements AbsListView.OnScrollListener {

    private DataQuery.QueryType mQueryType;

    public OverviewShowListViewAdapter(Context context, ArrayList<ShowInfo> showInfoList,
                                       String showType, DataQuery.QueryType queryType) {
        super(context, showInfoList, showType);
        mQueryType = queryType;
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount,
                         int totalItemCount) {
        switch(absListView.getId())
        {
            case R.id.lv_show_list:
                final int lastItem = firstVisibleItem + visibleItemCount;
                if(lastItem == totalItemCount)
                {
                    if(mPreLastItem != lastItem && mCurPage < mTotalPages)
                    {
                        mPreLastItem = lastItem;
                        OverviewShowResponseListener overviewShowResponseListener = new OverviewShowResponseListener(mShowType, this);
                        DataQuery.getShowInfos(overviewShowResponseListener, mShowType, mQueryType, mCurPage+1);
                    }
                }
                break;
        }
    }
}