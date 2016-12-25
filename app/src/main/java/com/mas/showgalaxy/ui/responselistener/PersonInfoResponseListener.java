package com.mas.showgalaxy.ui.responselistener;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.bumptech.glide.Glide;
import com.mas.showgalaxy.R;
import com.mas.showgalaxy.model.PersonInfo;
import com.mas.showgalaxy.moviedb.ImagePathConfigure;

import org.json.JSONObject;

public class PersonInfoResponseListener implements Response.Listener<JSONObject> {
    private Context mContext = null;
    private PersonInfo mPersonInfo = null;
    private View mPeronDetailView = null;

    public PersonInfoResponseListener(Context context, View personDetailView){
        this.mContext = context;
        this.mPeronDetailView = personDetailView;
    }

    @Override
    public void onResponse(JSONObject response) {
        constructPersonInfo(response);

        if(mPersonInfo == null || mPeronDetailView == null)
            return;
        ImageView personView = (ImageView) mPeronDetailView.findViewById(R.id.iv_person);
        TextView personNameView = (TextView) mPeronDetailView.findViewById(R.id.tv_person_name);
        TextView personBirthday = (TextView) mPeronDetailView.findViewById(R.id.tv_person_birthday);
        TextView personBirthplace = (TextView) mPeronDetailView.findViewById(R.id.tv_person_birthplace);
        TextView personBiography = (TextView) mPeronDetailView.findViewById(R.id.tv_person_biography);

        Glide.with(mContext)
                .load(ImagePathConfigure.getPersonImageUrl(mPersonInfo.getProfilePath()))
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(personView);

        personNameView.setText(mPersonInfo.getName());
        personBirthday.setText(mPersonInfo.getBirthday());
        personBirthplace.setText(mPersonInfo.getBirthPlace());
        personBiography.setText(mPersonInfo.getBiography());
    }


    private void constructPersonInfo(JSONObject response){
        if(response == null || response.length() <= 0)
            return;
        try {
            String personName = response.getString("name");
            int personId = response.getInt("id");
            String birthday = response.getString("birthday");
            String birthplace = response.getString("place_of_birth");
            String profilePath = response.getString("profile_path");
            String biography = response.getString("biography");

            mPersonInfo = new PersonInfo(personId, personName, biography, birthday, birthplace, profilePath);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
