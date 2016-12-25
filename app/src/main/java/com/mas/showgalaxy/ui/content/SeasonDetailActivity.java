package com.mas.showgalaxy.ui.content;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mas.showgalaxy.R;
import com.mas.showgalaxy.moviedb.DataQuery;
import com.mas.showgalaxy.ui.responselistener.ShowSeasonResponseListener;

public class SeasonDetailActivity extends Activity{
    public static final String TV_SHOW_ID = "tv_show_id";
    public static final String SEASON_NUM = "season_num";

    private int mTvShowId;
    private int mSeasonNum;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.season_detail_activity);

        Intent intent = getIntent();
        if(intent == null)
            return;

        mTvShowId = intent.getIntExtra(TV_SHOW_ID, -1);
        mSeasonNum = intent.getIntExtra(SEASON_NUM, -1);
        if(mTvShowId == -1 || mSeasonNum == -1)
            return;

        View seasonDetailView = findViewById(R.id.fr_season_detail);
        ShowSeasonResponseListener showSeasonResponseListener = new ShowSeasonResponseListener(this, seasonDetailView);
        DataQuery.getSeasonDetail(showSeasonResponseListener, mTvShowId, mSeasonNum);
    }

}




