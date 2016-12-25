package com.mas.showgalaxy.ui.responselistener;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.bumptech.glide.Glide;
import com.mas.showgalaxy.R;
import com.mas.showgalaxy.adapter.ShowSeasonGalleryAdapter;
import com.mas.showgalaxy.model.ShowInfo;
import com.mas.showgalaxy.model.TvSeasonInfo;
import com.mas.showgalaxy.moviedb.ImagePathConfigure;
import com.mas.showgalaxy.moviedb.DataQuery;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class ShowInfoResponseListener implements Response.Listener<JSONObject> {
    private Context mContext = null;
    private String mShowType = null;
    private ShowInfo mShowInfo = null;
    private View mShowDetailView = null;
    private RecyclerView mShowSeasonRecyclerView = null;
    private ArrayList<TvSeasonInfo> mTvSeasonShowInfos = new ArrayList<TvSeasonInfo>();

    public ShowInfoResponseListener(Context context, String showType, View showDetailView){
        this.mContext = context;
        this.mShowType = showType;
        this.mShowDetailView = showDetailView;
    }


    public ShowInfoResponseListener(Context context, String showType, View showDetailView,
                                    RecyclerView showSeasonRecyclerView){
        this(context, showType, showDetailView);
        this.mShowSeasonRecyclerView = showSeasonRecyclerView;
    }

    @Override
    public void onResponse(JSONObject response) {
        constructShowInfo(response);

        if(mShowInfo == null || mShowDetailView == null )
            return;
        if(mShowType.equals(DataQuery.TV_SHOW) && mShowSeasonRecyclerView == null)
            return;
        ImageView backdropView = (ImageView) mShowDetailView.findViewById(R.id.iv_show_backdrop);
        TextView showTitleView = (TextView) mShowDetailView.findViewById(R.id.tv_show_title);
        TextView releaseDateView = (TextView) mShowDetailView.findViewById(R.id.tv_show_release_date);
        TextView runTimeView = (TextView) mShowDetailView.findViewById(R.id.tv_show_run_time);
        TextView showScoreCount = (TextView) mShowDetailView.findViewById(R.id.tv_show_score_count);
        TextView showPopular = (TextView) mShowDetailView.findViewById(R.id.tv_show_popular);
        TextView showOverview = (TextView) mShowDetailView.findViewById(R.id.tv_show_overview);

        Glide.with(mContext)
                .load(ImagePathConfigure.getBackdropImageUrl(mShowInfo.getBackdropPath()))
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(backdropView);

        showTitleView.setText(mShowInfo.getShowTitle());
        releaseDateView.setText(mShowInfo.getReleaseDate());
        runTimeView.setText(String.format(Locale.US, "%sm", String.valueOf(mShowInfo.getRunTime())));
        showScoreCount.setText(String.format(Locale.US, "%s/%d", mShowInfo.getScore(), mShowInfo.getVoteCount()));
        showPopular.setText(String.format(Locale.US, "%.2f", mShowInfo.getPopularity()));
        showOverview.setText(mShowInfo.getOverview());

        if(mShowType.equals(DataQuery.TV_SHOW)){
            ShowSeasonGalleryAdapter showSeasonGalleryAdapter = new ShowSeasonGalleryAdapter(mContext, mTvSeasonShowInfos);
            mShowSeasonRecyclerView.setAdapter(showSeasonGalleryAdapter);
        }
    }

    private void constructShowInfo(JSONObject response){
        if(response == null || response.length() <= 0)
            return;
        try {
            String showTitle = "";
            String releaseDate = "";
            int runTime = 0;

            int showId = response.getInt("id");

            if(mShowType.equals(DataQuery.MOVIE)) {
                showTitle = response.getString("original_title");
                releaseDate = response.getString("release_date");
                runTime = response.getInt("runtime");
            }else {
                showTitle = response.getString("original_name");
                releaseDate = response.getString("first_air_date");
                JSONArray runTimeArray = response.getJSONArray("episode_run_time");
                if(runTimeArray != null && runTimeArray.length() >= 1){
                    runTime = runTimeArray.getInt(0);
                }

                JSONArray seasonArray = response.getJSONArray("seasons");
                if(seasonArray != null){
                    for(int i=0; i<seasonArray.length(); i++){
                        JSONObject seasonJson = seasonArray.getJSONObject(i);
                        String posterpath = seasonJson.getString("poster_path");
                        int seasonNum = seasonJson.getInt("season_number");

                        TvSeasonInfo tvSeasonShowInfo = new TvSeasonInfo(showId, posterpath, seasonNum);
                        mTvSeasonShowInfos.add(tvSeasonShowInfo);
                    }
                }
            }
            String backdropPath = response.getString("backdrop_path");
            String posterPath = response.getString("poster_path");
            String overview = response.getString("overview");
            JSONArray genreNameArray = response.getJSONArray("genres");
            ArrayList<String> genreNames = new ArrayList<String>();
            if(genreNameArray != null) {
                for (int j = 0; j < genreNameArray.length(); j++) {
                    JSONObject genreJson = genreNameArray.getJSONObject(j);
                    genreNames.add(genreJson.getString("name"));
                }
            }
            double popularity = response.getDouble("popularity");
            int voteCount = response.getInt("vote_count");
            double score = response.getDouble("vote_average");

            mShowInfo = new ShowInfo(mShowType, showId, showTitle == null ? "" : showTitle, score, voteCount,
                    popularity, backdropPath, posterPath, overview, releaseDate, genreNames, runTime);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
