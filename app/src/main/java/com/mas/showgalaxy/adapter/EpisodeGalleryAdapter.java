package com.mas.showgalaxy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mas.showgalaxy.R;
import com.mas.showgalaxy.model.TvEpisodeInfo;
import com.mas.showgalaxy.moviedb.ImagePathConfigure;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class EpisodeGalleryAdapter extends RecyclerView.Adapter<EpisodeGalleryAdapter.ViewHolder> {

    private List<TvEpisodeInfo> mTvEpisodeInfoList;
    private LayoutInflater mInflater = null;
    private Context mContext;

    public EpisodeGalleryAdapter(Context context, ArrayList<TvEpisodeInfo> tvEpisodeInfoList) {
        mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mTvEpisodeInfoList = tvEpisodeInfoList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public int mPosition;
        public ImageView mIvEpisodeBackdrop;
        public TextView mTvEpisodeTitle;
        public TextView mTvEpisodeNum;
        public TextView mTvEpisodeReleaseDate;
        public TextView mTvEpisodeOverview;

        public ViewHolder(View view){
            super(view);
        }
    }


    @Override
    public EpisodeGalleryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.episode_info_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.mIvEpisodeBackdrop = (ImageView) view.findViewById(R.id.iv_episode_backdrop);
        viewHolder.mTvEpisodeTitle = (TextView) view.findViewById(R.id.tv_episode_title);
        viewHolder.mTvEpisodeNum = (TextView) view.findViewById(R.id.tv_spisode_num);
        viewHolder.mTvEpisodeReleaseDate = (TextView) view.findViewById(R.id.tv_spisode_release_date);
        viewHolder.mTvEpisodeOverview = (TextView) view.findViewById(R.id.tv_episode_overview);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final EpisodeGalleryAdapter.ViewHolder holder, int position) {
        if(position+1 > mTvEpisodeInfoList.size())
            return;

        // Get TvEpisodeInfo
        final TvEpisodeInfo tvEpisodeInfo = mTvEpisodeInfoList.get(position);
        if(tvEpisodeInfo == null)
            return;

        // Set app name
        holder.mTvEpisodeTitle.setText(tvEpisodeInfo.getName());
        holder.mTvEpisodeNum.setText(String.format(Locale.US, "Episode %s", tvEpisodeInfo.getEpisodeNum()));
        holder.mTvEpisodeReleaseDate.setText(tvEpisodeInfo.getReleaseDate());
        holder.mTvEpisodeOverview.setText(tvEpisodeInfo.getOverview());

        Glide.with(mContext)
                .load(ImagePathConfigure.getBackdropImageUrl(tvEpisodeInfo.getBackdropPath()))
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .crossFade()
                .into(holder.mIvEpisodeBackdrop);
    }

    @Override
    public int getItemCount() {
        return mTvEpisodeInfoList.size();
    }
}
