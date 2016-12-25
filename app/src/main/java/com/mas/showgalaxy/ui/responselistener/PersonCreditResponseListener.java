package com.mas.showgalaxy.ui.responselistener;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.volley.Response;
import com.mas.showgalaxy.adapter.PersonCreditGalleryAdapter;
import com.mas.showgalaxy.common.Utils;
import com.mas.showgalaxy.model.ShowInfo;
import com.mas.showgalaxy.moviedb.DataQuery;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class PersonCreditResponseListener implements Response.Listener<JSONObject> {
    private Context mContext = null;
    private String mShowType = null;
    private View mRootView = null;
    private ArrayList<ShowInfo> mShowInfoList = new ArrayList<ShowInfo>();
    private RecyclerView mPersonCreditRecyclerView = null;

    public PersonCreditResponseListener(Context context, String showType, View rootView,
                                        RecyclerView personCreditRecyclerView){
        this.mContext = context;
        this.mShowType = showType;
        this.mRootView = rootView;
        this.mPersonCreditRecyclerView = personCreditRecyclerView;
    }

    @Override
    public void onResponse(JSONObject response) {
        constructPersonCreditInfo(response);
        if(mShowInfoList.size() == 0)
            return;

        mRootView.setVisibility(View.VISIBLE);

        PersonCreditGalleryAdapter personCreditGalleryAdapter = new PersonCreditGalleryAdapter(mContext, mShowInfoList);
        mPersonCreditRecyclerView.setAdapter(personCreditGalleryAdapter);
    }

    private void constructPersonCreditInfo(JSONObject response){
        if(response == null || response.length() <= 0)
            return;
        try {
            JSONArray castArray = response.getJSONArray("cast");
            JSONArray crewArray = response.getJSONArray("crew");
            if(castArray == null && crewArray == null)
                return;

            for(int i=0; i<castArray.length(); i++){
                JSONObject castInfo = castArray.getJSONObject(i);
                if(castInfo == null)
                    continue;
                int showId = castInfo.getInt("id");
                String title = "";
                if(mShowType.equals(DataQuery.MOVIE))
                    title = castInfo.getString("original_title");
                else
                    title = castInfo.getString("original_name");
                String posterPath = castInfo.getString("poster_path");
                if(posterPath == null || posterPath.equals(""))
                    continue;
                ShowInfo showInfo = new ShowInfo(mShowType, showId, title, posterPath);
                mShowInfoList.add(showInfo);
            }

            for(int i=0; i<crewArray.length(); i++){
                JSONObject crewInfo = crewArray.getJSONObject(i);
                if(crewInfo == null)
                    continue;
                String character = crewInfo.getString("job");
                if(!character.equals(Utils.DIRECTOR))
                    continue;
                int showId = crewInfo.getInt("id");
                String title = "";
                if(mShowType.equals(DataQuery.MOVIE))
                    title = crewInfo.getString("original_title");
                else
                    title = crewInfo.getString("original_name");
                String posterPath = crewInfo.getString("poster_path");
                if(posterPath == null || posterPath.equals(""))
                    continue;
                ShowInfo showInfo = new ShowInfo(mShowType, showId, title, posterPath);
                mShowInfoList.add(showInfo);
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}

