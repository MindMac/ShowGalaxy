package com.mas.showgalaxy.ui.responselistener;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Response;
import com.mas.showgalaxy.adapter.ShowPersonGalleryAdapter;
import com.mas.showgalaxy.common.Utils;
import com.mas.showgalaxy.model.PersonInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ShowPersonResponseListener implements Response.Listener<JSONObject> {
    private static final int SHOW_PERSON_LIMIT = 15;

    private Context mContext = null;
    private ArrayList<PersonInfo> mPersonList = new ArrayList<PersonInfo>();
    private RecyclerView mShowPersonGalleryRecyclerView = null;
    private ShowPersonGalleryAdapter mShowPersonGalleryAdapter = null;

    public ShowPersonResponseListener(Context context, RecyclerView showPeopleGalleryRecyclerView){
        this.mContext = context;
        this.mShowPersonGalleryRecyclerView = showPeopleGalleryRecyclerView;
    }

    @Override
    public void onResponse(JSONObject response) {
        constructShowPeopleInfo(response);

        mShowPersonGalleryAdapter = new ShowPersonGalleryAdapter(mContext, mPersonList);
        mShowPersonGalleryRecyclerView.setAdapter(mShowPersonGalleryAdapter);
    }

    private void constructShowPeopleInfo(JSONObject response){
        if(response == null || response.length() <= 0)
            return;
        try {
            JSONArray castArray = response.getJSONArray("cast");
            JSONArray crewArray = response.getJSONArray("crew");
            if(castArray == null && crewArray == null)
                return;

            for(int i=0; i<crewArray.length(); i++){
                JSONObject crewInfo = crewArray.getJSONObject(i);
                if(crewInfo == null)
                    continue;
                String character = crewInfo.getString("job");
                if(!character.equals(Utils.DIRECTOR))
                    continue;
                int peopleId = crewInfo.getInt("id");
                String name = crewInfo.getString("name");
                String profilePath = crewInfo.getString("profile_path");
                if(profilePath == null || profilePath.equals(""))
                    continue;
                PersonInfo personInfo = new PersonInfo(peopleId, name, character, profilePath);
                mPersonList.add(personInfo);
                if(mPersonList.size() >= SHOW_PERSON_LIMIT)
                    return;
            }

            for(int i=0; i<castArray.length(); i++){
                JSONObject castInfo = castArray.getJSONObject(i);
                if(castInfo == null)
                    continue;
                String character = castInfo.getString("character");
                int peopleId = castInfo.getInt("id");
                String name = castInfo.getString("name");
                String profilePath = castInfo.getString("profile_path");
                if(profilePath == null || profilePath.equals(""))
                    continue;
                PersonInfo personInfo = new PersonInfo(peopleId, name, character, profilePath);
                mPersonList.add(personInfo);
                if(mPersonList.size() >= SHOW_PERSON_LIMIT)
                    return;
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}

