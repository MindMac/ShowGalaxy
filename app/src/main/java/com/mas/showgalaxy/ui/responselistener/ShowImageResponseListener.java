package com.mas.showgalaxy.ui.responselistener;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Response;
import com.mas.showgalaxy.adapter.ShowBackdropGalleryAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ShowImageResponseListener implements Response.Listener<JSONObject> {
    private Context mContext = null;
    private ArrayList<String> mBackdropPathList = new ArrayList<String>();
    private ArrayList<String> mPosterPathList = new ArrayList<String>();
    private RecyclerView mShowBackdropGalleryRecyclerView = null;

    public ShowImageResponseListener(Context context, RecyclerView showBackdropGalleryRecyclerView){
        this.mContext = context;
        this.mShowBackdropGalleryRecyclerView = showBackdropGalleryRecyclerView;
    }

    @Override
    public void onResponse(JSONObject response) {
        constructShowImage(response);

        ShowBackdropGalleryAdapter mShowBackdropGalleryAdapter = new ShowBackdropGalleryAdapter(mContext, mBackdropPathList);
        mShowBackdropGalleryRecyclerView.setAdapter(mShowBackdropGalleryAdapter);
    }

    private void constructShowImage(JSONObject response){
        if(response == null || response.length() <= 0)
            return;
        try {
            JSONArray backdropArray = response.getJSONArray("backdrops");
            JSONArray posterArray = response.getJSONArray("posters");
            if(backdropArray == null || posterArray == null)
                return;

            for(int i=0; i<backdropArray.length(); i++){
                JSONObject backdropInfo = backdropArray.getJSONObject(i);
                if(backdropInfo == null)
                    continue;
                String backdropPath = backdropInfo.getString("file_path");
                if(backdropPath != null)
                    mBackdropPathList.add(backdropPath);
            }

            for(int i=0; i<posterArray.length(); i++){
                JSONObject posterInfo = posterArray.getJSONObject(i);
                if(posterInfo == null)
                    continue;
                String posterPath = posterInfo.getString("file_path");
                if(posterPath != null)
                    mPosterPathList.add(posterPath);
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
