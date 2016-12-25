package com.mas.showgalaxy.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mas.showgalaxy.R;
import com.mas.showgalaxy.common.Utils;
import com.mas.showgalaxy.model.ConfigureInfo;
import com.mas.showgalaxy.model.ShowInfo;
import com.mas.showgalaxy.moviedb.DataQuery;
import com.mas.showgalaxy.moviedb.ImagePathConfigure;
import com.mas.showgalaxy.ui.content.ShowDetailActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ShowListViewAdapter extends ArrayAdapter<ShowInfo> {

    List<ShowInfo> mShowInfoList;
    private LayoutInflater mInflater = null;
    int mTotalPages = 1;
    int mCurPage = 1;
    private Context mContext;
    String mShowType;
    int mPreLastItem = 0;

    public ShowListViewAdapter(Context context, ArrayList<ShowInfo> showInfoList, String showType) {
        super(context, R.layout.show_overview_info_item, showInfoList);
        mContext = context;
        mShowInfoList = showInfoList;
        mShowType = showType;
        mCurPage = 1;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setTotalPages(int totalPages){
        mTotalPages = totalPages;
    }

    public void nextPage(ArrayList<ShowInfo> showInfoList){
        if(showInfoList == null || showInfoList.size() == 0)
            return;

        mCurPage += 1;
        mShowInfoList.addAll(showInfoList);
        this.notifyDataSetChanged();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.show_overview_info_item, null);
            holder = new ViewHolder(convertView, position);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            holder.mPosition = position;
        }

        // Get ShowInfo
        final ShowInfo showInfo = getItem(holder.mPosition);

        // Set app name
        holder.mTvShowTitle.setText(showInfo.getShowTitle());
        holder.mTvShowReleaseDate.setText(showInfo.getReleaseDate());
        holder.mTvShowScoreCount.setText(String.format(Locale.US, "%s/%d", showInfo.getScore(),
                showInfo.getVoteCount()));
        holder.mTvShowPopular.setText(String.format(Locale.US, "%.2f", showInfo.getPopularity()));
        ArrayList<String> genreNames = new ArrayList<String>();
        if(mShowType.equals(DataQuery.MOVIE))
            genreNames = ConfigureInfo.getMovieGenreNames(showInfo.getGenreIds());
        else
            genreNames = ConfigureInfo.getTvShowGenreNames(showInfo.getGenreIds());
        holder.mTvShowCategory.setText(TextUtils.join(",", genreNames));

        Glide.with(this.getContext())
                .load(ImagePathConfigure.getListPosterImageUrl(showInfo.getPosterPath()))
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .crossFade()
                .into(holder.mIvShowPoster);

        holder.mLlShowItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(mContext, ShowDetailActivity.class);
                intent.putExtra(ShowDetailActivity.SHOW_ID, showInfo.getShowId());
                intent.putExtra(Utils.SHOW_TYPE, mShowType);
                mContext.startActivity(intent);
            }
        });

        return convertView;
    }


    // ViewHolder
    private class ViewHolder{
        public int mPosition;
        public LinearLayout mLlShowItem;
        public ImageView mIvShowPoster;
        public TextView mTvShowTitle;
        public TextView mTvShowReleaseDate;
        public TextView mTvShowScoreCount;
        public TextView mTvShowPopular;
        public TextView mTvShowCategory;

        public ViewHolder(View row, int position) {
            mPosition = position;
            mLlShowItem = (LinearLayout) row.findViewById(R.id.ll_show);
            mIvShowPoster = (ImageView) row.findViewById(R.id.iv_show_poster);
            mTvShowTitle = (TextView) row.findViewById(R.id.tv_show_title);
            mTvShowReleaseDate = (TextView) row.findViewById(R.id.tv_show_release_date);
            mTvShowScoreCount = (TextView) row.findViewById(R.id.tv_show_score_count);
            mTvShowPopular = (TextView) row.findViewById(R.id.tv_show_popular);
            mTvShowCategory = (TextView) row.findViewById(R.id.tv_show_category);
        }
    }
}