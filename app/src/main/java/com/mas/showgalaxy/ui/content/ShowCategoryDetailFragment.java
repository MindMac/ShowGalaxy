package com.mas.showgalaxy.ui.content;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.mas.showgalaxy.R;
import com.mas.showgalaxy.adapter.OverviewShowListViewAdapter;
import com.mas.showgalaxy.adapter.ShowCategoryViewPagerAdapter;
import com.mas.showgalaxy.model.ShowInfo;
import com.mas.showgalaxy.moviedb.DataQuery;
import com.mas.showgalaxy.ui.navigation.ShowCategoryFragment;
import com.mas.showgalaxy.ui.responselistener.OverviewShowResponseListener;

import java.util.ArrayList;

public class ShowCategoryDetailFragment extends Fragment {
    private static final String PAGE_NUM_KEY = "pageNum";
    private int mPageNum;
    private String mShowType;


    /**
     * Create a new instance of ShowCategoryDetailFragment, providing "pageNum" and "showType"
     * as an argument.
     */

    public static ShowCategoryDetailFragment newInstance(int pageNum, String showType) {
        ShowCategoryDetailFragment showCategoryDetailFragment = new ShowCategoryDetailFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(PAGE_NUM_KEY, pageNum);
        arguments.putString(ShowCategoryFragment.SHOW_TYPE, showType);
        showCategoryDetailFragment.setArguments(arguments);
        return showCategoryDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNum = getArguments().getInt(PAGE_NUM_KEY, 0);
        mShowType = getArguments().getString(ShowCategoryFragment.SHOW_TYPE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.show_category_detail_fragment, container, false);
        inflateDetailContent(view);
        return view;
    }

    private void inflateDetailContent(View view){
        ListView showListView = (ListView) view.findViewById(R.id.lv_show_list);
        ArrayList<ShowInfo> showInfoList = new ArrayList<ShowInfo>();

        ViewPager backdropViewPager = (ViewPager) view.findViewById(R.id.vp_show_backdrop);
        DataQuery.QueryType queryType = getCorrectQueryType();

        OverviewShowListViewAdapter overviewShowListViewAdapter = new OverviewShowListViewAdapter(this.getContext(),
                showInfoList, mShowType, queryType);
        showListView.setAdapter(overviewShowListViewAdapter);
        showListView.setOnScrollListener(overviewShowListViewAdapter);

        OverviewShowResponseListener overviewShowResponseListener = new OverviewShowResponseListener(
                mShowType, overviewShowListViewAdapter, backdropViewPager, getChildFragmentManager());
        DataQuery.getShowInfos(overviewShowResponseListener, mShowType, queryType, 1);
    }

    public DataQuery.QueryType getCorrectQueryType(){
        switch (mPageNum) {
            case ShowCategoryViewPagerAdapter.NOW_PLAYING_PAGE:
                return DataQuery.QueryType.NOW_PLAYING;
            case ShowCategoryViewPagerAdapter.UPCOMING_PAGE:
                return DataQuery.QueryType.UPCOMING;
            case ShowCategoryViewPagerAdapter.POPULAR_PAGE:
                return DataQuery.QueryType.POPULAR;
            case ShowCategoryViewPagerAdapter.TOP_RATE_PAGE:
                return DataQuery.QueryType.TOP_RATE;
        }
        return null;
    }

//    private class ShowResponseListener implements Listener<JSONObject>{
//        private ArrayList<ShowInfo> mShowInfoList = new ArrayList<ShowInfo>();
//        private ViewPager mBackdropViewPager = null;
//        private ListView mShowListView = null;
//        private DataQuery.QueryType mQueryType = null;
//        private int mTotalPages = 1;
//
//        public ShowResponseListener(ViewPager backdropViewPager, ListView showListView,
//                                     DataQuery.QueryType queryType){
//            this.mBackdropViewPager = backdropViewPager;
//            this.mShowListView = showListView;
//            this.mQueryType = queryType;
//        }
//
//        @Override
//        public void onResponse(JSONObject response) {
//            constructShowInfo(response);
//            ShowBackdropViewPagerAdapter showBackdropViewPagerAdapter = new
//                    ShowBackdropViewPagerAdapter(getChildFragmentManager(), mShowType, mShowInfoList);
//            mBackdropViewPager.setAdapter(showBackdropViewPagerAdapter);
//            OverviewShowListViewAdapter overviewShowListViewAdapter = new OverviewShowListViewAdapter(getContext(),
//                    mShowInfoList, mShowType, mQueryType);
//            overviewShowListViewAdapter.setTotalPages(mTotalPages);
//            mShowListView.setAdapter(overviewShowListViewAdapter);
//            mShowListView.setOnScrollListener(overviewShowListViewAdapter);
//        }
//
//        private void constructShowInfo(JSONObject response){
//            if(response == null || response.length() <= 0)
//                return;
//            try {
//                JSONArray showArray = response.getJSONArray("results");
//                mTotalPages = response.getInt("total_pages");
//                if(showArray != null){
//                    for(int i=0; i<showArray.length(); i++){
//                        JSONObject showInfo = showArray.getJSONObject(i);
//                        String showTitle = "";
//                        if(mShowType.equals(DataQuery.MOVIE))
//                            showTitle = showInfo.getString("original_title");
//                        else
//                            showTitle = showInfo.getString("original_name");
//                        int showId = showInfo.getInt("id");
//                        String backdropPath = showInfo.getString("backdrop_path");
//                        String posterPath = showInfo.getString("poster_path");
//                        String overview = showInfo.getString("overview");
//                        String releaseDate = "";
//                        if(mShowType.equals(DataQuery.MOVIE))
//                            releaseDate = showInfo.getString("release_date");
//                        else
//                            releaseDate = showInfo.getString("first_air_date");
//                        JSONArray genreIds = showInfo.getJSONArray("genre_ids");
//                        ArrayList<Integer> genreIdList = new ArrayList<Integer>();
//                        for(int j=0; j< genreIds.length(); j++){
//                            genreIdList.add((Integer) genreIds.get(j));
//                        }
//                        double popularity = showInfo.getDouble("popularity");
//                        int voteCount = showInfo.getInt("vote_count");
//                        double score = showInfo.getDouble("vote_average");
//
//
//                        ShowInfo showInfoObj = new ShowInfo(mShowType, showId, showTitle == null ? "" : showTitle, score, voteCount,
//                                popularity, backdropPath, posterPath, overview, releaseDate, genreIdList);
//                        mShowInfoList.add(showInfoObj);
//                    }
//                }
//            }catch (Exception ex){
//                ex.printStackTrace();
//            }
//        }
//    }
}

