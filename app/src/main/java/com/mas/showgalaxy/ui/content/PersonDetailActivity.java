package com.mas.showgalaxy.ui.content;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.mas.showgalaxy.R;
import com.mas.showgalaxy.common.DividerItemDecoration;
import com.mas.showgalaxy.moviedb.DataQuery;
import com.mas.showgalaxy.ui.responselistener.PersonCreditResponseListener;
import com.mas.showgalaxy.ui.responselistener.PersonInfoResponseListener;

public class PersonDetailActivity extends Activity{
    public static final String PERSON_ID = "person_id";

    private int mPersonId;
    private RecyclerView mPersonCreditMovieRecyclerView;
    private RecyclerView mPersonCreditTvShowRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_detail_activity);

        Intent intent = getIntent();
        if(intent == null)
            return;

        mPersonId = intent.getIntExtra(PERSON_ID, -1);
        if(mPersonId == -1)
            return;


        // Person detail info
        LinearLayout personDetailLayout = (LinearLayout) findViewById(R.id.ll_person_detail);
        PersonInfoResponseListener personInfoResponseListener = new PersonInfoResponseListener(this,
                personDetailLayout);
        DataQuery.getPersonDetail(personInfoResponseListener, mPersonId);


        // Show credit images
        // Movie credits
        mPersonCreditMovieRecyclerView = (RecyclerView) findViewById(R.id.rv_person_movie_credit_gallery);
        LinearLayoutManager movieLinearLayoutManager = new LinearLayoutManager(this);
        movieLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mPersonCreditMovieRecyclerView.setLayoutManager(movieLinearLayoutManager);
        mPersonCreditMovieRecyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.HORIZONTAL_LIST));

        View creditMovieView = findViewById(R.id.ll_credit_movie);
        PersonCreditResponseListener moviePersonCreditResponseListener = new PersonCreditResponseListener(this,
                DataQuery.MOVIE, creditMovieView, mPersonCreditMovieRecyclerView);
        DataQuery.getPersonMovieCredits(moviePersonCreditResponseListener, mPersonId);

        // TV show credits
        mPersonCreditTvShowRecyclerView = (RecyclerView) findViewById(R.id.rv_person_tv_credit_gallery);
        LinearLayoutManager tvLinearLayoutManager = new LinearLayoutManager(this);
        tvLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mPersonCreditTvShowRecyclerView.setLayoutManager(tvLinearLayoutManager);
        mPersonCreditTvShowRecyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.HORIZONTAL_LIST));

        View creditTvView = findViewById(R.id.ll_credit_tv);
        PersonCreditResponseListener tvPersonCreditResponseListener = new PersonCreditResponseListener(this,
                DataQuery.TV_SHOW, creditTvView, mPersonCreditTvShowRecyclerView);
        DataQuery.getPersonTvCredits(tvPersonCreditResponseListener, mPersonId);
    }

}




