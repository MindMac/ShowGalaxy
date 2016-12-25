package com.mas.showgalaxy.ui.content;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.mas.showgalaxy.R;
import com.mas.showgalaxy.adapter.ListViewAdapterInterface;
import com.mas.showgalaxy.adapter.SearchPersonListViewAdapter;
import com.mas.showgalaxy.adapter.SearchShowListViewAdapter;
import com.mas.showgalaxy.model.PersonInfo;
import com.mas.showgalaxy.model.ShowInfo;
import com.mas.showgalaxy.moviedb.DataQuery;
import com.mas.showgalaxy.ui.responselistener.SearchPersonResponseListener;
import com.mas.showgalaxy.ui.responselistener.ShowResponseListener;

import java.util.ArrayList;


public class SearchResultFragment extends Fragment {
    private View mRootView;
    private ListViewAdapterInterface mSearchListViewAdapter = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.search_result_fragment, container, false);
        return mRootView;
    }

    public void startSearch(String searchType, String searchText){
        ListView searchResultListView = (ListView) mRootView.findViewById(R.id.lv_search_result);

        if(searchType.equals(DataQuery.PEOPLE)){
            ArrayList<PersonInfo> personInfoList = new ArrayList<PersonInfo>();
            SearchPersonListViewAdapter searchPersonListViewAdapter = new SearchPersonListViewAdapter(this.getContext(),
                    personInfoList, searchText);
            mSearchListViewAdapter = searchPersonListViewAdapter;
            searchResultListView.setAdapter(searchPersonListViewAdapter);
            searchResultListView.setOnScrollListener(searchPersonListViewAdapter);
            SearchPersonResponseListener searchPersonResponseListener = new SearchPersonResponseListener(searchPersonListViewAdapter);
            DataQuery.getSearchResult(searchPersonResponseListener, searchType, searchText, 1);
        }else {
            ArrayList<ShowInfo> showInfoList = new ArrayList<ShowInfo>();
            SearchShowListViewAdapter searchShowListViewAdapter = new SearchShowListViewAdapter(this.getContext(),
                    showInfoList, searchType, searchText);
            mSearchListViewAdapter= searchShowListViewAdapter;
            searchResultListView.setAdapter(searchShowListViewAdapter);
            searchResultListView.setOnScrollListener(searchShowListViewAdapter);
            ShowResponseListener showResponseListener = new ShowResponseListener(searchType, searchShowListViewAdapter);
            DataQuery.getSearchResult(showResponseListener, searchType, searchText, 1);
        }
    }

    public void clearSearchResults(){
        if(mSearchListViewAdapter != null)
            mSearchListViewAdapter.clearResults();
    }
}
