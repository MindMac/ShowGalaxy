package com.mas.showgalaxy.ui.responselistener;

import com.android.volley.Response;
import com.mas.showgalaxy.adapter.SearchPersonListViewAdapter;
import com.mas.showgalaxy.model.PersonInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchPersonResponseListener implements Response.Listener<JSONObject> {
    private int mTotalPages;
    private ArrayList<PersonInfo> mPersonInfoList = new ArrayList<PersonInfo>();
    private SearchPersonListViewAdapter mSearchPersonListViewAdapter = null;

    public SearchPersonResponseListener(SearchPersonListViewAdapter searchPersonListViewAdapter){
        this.mSearchPersonListViewAdapter = searchPersonListViewAdapter;
    }


    @Override
    public void onResponse(JSONObject response) {
        constructPersonInfo(response);
        mSearchPersonListViewAdapter.setTotalPages(mTotalPages);
        mSearchPersonListViewAdapter.nextPage(mPersonInfoList);
    }

    void constructPersonInfo(JSONObject response){
        if(response == null || response.length() <= 0)
            return;
        try {
            mTotalPages = response.getInt("total_pages");
            JSONArray personArray = response.getJSONArray("results");
            if(personArray != null){
                for(int i=0; i<personArray.length(); i++){
                    JSONObject personInfo = personArray.getJSONObject(i);
                    String personName = personInfo.getString("name");
                    int personId = personInfo.getInt("id");
                    String profilePath = personInfo.getString("profile_path");

                    PersonInfo personInfoObj = new PersonInfo(personId, personName, profilePath);
                    mPersonInfoList.add(personInfoObj);
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
