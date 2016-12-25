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
import com.mas.showgalaxy.common.Utils;
import com.mas.showgalaxy.model.ShowInfo;
import com.mas.showgalaxy.moviedb.ImagePathConfigure;
import com.mas.showgalaxy.ui.content.ShowDetailActivity;

import java.util.ArrayList;

public class PersonCreditGalleryAdapter extends RecyclerView.Adapter<PersonCreditGalleryAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private ArrayList<ShowInfo> mShowInfoList;
    private Context mContext;

    public PersonCreditGalleryAdapter(Context context, ArrayList<ShowInfo> showInfoList)
    {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mShowInfoList = showInfoList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView mIvPersonCredit;
        int mShowId;
        String mShowType;
        public ViewHolder(View view){
            super(view);
        }
    }


    @Override
    public PersonCreditGalleryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.person_credit_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.mIvPersonCredit = (ImageView) view.findViewById(R.id.iv_person_credit);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final PersonCreditGalleryAdapter.ViewHolder holder, int position) {
        if(position+1 > mShowInfoList.size())
            return;

        ShowInfo showInfo = mShowInfoList.get(position);
        if(showInfo == null)
            return;

        holder.mShowId = showInfo.getShowId();
        holder.mShowType = showInfo.getShowType();

        Glide.with(mContext)
                .load(ImagePathConfigure.getCreditPosterImageUrl(showInfo.getPosterPath()))
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .crossFade()
                .into(holder.mIvPersonCredit);

        holder.mIvPersonCredit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(mContext, ShowDetailActivity.class);
                intent.putExtra(Utils.SHOW_TYPE, holder.mShowType);
                intent.putExtra(ShowDetailActivity.SHOW_ID, holder.mShowId);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mShowInfoList.size();
    }
}
