package com.mas.showgalaxy.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mas.showgalaxy.R;
import com.mas.showgalaxy.model.PersonInfo;
import com.mas.showgalaxy.moviedb.ImagePathConfigure;
import com.mas.showgalaxy.moviedb.DataQuery;
import com.mas.showgalaxy.ui.content.PersonDetailActivity;
import com.mas.showgalaxy.ui.responselistener.SearchPersonResponseListener;

import java.util.ArrayList;
import java.util.List;

public class SearchPersonListViewAdapter extends ArrayAdapter<PersonInfo> implements AbsListView.OnScrollListener, ListViewAdapterInterface{

    private List<PersonInfo> mPersonInfoList;
    private LayoutInflater mInflater = null;
    private int mTotalPages = 1;
    private int mCurPage = 1;
    private Context mContext;
    private int mPreLastItem = 0;
    private String mSearchText = null;

    public SearchPersonListViewAdapter(Context context, ArrayList<PersonInfo> personInfoList, String searchText) {
        super(context, R.layout.search_person_item, personInfoList);
        mContext = context;
        mPersonInfoList = personInfoList;
        mSearchText = searchText;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setTotalPages(int totalPages){
        mTotalPages = totalPages;
    }

    public void nextPage(ArrayList<PersonInfo> personInfoList){
        if(personInfoList == null || personInfoList.size() == 0)
            return;

        mCurPage += 1;
        mPersonInfoList.addAll(personInfoList);
        this.notifyDataSetChanged();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.search_person_item, null);
            holder = new ViewHolder(convertView, position);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            holder.mPosition = position;
        }

        // Get PersonInfo
        final PersonInfo personInfo = getItem(holder.mPosition);

        // Set app name
        holder.mTvPersonName.setText(personInfo.getName());

        Glide.with(this.getContext())
                .load(ImagePathConfigure.getListPosterImageUrl(personInfo.getProfilePath()))
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .crossFade()
                .into(holder.mIvPerson);

        holder.mLlSearchPersonItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(mContext, PersonDetailActivity.class);
                intent.putExtra(PersonDetailActivity.PERSON_ID, personInfo.getPersonId());
                mContext.startActivity(intent);
            }
        });

        return convertView;
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount,
                         int totalItemCount) {

        switch(absListView.getId())
        {
            case R.id.lv_search_result:
                final int lastItem = firstVisibleItem + visibleItemCount;
                if(lastItem == totalItemCount)
                {
                    if(mPreLastItem != lastItem && mCurPage < mTotalPages)
                    {
                        mPreLastItem = lastItem;
                        SearchPersonResponseListener searchPersonResponseListener = new SearchPersonResponseListener(this);
                        DataQuery.getSearchResult(searchPersonResponseListener, DataQuery.PEOPLE, mSearchText, mCurPage+1);
                    }
                }
                break;
        }
    }

    @Override
    public void clearResults() {
        mPersonInfoList.clear();
    }

    // ViewHolder
    private class ViewHolder{
        public int mPosition;
        public LinearLayout mLlSearchPersonItem;
        public ImageView mIvPerson;
        public TextView mTvPersonName;

        public ViewHolder(View row, int position) {
            mPosition = position;
            mLlSearchPersonItem = (LinearLayout) row.findViewById(R.id.ll_search_person);
            mIvPerson = (ImageView) row.findViewById(R.id.iv_person);
            mTvPersonName = (TextView) row.findViewById(R.id.tv_person_name);
        }
    }
}