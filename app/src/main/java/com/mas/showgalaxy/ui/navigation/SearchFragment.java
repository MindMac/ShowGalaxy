package com.mas.showgalaxy.ui.navigation;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mas.showgalaxy.R;
import com.mas.showgalaxy.adapter.SearchViewPagerAdapter;
import com.mas.showgalaxy.moviedb.DataQuery;

public class SearchFragment extends Fragment {
    private SearchPageChangeListener mSearchPageChangeListener;
    private SearchViewPagerAdapter mSearchViewPagerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_fragment, container, false);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.vp_search);
        mSearchPageChangeListener = new SearchPageChangeListener();
        viewPager.addOnPageChangeListener(mSearchPageChangeListener);

        mSearchViewPagerAdapter = new SearchViewPagerAdapter(getChildFragmentManager(),
                this.getActivity());
        viewPager.setAdapter(mSearchViewPagerAdapter);

        //TabLayout
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tl_search);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

    private String getSearchType(){
        int curPage = 0;
        if(mSearchPageChangeListener == null)
            curPage = 0;
        else
            curPage = mSearchPageChangeListener.getCurrentPage();
        if(curPage == 0)
            return DataQuery.MOVIE;
        else if(curPage == 1)
            return DataQuery.TV_SHOW;
        else if(curPage == 2)
            return DataQuery.PEOPLE;
        return null;
    }


    public void startSearch(String searchText){
        String searchType = getSearchType();
        if(searchType == null || searchType.equals(""))
            return;
        mSearchViewPagerAdapter.startSearch(searchType, searchText);
    }

    public void clearSearchResults(){
        if(mSearchViewPagerAdapter != null) {
            String searchType = getSearchType();
            if(searchType == null || searchType.equals(""))
                return;
            mSearchViewPagerAdapter.clearSearchResults(searchType);
        }
    }

    class SearchPageChangeListener extends ViewPager.SimpleOnPageChangeListener {
        private int mCurrentPage = 0;

        @Override
        public void onPageSelected(int position) {
            mCurrentPage = position;
        }

        public int getCurrentPage() {
            return mCurrentPage;
        }
    }

}
