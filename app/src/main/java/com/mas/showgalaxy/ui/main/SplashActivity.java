package com.mas.showgalaxy.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.mas.showgalaxy.R;
import com.mas.showgalaxy.common.Utils;
import com.mas.showgalaxy.model.ConfigureInfo;
import com.mas.showgalaxy.ui.navigation.AboutFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class SplashActivity extends Activity {
    ProgressBar mProgressBar = null;

    private static ExecutorService mExecutor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(),
            new PriorityThreadFactory());

    private static class PriorityThreadFactory implements ThreadFactory {
        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        mProgressBar = (ProgressBar) findViewById(R.id.mpb_splash);
        mProgressBar.getIndeterminateDrawable().setColorFilter(
                getResources().getColor(R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);

        InitTask initTask = new InitTask(mProgressBar, this);
        initTask.executeOnExecutor(mExecutor, (Object) null);
    }

    private class InitTask extends AsyncTask<Object, Integer, ConfigureInfo> {
        private ProgressBar mProgressBar;
        private Activity mActivity;

        public InitTask(ProgressBar progressBar, Activity activity){
            mProgressBar = progressBar;
            mActivity = activity;
        }

        @Override
        protected ConfigureInfo doInBackground(Object... params) {
            // Construct Icon credits
            AboutFragment.constructIconCreditList(mActivity.getApplicationContext());

            // Moviedb configure
            String configureResponse = Utils.getNetworkResponse(String.format(Locale.US, "%s?api_key=%s",
                    ConfigureInfo.CONFIGURE_HOST_URL, ConfigureInfo.API_KEY));
            if(configureResponse == null || configureResponse.equals(""))
                return null;
            try {
                JSONObject configureJson = new JSONObject(configureResponse);
                JSONObject imageConfigureJson = configureJson.getJSONObject("images");
                String imageBaseUrl = imageConfigureJson.getString("base_url");
                ConfigureInfo.imageBaseUrl = imageBaseUrl;

                JSONArray backdropSizeArray = imageConfigureJson.getJSONArray("backdrop_sizes");
                ConfigureInfo.backdropSizeList = Utils.convertJsonArrayToList(backdropSizeArray);

                JSONArray posterSizeArray = imageConfigureJson.getJSONArray("poster_sizes");
                ConfigureInfo.posterSizeList = Utils.convertJsonArrayToList(posterSizeArray);

                JSONArray profileSizeArray = imageConfigureJson.getJSONArray("profile_sizes");
                ConfigureInfo.profileSizeList = Utils.convertJsonArrayToList(profileSizeArray);

                JSONArray stillSizeArray = imageConfigureJson.getJSONArray("still_sizes");
                ConfigureInfo.stillSizeList = Utils.convertJsonArrayToList(stillSizeArray);

            }catch (Exception ex){
                ex.printStackTrace();
            }

            String movieGenreResponse = Utils.getNetworkResponse(String.format(Locale.US, "%s?api_key=%s",
                    ConfigureInfo.MOVIE_GENRE_HOST_URL, ConfigureInfo.API_KEY));
            if(movieGenreResponse == null || movieGenreResponse.equals(""))
                return null;
            try {
                JSONObject genreJson = new JSONObject(movieGenreResponse);
                JSONArray genreArray = genreJson.getJSONArray("genres");
                ConfigureInfo.constructGenre(genreArray, ConfigureInfo.movieGenreMap);

            }catch (Exception ex){
                ex.printStackTrace();
            }

            String tvshowGenreResponse = Utils.getNetworkResponse(String.format(Locale.US, "%s?api_key=%s",
                    ConfigureInfo.TV_SHOW_GENRE_HOST_URL, ConfigureInfo.API_KEY));
            if(tvshowGenreResponse == null || tvshowGenreResponse.equals(""))
                return null;
            try {
                JSONObject genreJson = new JSONObject(tvshowGenreResponse);
                JSONArray genreArray = genreJson.getJSONArray("genres");
                ConfigureInfo.constructGenre(genreArray, ConfigureInfo.tvShowGenreMap);

            }catch (Exception ex){
                ex.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(ConfigureInfo configureInfo) {
            super.onPostExecute(configureInfo);
            mProgressBar.setVisibility(ProgressBar.INVISIBLE);
            Intent intent = new Intent();
            intent.setClass(mActivity, ShowNavigationActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mActivity.startActivity(intent);

            mActivity.finish();

        }
    }



}
