package com.mas.showgalaxy.moviedb;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.mas.showgalaxy.application.MyApplication;
import com.mas.showgalaxy.common.Utils;

import org.json.JSONObject;

import java.util.Locale;

import static com.mas.showgalaxy.model.ConfigureInfo.API_KEY;
import static com.mas.showgalaxy.model.ConfigureInfo.MOVIE_HOST_URL;
import static com.mas.showgalaxy.model.ConfigureInfo.MOVIE_SEARCH_HOST_URL;
import static com.mas.showgalaxy.model.ConfigureInfo.PEOPLE_SEARCH_HOST_URL;
import static com.mas.showgalaxy.model.ConfigureInfo.PERSON_HOST_URL;
import static com.mas.showgalaxy.model.ConfigureInfo.TV_SHOW_HOST_URL;
import static com.mas.showgalaxy.model.ConfigureInfo.TV_SHOW_SEARCH_HOST_URL;


public class DataQuery {
    public static final String MOVIE = "movie";
    public static final String TV_SHOW = "tv_show";
    public static final String PEOPLE = "people";

    public enum QueryType{
        NOW_PLAYING, POPULAR, TOP_RATE, UPCOMING
    }

    private static String constructShowInfoUrl(String showType, QueryType queryType, int pageNum){
        String url = null;


        if(queryType == QueryType.POPULAR){
            if(showType.equals(MOVIE))
                url = String.format(Locale.US, "%s/popular?api_key=%s&language=en-US&region=US&page=%d",
                        MOVIE_HOST_URL, API_KEY, pageNum);
            else if(showType.equals((TV_SHOW)))
                url = String.format(Locale.US, "%s/popular?api_key=%s&language=en-US&&page=%d",
                        TV_SHOW_HOST_URL, API_KEY, pageNum);
        }

        else if(queryType == QueryType.NOW_PLAYING) {
            if(showType.equals(MOVIE))
                url = String.format(Locale.US, "%s/now_playing?api_key=%s&language=en-US&region=US&page=%d",
                        MOVIE_HOST_URL, API_KEY, pageNum);
            else if(showType.equals(TV_SHOW))
                url = String.format(Locale.US, "%s/airing_today?api_key=%s&language=en-US&page=%d",
                        TV_SHOW_HOST_URL, API_KEY, pageNum);
        }
        else if(queryType == QueryType.UPCOMING) {
            if(showType.equals(MOVIE))
                url = String.format(Locale.US, "%s/upcoming?api_key=%s&language=en-US&region=US&page=%d",
                        MOVIE_HOST_URL, API_KEY, pageNum);
            else if(showType.equals(TV_SHOW))
                url = String.format(Locale.US, "%s/on_the_air?api_key=%s&language=en-US&page=%d",
                        TV_SHOW_HOST_URL, API_KEY, pageNum);
        }
        else if(queryType == QueryType.TOP_RATE) {
            if(showType.equals(MOVIE))
                url = String.format(Locale.US, "%s/top_rated?api_key=%s&language=en-US&region=US&page=%d",
                        MOVIE_HOST_URL, API_KEY, pageNum);
            else if(showType.equals(TV_SHOW))
                url = String.format(Locale.US, "%s/top_rated?api_key=%s&language=en-US&page=%d",
                        TV_SHOW_HOST_URL, API_KEY, pageNum);
        }

        return url;
    }

