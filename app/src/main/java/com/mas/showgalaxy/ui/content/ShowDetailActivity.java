package com.mas.showgalaxy.ui.content;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.mas.showgalaxy.R;
import com.mas.showgalaxy.common.DividerItemDecoration;
import com.mas.showgalaxy.common.Utils;
import com.mas.showgalaxy.moviedb.DataQuery;
import com.mas.showgalaxy.ui.responselistener.ShowImageResponseListener;
import com.mas.showgalaxy.ui.responselistener.ShowInfoResponseListener;
import com.mas.showgalaxy.ui.responselistener.ShowPersonResponseListener;
import com.mas.showgalaxy.ui.responselistener.ShowTrailerResponseListener;

public class ShowDetailActivity extends Activity{
    public static final String SHOW_ID = "show_id";

    private int mShowId;
    private String mShowType;
    private RecyclerView mShowBackdropGalleryRecyclerView;
    private RecyclerView mShowTrailerGalleryRecyclerView;
    private RecyclerView mShowPersonGalleryRecyclerView;
    private RecyclerView mShowSeasonGalleryRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if(intent == null)
            return;

        mShowId = intent.getIntExtra(SHOW_ID, -1);
        mShowType = intent.getStringExtra(Utils.SHOW_TYPE);
        if(mShowId == -1 || mShowType == null)
            return;

        ShowInfoResponseListener showInfoResponseListener = null;
        if(mShowType.equals(DataQuery.MOVIE)) {
            setContentView(R.layout.movie_detail_activity);
            LinearLayout showDetailInfoLayout = (LinearLayout) findViewById(R.id.ll_show_detail_info);
            showInfoResponseListener = new ShowInfoResponseListener(this, mShowType,
                    showDetailInfoLayout);
        }else if(mShowType.equals(DataQuery.TV_SHOW)) {
            setContentView(R.layout.tvshow_detail_activity);
            LinearLayout showDetailInfoLayout = (LinearLayout) findViewById(R.id.ll_show_detail_info);
            mShowSeasonGalleryRecyclerView = (RecyclerView) findViewById(R.id.rv_season_gallery);
            LinearLayoutManager seasonLinearLayoutManager = new LinearLayoutManager(this);
            seasonLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            mShowSeasonGalleryRecyclerView.setLayoutManager(seasonLinearLayoutManager);
            mShowSeasonGalleryRecyclerView.addItemDecoration(new DividerItemDecoration(this,
                    DividerItemDecoration.HORIZONTAL_LIST));
            showInfoResponseListener = new ShowInfoResponseListener(this, mShowType,
                    showDetailInfoLayout, mShowSeasonGalleryRecyclerView);
        }

        if(showInfoResponseListener == null)
            return;

        DataQuery.getShowDetailInfo(showInfoResponseListener, mShowType, mShowId);

        // Show photos
        mShowBackdropGalleryRecyclerView = (RecyclerView) findViewById(R.id.rv_backdrop_gallery);
        LinearLayoutManager backdropLinearLayoutManager = new LinearLayoutManager(this);
        backdropLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mShowBackdropGalleryRecyclerView.setLayoutManager(backdropLinearLayoutManager);
        mShowBackdropGalleryRecyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.HORIZONTAL_LIST));

        ShowImageResponseListener showImageResponseListener = new ShowImageResponseListener(this,
                mShowBackdropGalleryRecyclerView);
        DataQuery.getShowImages(showImageResponseListener, mShowType, mShowId);

        // Show trailers
        mShowTrailerGalleryRecyclerView = (RecyclerView) findViewById(R.id.rv_trailer_gallery);
        LinearLayoutManager trailerLinearLayoutManager = new LinearLayoutManager(this);
        trailerLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mShowTrailerGalleryRecyclerView.setLayoutManager(trailerLinearLayoutManager);
        mShowTrailerGalleryRecyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.HORIZONTAL_LIST));

        ShowTrailerResponseListener showTrailerResponseListener = new ShowTrailerResponseListener(this,
                mShowTrailerGalleryRecyclerView);
        DataQuery.getShowTrailers(showTrailerResponseListener, mShowType, mShowId);

        // Show people
        mShowPersonGalleryRecyclerView = (RecyclerView) findViewById(R.id.rv_person_gallery);
        LinearLayoutManager personLinearLayoutManager = new LinearLayoutManager(this);
        personLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mShowPersonGalleryRecyclerView.setLayoutManager(personLinearLayoutManager);
        mShowPersonGalleryRecyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.HORIZONTAL_LIST));

        ShowPersonResponseListener showPersonResponseListener = new ShowPersonResponseListener(this,
                mShowPersonGalleryRecyclerView);
        DataQuery.getShowCredits(showPersonResponseListener, mShowType, mShowId);
    }

}




