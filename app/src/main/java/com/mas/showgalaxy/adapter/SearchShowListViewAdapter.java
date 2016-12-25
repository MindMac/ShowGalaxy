package com.mas.showgalaxy.adapter;

import android.content.Context;
import android.widget.AbsListView;

import com.mas.showgalaxy.R;
import com.mas.showgalaxy.model.ShowInfo;
import com.mas.showgalaxy.moviedb.DataQuery;
import com.mas.showgalaxy.ui.responselistener.ShowResponseListener;

import java.util.ArrayList;

public class SearchShowListViewAdapter extends ShowListViewAdapter implements AbsListView.OnScrollListener, ListViewAdapterInterface {

    private String mSearchText;

    public SearchShowListViewAdapter(Context context, ArrayList<ShowInfo> showInfoList, String searchType, String searchText) {
        super(context, showInfoList, searchType);
        mSearchText = searchText;
    }


    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount,
                         int totalItemCount) {

        switch(absListView.getId())
        {
            case R.id.lv_search_result:

                final int lastItem = firstVisibleItem + visibleItemCount;
                if(lastItem == totalItemCount)
                {
                    if(mPreLastItem != lastItem && mCurPage < mTotalPages)
                    {
                        mPreLastItem = lastItem;
                        ShowResponseListener showSearchResponseListener = new ShowResponseListener(mShowType, this);
                        DataQuery.getSearchResult(showSearchResponseListener, mShowType, mSearchText, mCurPage+1);
                    }
                }
                break;
        }
    }

    @Override
    public void clearResults() {
        mShowInfoList.clear();
    }
}

