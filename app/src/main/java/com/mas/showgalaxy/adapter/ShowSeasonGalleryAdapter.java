package com.mas.showgalaxy.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mas.showgalaxy.R;
import com.mas.showgalaxy.model.TvSeasonInfo;
import com.mas.showgalaxy.moviedb.ImagePathConfigure;
import com.mas.showgalaxy.ui.content.SeasonDetailActivity;

import java.util.ArrayList;

public class ShowSeasonGalleryAdapter extends RecyclerView.Adapter<ShowSeasonGalleryAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private ArrayList<TvSeasonInfo> mTvSeasonShowInfoList;
    private Context mContext;

    public ShowSeasonGalleryAdapter(Context context, ArrayList<TvSeasonInfo> tvSeasonShowInfoList)
    {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mTvSeasonShowInfoList = tvSeasonShowInfoList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView mIvSeason;
        int mShowId;
        int mSeasonNum;
        public ViewHolder(View view){
            super(view);
        }
    }


    @Override
    public ShowSeasonGalleryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.show_detail_season_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.mIvSeason = (ImageView) view.findViewById(R.id.iv_show_season);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ShowSeasonGalleryAdapter.ViewHolder holder, int position) {
        if(position+1 > mTvSeasonShowInfoList.size())
            return;

        TvSeasonInfo tvSeasonShowInfo = mTvSeasonShowInfoList.get(position);
        if(tvSeasonShowInfo == null)
            return;

        holder.mSeasonNum = tvSeasonShowInfo.getSeasonNum();
        holder.mShowId = tvSeasonShowInfo.getShowId();

        Glide.with(mContext)
                .load(ImagePathConfigure.getBackdropImageUrl(tvSeasonShowInfo.getPosterpath()))
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .crossFade()
                .into(holder.mIvSeason);

        holder.mIvSeason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(mContext, SeasonDetailActivity.class);
                intent.putExtra(SeasonDetailActivity.TV_SHOW_ID, holder.mShowId);
                intent.putExtra(SeasonDetailActivity.SEASON_NUM, holder.mSeasonNum);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTvSeasonShowInfoList.size();
    }
}