    public static void getShowInfos(Listener<JSONObject> responseListener, String showType,
                                    QueryType queryType, int pageNum){
        String url = constructShowInfoUrl(showType, queryType, pageNum);
        if(url == null)
            return;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                responseListener, new ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(Utils.TAG, "Error: " + error.getMessage());
                    }
        });

        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(jsonObjectRequest);
    }

    public static void getShowDetailInfo(Listener<JSONObject> responseListener, String showType,
                                         int showId){
        String url = null;
        if(showType.equals(MOVIE))
            url = String.format(Locale.US, "%s/%d?api_key=%s&language=en-US", MOVIE_HOST_URL,
                    showId, API_KEY);
        else if(showType.equals(TV_SHOW))
            url = String.format(Locale.US, "%s/%d?api_key=%s&language=en-US", TV_SHOW_HOST_URL,
                    showId, API_KEY);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                responseListener, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(Utils.TAG, "Error: " + error.getMessage());
            }
        });

        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(jsonObjectRequest);
    }

    public static void getShowImages(Listener<JSONObject> responseListener, String showType, int showId){
        String url = null;
        if(showType.equals(MOVIE))
            url = String.format(Locale.US, "%s/%d/images?api_key=%s&language=en-US&include_image_language=en,null",
                MOVIE_HOST_URL, showId, API_KEY);
        else if(showType.equals(TV_SHOW))
            url = String.format(Locale.US, "%s/%d/images?api_key=%s&language=en-US&include_image_language=en,null",
                    TV_SHOW_HOST_URL, showId, API_KEY);
        if(url == null)
            return;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                responseListener, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(Utils.TAG, "Error: " + error.getMessage());
            }
        });

        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(jsonObjectRequest);
    }

    public static void getShowTrailers(Listener<JSONObject> responseListener, String showType, int showId){
        String url = null;
        if(showType.equals(MOVIE))
            url = String.format(Locale.US, "%s/%d/videos?api_key=%s&language=en-US",
                    MOVIE_HOST_URL, showId, API_KEY);
        else if(showType.equals(TV_SHOW))
            url = String.format(Locale.US, "%s/%d/videos?api_key=%s&language=en-US",
                    TV_SHOW_HOST_URL, showId, API_KEY);
        if(url == null)
            return;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                responseListener, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(Utils.TAG, "Error: " + error.getMessage());
            }
        });

        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(jsonObjectRequest);
    }

    public static void getShowCredits(Listener<JSONObject> responseListener, String showType, int showId){
        String url = null;
        if(showType.equals(MOVIE))
            url = String.format(Locale.US, "%s/%d/credits?api_key=%s&language=en-US",
                    MOVIE_HOST_URL, showId, API_KEY);
        else if(showType.equals(TV_SHOW))
            url = String.format(Locale.US, "%s/%d/credits?api_key=%s&language=en-US",
                    TV_SHOW_HOST_URL, showId, API_KEY);
        if(url == null)
            return;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                responseListener, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(Utils.TAG, "Error: " + error.getMessage());
            }
        });

        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(jsonObjectRequest);
    }

    public static void getPersonDetail(Listener<JSONObject> responseListener, int personId){
        String url = String.format(Locale.US, "%s/%d?api_key=%s&language=en-US",
                    PERSON_HOST_URL, personId, API_KEY);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                responseListener, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(Utils.TAG, "Error: " + error.getMessage());
            }
        });

        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(jsonObjectRequest);
    }

    public static void getPersonMovieCredits(Listener<JSONObject> responseListener, int personId){
        String url = String.format(Locale.US, "%s/%d/movie_credits?api_key=%s&language=en-US",
                PERSON_HOST_URL, personId, API_KEY);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                responseListener, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(Utils.TAG, "Error: " + error.getMessage());
            }
        });

        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(jsonObjectRequest);
    }

    public static void getPersonTvCredits(Listener<JSONObject> responseListener, int personId){
        String url = String.format(Locale.US, "%s/%d/tv_credits?api_key=%s&language=en-US",
                PERSON_HOST_URL, personId, API_KEY);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                responseListener, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(Utils.TAG, "Error: " + error.getMessage());
            }
        });

        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(jsonObjectRequest);
    }

    public static void getSeasonDetail(Listener<JSONObject> responseListener, int tvId, int seasonNum){
        String url = String.format(Locale.US, "%s/%d/season/%d?api_key=%s&language=en-US",
                TV_SHOW_HOST_URL, tvId, seasonNum, API_KEY);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                responseListener, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(Utils.TAG, "Error: " + error.getMessage());
            }
        });

        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(jsonObjectRequest);
    }

    public static void getSearchResult(Listener<JSONObject> responseListener, String searchType,
                                       String searchText, int pageNum){

        String url = null;
        if(searchType.equals(MOVIE))
            url = String.format(Locale.US, "%s?api_key=%s&language=en-US&query=%s&page=%d&include_adult=false",
                    MOVIE_SEARCH_HOST_URL, API_KEY, searchText, pageNum);
        else if(searchType.equals(TV_SHOW))
            url = String.format(Locale.US, "%s?api_key=%s&language=en-US&query=%s&page=%d",
                    TV_SHOW_SEARCH_HOST_URL, API_KEY, searchText, pageNum);
        else if(searchType.equals(PEOPLE))
            url = String.format(Locale.US, "%s?api_key=%s&language=en-US&query=%s&page=%d&include_adult=false",
                    PEOPLE_SEARCH_HOST_URL, API_KEY, searchText, pageNum);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                responseListener, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(Utils.TAG, "Error: " + error.getMessage());
            }
        });

        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(jsonObjectRequest);
    }


}
