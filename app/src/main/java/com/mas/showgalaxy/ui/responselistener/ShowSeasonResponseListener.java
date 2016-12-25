package com.mas.showgalaxy.ui.responselistener;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.bumptech.glide.Glide;
import com.mas.showgalaxy.R;
import com.mas.showgalaxy.adapter.EpisodeGalleryAdapter;
import com.mas.showgalaxy.common.DividerItemDecoration;
import com.mas.showgalaxy.model.TvEpisodeInfo;
import com.mas.showgalaxy.moviedb.ImagePathConfigure;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class ShowSeasonResponseListener implements Response.Listener<JSONObject> {

    private Context mContext = null;
    private ArrayList<TvEpisodeInfo> mTvEpisodeInfoList = new ArrayList<TvEpisodeInfo>();
    private View mSeasonDetailView = null;

    private String mSeasonName = "";
    private String mReleaseDate = "";
    private String mPosterpath = "";
    private String mOverview = "";

    public ShowSeasonResponseListener(Context context, View seasonDetailView){
        this.mContext = context;
        this.mSeasonDetailView = seasonDetailView;
    }

    @Override
    public void onResponse(JSONObject response) {
        constructSeasonInfo(response);

        TextView tvSeasonName = (TextView) mSeasonDetailView.findViewById(R.id.tv_season_title);
        TextView tvReleaseDate = (TextView) mSeasonDetailView.findViewById(R.id.tv_season_release_date);
        ImageView ivPoster = (ImageView) mSeasonDetailView.findViewById(R.id.iv_season_poster);
        TextView tvSeasonOverview = (TextView) mSeasonDetailView.findViewById(R.id.tv_season_overview);
        TextView tvEpisodeCount = (TextView) mSeasonDetailView.findViewById(R.id.tv_episode_count);

        Glide.with(mContext)
                .load(ImagePathConfigure.getSeasonPosterImageUrl(mPosterpath))
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .crossFade()
                .into(ivPoster);

        tvSeasonName.setText(mSeasonName);
        tvReleaseDate.setText(mReleaseDate);
        tvEpisodeCount.setText(String.format(Locale.US, "%s", mTvEpisodeInfoList.size()));
        tvSeasonOverview.setText(mOverview);


        RecyclerView episodeRecyclerView = (RecyclerView) mSeasonDetailView.findViewById(R.id.rv_episode_gallery);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        episodeRecyclerView.setLayoutManager(linearLayoutManager);
        episodeRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.HORIZONTAL_LIST));
        EpisodeGalleryAdapter episodeGalleryAdapter = new EpisodeGalleryAdapter(mContext, mTvEpisodeInfoList);
        episodeRecyclerView.setAdapter(episodeGalleryAdapter);
    }

    private void constructSeasonInfo(JSONObject response){
        if(response == null || response.length() <= 0)
            return;
        try {
            mSeasonName = response.optString("name");
            mReleaseDate = response.optString("air_date");
            mPosterpath = response.optString("poster_path");
            mOverview = response.optString("overview");

            JSONArray episodeArray = response.optJSONArray("episodes");
            if(episodeArray != null){
                for(int i=0; i<episodeArray.length(); i++){
                    JSONObject episodeJson = episodeArray.getJSONObject(i);
                    String releaseDate = episodeJson.getString("air_date");
                    String episodeName = episodeJson.getString("name");
                    int episodeNum = episodeJson.getInt("episode_number");
                    String overview = episodeJson.getString("overview");
                    String backdropPath = episodeJson.getString("still_path");

                    TvEpisodeInfo tvEpisodeInfo = new TvEpisodeInfo(releaseDate, episodeName, overview,
                            backdropPath, episodeNum);
                    mTvEpisodeInfoList.add(tvEpisodeInfo);
                }
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}

