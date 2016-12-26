package com.mas.showgalaxy.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;

public class ConfigureInfo {
    public static final String CONFIGURE_HOST_URL = "https://api.themoviedb.org/3/configuration";
    public static final String MOVIE_GENRE_HOST_URL = "https://api.themoviedb.org/3/genre/movie/list";
    public static final String TV_SHOW_GENRE_HOST_URL = "https://api.themoviedb.org/3/genre/tv/list";
    public static final String MOVIE_HOST_URL = "https://api.themoviedb.org/3/movie";
    public static final String TV_SHOW_HOST_URL = "https://api.themoviedb.org/3/tv";
    public static final String PERSON_HOST_URL = "https://api.themoviedb.org/3/person";

    public static final String MOVIE_SEARCH_HOST_URL = "https://api.themoviedb.org/3/search/movie";
    public static final String TV_SHOW_SEARCH_HOST_URL = "https://api.themoviedb.org/3/search/tv";
    public static final String PEOPLE_SEARCH_HOST_URL = "https://api.themoviedb.org/3/search/person";

    public static final String API_KEY = "your moviedb key";

    public static String imageBaseUrl = null;
    public static ArrayList<String> backdropSizeList = new ArrayList<String>();
    public static ArrayList<String> posterSizeList = new ArrayList<String>();
    public static ArrayList<String> profileSizeList = new ArrayList<String>();
    public static ArrayList<String> stillSizeList = new ArrayList<String>();

    public static Hashtable<Integer, String> movieGenreMap = new Hashtable<Integer, String>();
    public static Hashtable<Integer, String> tvShowGenreMap = new Hashtable<Integer, String>();


    public static String getBackdropSize(){
        if(backdropSizeList == null)
            return null;
        int backdropSizeNum = backdropSizeList.size();
        if(backdropSizeNum > 3){
            return backdropSizeList.get(backdropSizeNum-3);
        }else
            return null;
    }

    public static String getLargePosterSize(){
        if(posterSizeList == null)
            return null;
        int posterSizeNum = posterSizeList.size();
        if(posterSizeNum > 2){
            return posterSizeList.get(posterSizeNum-2);
        }else
            return "w780";
    }

    public static String getMediumPosterSize(){
        if(posterSizeList == null)
            return null;
        int posterSizeNum = posterSizeList.size();
        if(posterSizeNum > 6){
            return posterSizeList.get(3);
        }else
            return "w342";
    }


    public static String getSmallPosterSize(){
        if(posterSizeList == null)
            return null;
        int posterSizeNum = posterSizeList.size();
        if(posterSizeNum > 2){
            return posterSizeList.get(1);
        }else
            return "w154";
    }


    public static String getProfileSize(){
        if(profileSizeList == null)
            return null;
        int profileSizeNum = profileSizeList.size();
        if(profileSizeNum > 2){
            return profileSizeList.get(profileSizeNum-2);
        }else
            return "w780";
    }

    public static String getStillSize(){
        if(stillSizeList == null)
            return null;
        int stillSizeNum = stillSizeList.size();
        if(stillSizeNum > 2){
            return stillSizeList.get(stillSizeNum-2);
        }else
            return "w780";
    }

    public static ArrayList<String> getMovieGenreNames(ArrayList<Integer> genreIds){
        ArrayList<String> genreNames = new ArrayList<String>();
        for(Integer genreId : genreIds){
            if(movieGenreMap.containsKey(genreId))
                genreNames.add(movieGenreMap.get(genreId));
        }
        return genreNames;
    }

    public static ArrayList<String> getTvShowGenreNames(ArrayList<Integer> genreIds){
        ArrayList<String> genreNames = new ArrayList<String>();
        for(Integer genreId : genreIds){
            if(tvShowGenreMap.containsKey(genreId))
                genreNames.add(tvShowGenreMap.get(genreId));
        }
        return genreNames;
    }

    public static void constructGenre(JSONArray genreArray, Hashtable<Integer, String> genreMap){
        if(genreArray == null)
            return;
        for(int i=0; i<genreArray.length(); i++){
            try {
                JSONObject jsonObject = genreArray.getJSONObject(i);
                genreMap.put((Integer) jsonObject.get("id"), (String)jsonObject.get("name"));
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }
}
