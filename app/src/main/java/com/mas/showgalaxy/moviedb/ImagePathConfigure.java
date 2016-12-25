package com.mas.showgalaxy.moviedb;

import com.mas.showgalaxy.model.ConfigureInfo;

import java.util.Locale;

public class ImagePathConfigure {

    public static String getBackdropImageUrl(String path){
        return String.format(Locale.US, "%s/%s/%s", ConfigureInfo.imageBaseUrl, ConfigureInfo.getBackdropSize(),
                path);
    }

    public static String getSeasonPosterImageUrl(String path){
        return String.format("%s/%s/%s", ConfigureInfo.imageBaseUrl, ConfigureInfo.getLargePosterSize(),
                path);
    }

    public static String getListPosterImageUrl(String path){
        return String.format("%s/%s/%s", ConfigureInfo.imageBaseUrl, ConfigureInfo.getSmallPosterSize(),
                path);
    }

    public static String getCreditPosterImageUrl(String path){
        return String.format("%s/%s/%s", ConfigureInfo.imageBaseUrl, ConfigureInfo.getSmallPosterSize(),
                path);
    }

    public static String getPersonImageUrl(String path){
        return String.format("%s/%s/%s", ConfigureInfo.imageBaseUrl, ConfigureInfo.getSmallPosterSize(),
                path);
    }

}
