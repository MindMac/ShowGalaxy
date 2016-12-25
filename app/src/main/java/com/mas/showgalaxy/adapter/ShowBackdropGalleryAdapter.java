package com.mas.showgalaxy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mas.showgalaxy.R;
import com.mas.showgalaxy.moviedb.ImagePathConfigure;

import java.util.ArrayList;

public class ShowBackdropGalleryAdapter extends RecyclerView.Adapter<ShowBackdropGalleryAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private ArrayList<String> mBackdropPathList;
    private Context mContext;

    public ShowBackdropGalleryAdapter(Context context, ArrayList<String> backdropPathList)
    {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mBackdropPathList = backdropPathList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView mIvBackdrop;
        public ViewHolder(View view){
            super(view);
        }
    }


    @Override
    public ShowBackdropGalleryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.show_detail_backdrop_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.mIvBackdrop = (ImageView) view.findViewById(R.id.iv_show_backdrop);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ShowBackdropGalleryAdapter.ViewHolder holder, int position) {
        if(position+1 > mBackdropPathList.size())
            return;

        Glide.with(mContext)
                .load(ImagePathConfigure.getBackdropImageUrl(mBackdropPathList.get(position)))
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .crossFade()
                .into(holder.mIvBackdrop);
    }

    @Override
    public int getItemCount() {
        return mBackdropPathList.size();
    }
}
