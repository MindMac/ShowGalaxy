package com.mas.showgalaxy.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mas.showgalaxy.R;
import com.mas.showgalaxy.model.PersonInfo;
import com.mas.showgalaxy.moviedb.ImagePathConfigure;
import com.mas.showgalaxy.ui.content.PersonDetailActivity;

import java.util.ArrayList;
import java.util.Locale;

public class ShowPersonGalleryAdapter extends RecyclerView.Adapter<ShowPersonGalleryAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private ArrayList<PersonInfo> mPersonInfoList;
    private Context mContext;

    public ShowPersonGalleryAdapter(Context context, ArrayList<PersonInfo> personInfoList)
    {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mPersonInfoList = personInfoList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView mIvPerson;
        TextView mTvName;
        TextView mTvCharacter;
        int mPersonId;
        public ViewHolder(View view){
            super(view);
        }
    }


    @Override
    public ShowPersonGalleryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.show_detail_person_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.mIvPerson = (ImageView) view.findViewById(R.id.iv_show_person);
        viewHolder.mTvName = (TextView) view.findViewById(R.id.tv_show_person_name);
        viewHolder.mTvCharacter = (TextView) view.findViewById(R.id.tv_show_people_character);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ShowPersonGalleryAdapter.ViewHolder holder, int position) {
        if(position+1 > mPersonInfoList.size())
            return;

        PersonInfo personInfo = mPersonInfoList.get(position);
        if(personInfo == null)
            return;

        holder.mPersonId = personInfo.getPersonId();

        Glide.with(mContext)
                .load(ImagePathConfigure.getPersonImageUrl(personInfo.getProfilePath()))
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .crossFade()
                .into(holder.mIvPerson);
        holder.mTvName.setText(personInfo.getName());
        if(personInfo.getCharacter() != null && !personInfo.getCharacter().equals(""))
            holder.mTvCharacter.setText(String.format(Locale.US, "as %s", personInfo.getCharacter()));

        holder.mIvPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(mContext, PersonDetailActivity.class);
                intent.putExtra(PersonDetailActivity.PERSON_ID, holder.mPersonId);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPersonInfoList.size();
    }
}
