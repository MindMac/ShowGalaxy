package com.mas.showgalaxy.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.mas.showgalaxy.R;
import com.mas.showgalaxy.moviedb.DataQuery;
import com.mas.showgalaxy.ui.content.SearchResultFragment;

import java.util.Hashtable;


public class SearchViewPagerAdapter extends FragmentPagerAdapter {
    private static final int PAGE_COUNT = 3;
    private Context mContext = null;
    private String mSearchText = null;
    private static Hashtable<String, SearchResultFragment> mSearchResultFragmentMap = new Hashtable<>();

    public SearchViewPagerAdapter(FragmentManager fragmentManager, Context context) {
        super(fragmentManager);
        this.mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        if(position+1 > PAGE_COUNT)
            return null;
        String searchType = getSearchType(position);
        SearchResultFragment searchResultFragment = new SearchResultFragment();
        mSearchResultFragmentMap.put(searchType, searchResultFragment);
        return searchResultFragment;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(position == 0)
            return mContext.getString(R.string.search_movie);
        else if(position == 1)
            return mContext.getString(R.string.search_tvshow);
        else if(position == 2)
            return mContext.getString(R.string.search_people);
        return "";
    }

    private String getSearchType(int position){
        switch (position){
            case 0:
                return DataQuery.MOVIE;
            case 1:
                return DataQuery.TV_SHOW;
            case 2:
                return DataQuery.PEOPLE;
        }
        return null;
    }


    public void startSearch(String searchType, String searchText){
        if(searchType == null)
            return;
        SearchResultFragment searchResultFragment = mSearchResultFragmentMap.get(searchType);
        if(searchResultFragment == null)
            return;
        mSearchText = searchText;
        searchResultFragment.startSearch(searchType, searchText);
    }

    public void clearSearchResults(String searchType){
        if(searchType == null)
            return;
        SearchResultFragment searchResultFragment = mSearchResultFragmentMap.get(searchType);
        if(searchResultFragment == null)
            return;
        searchResultFragment.clearSearchResults();
    }
}
