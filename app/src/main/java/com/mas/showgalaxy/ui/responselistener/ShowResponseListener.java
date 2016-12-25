package com.mas.showgalaxy.ui.responselistener;

import com.android.volley.Response;
import com.mas.showgalaxy.adapter.ShowListViewAdapter;
import com.mas.showgalaxy.model.ShowInfo;
import com.mas.showgalaxy.moviedb.DataQuery;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ShowResponseListener implements Response.Listener<JSONObject> {
    String mShowType;
    int mTotalPages;
    ArrayList<ShowInfo> mShowInfoList = new ArrayList<ShowInfo>();
    ShowListViewAdapter mShowListViewAdapter = null;

    public ShowResponseListener(String showType, ShowListViewAdapter showListViewAdapter){
        this.mShowType = showType;
        this.mShowListViewAdapter = showListViewAdapter;
    }


    @Override
    public void onResponse(JSONObject response) {
        constructShowInfo(response);
        mShowListViewAdapter.setTotalPages(mTotalPages);
        mShowListViewAdapter.nextPage(mShowInfoList);
    }

    void constructShowInfo(JSONObject response){
        if(response == null || response.length() <= 0)
            return;
        try {
            mTotalPages = response.getInt("total_pages");
            JSONArray showArray = response.getJSONArray("results");
            if(showArray != null){
                for(int i=0; i<showArray.length(); i++){
                    JSONObject showInfo = showArray.getJSONObject(i);
                    String showTitle = "";
                    String releaseDate = "";

                    if(mShowType.equals(DataQuery.MOVIE)) {
                        showTitle = showInfo.optString("original_title");
                        releaseDate = showInfo.optString("release_date");
                    }else {
                        showTitle = showInfo.optString("original_name");
                        releaseDate = showInfo.optString("first_air_date");
                    }

                    int showId = showInfo.optInt("id");
                    String backdropPath = showInfo.getString("backdrop_path");
                    String posterPath = showInfo.getString("poster_path");
                    String overview = showInfo.getString("overview");
                    JSONArray genreIds = showInfo.getJSONArray("genre_ids");
                    ArrayList<Integer> genreIdList = new ArrayList<Integer>();
                    for(int j=0; j< genreIds.length(); j++){
                        genreIdList.add((Integer) genreIds.get(j));
                    }
                    double popularity = showInfo.getDouble("popularity");
                    int voteCount = showInfo.getInt("vote_count");
                    double score = showInfo.getDouble("vote_average");

                    ShowInfo showInfoObj = new ShowInfo(mShowType, showId, showTitle == null ? "" : showTitle, score, voteCount,
                            popularity, backdropPath, posterPath, overview, releaseDate, genreIdList);
                    mShowInfoList.add(showInfoObj);
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
