package com.mas.showgalaxy.ui.responselistener;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Response;
import com.mas.showgalaxy.adapter.ShowTrailerGalleryAdapter;
import com.mas.showgalaxy.common.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ShowTrailerResponseListener implements Response.Listener<JSONObject> {
    private Context mContext = null;
    private ArrayList<String> mTrailerIdList = new ArrayList<String>();
    private RecyclerView mShowTrailerGalleryRecyclerView = null;
    private ShowTrailerGalleryAdapter mShowTrailerGalleryAdapter = null;

    public ShowTrailerResponseListener(Context context, RecyclerView showTrailerGalleryRecyclerView){
        this.mContext = context;
        this.mShowTrailerGalleryRecyclerView = showTrailerGalleryRecyclerView;
    }

    @Override
    public void onResponse(JSONObject response) {
        constructShowTrailerInfo(response);

        mShowTrailerGalleryAdapter = new ShowTrailerGalleryAdapter(mContext, mTrailerIdList);
        mShowTrailerGalleryRecyclerView.setAdapter(mShowTrailerGalleryAdapter);
    }

    private void constructShowTrailerInfo(JSONObject response){
        if(response == null || response.length() <= 0)
            return;
        try {
            JSONArray trailerArray = response.getJSONArray("results");
            if(trailerArray == null)
                return;

            for(int i=0; i<trailerArray.length(); i++){
                JSONObject trailerInfo = trailerArray.getJSONObject(i);
                if(trailerInfo == null)
                    continue;
                String site = trailerInfo.getString("site");
                String trailerId = trailerInfo.getString("key");
                if(trailerId != null && site != null && site.equals(Utils.YOUTUBE_TRAILER))
                    mTrailerIdList.add(trailerId);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}

