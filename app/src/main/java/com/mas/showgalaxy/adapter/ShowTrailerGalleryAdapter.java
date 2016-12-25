package com.mas.showgalaxy.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mas.showgalaxy.R;
import com.mas.showgalaxy.common.Utils;

import java.util.ArrayList;

public class ShowTrailerGalleryAdapter extends RecyclerView.Adapter<ShowTrailerGalleryAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private ArrayList<String> mTrailerIdList;
    private Context mContext;

    public ShowTrailerGalleryAdapter(Context context, ArrayList<String> trailerIdList)
    {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mTrailerIdList = trailerIdList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView mIvTrailer;
        String mTrailerId;
        public ViewHolder(View view){
            super(view);
        }
    }


    @Override
    public ShowTrailerGalleryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.show_detail_trailer_item, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.mIvTrailer = (ImageView) view.findViewById(R.id.iv_show_trailer);
        viewHolder.mIvTrailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Utils.isAppInstalled(mContext, Utils.YOUTUBE_PACKAGE_NAME))
                    Toast.makeText(mContext, mContext.getText(R.string.install_youtube), Toast.LENGTH_LONG).show();
                else{
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + viewHolder.mTrailerId));
                    mContext.startActivity(intent);
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ShowTrailerGalleryAdapter.ViewHolder holder, int position) {
        if(position+1 > mTrailerIdList.size())
            return;

        holder.mTrailerId = mTrailerIdList.get(position);
        Glide.with(mContext)
            .load(Utils.constructYoutubeTrailerBackgroundImageUrl(mTrailerIdList.get(position)))
            .placeholder(R.mipmap.ic_launcher)
            .error(R.mipmap.ic_launcher)
            .crossFade()
            .into(holder.mIvTrailer);
    }

    @Override
    public int getItemCount() {
        return mTrailerIdList.size();
    }
}
