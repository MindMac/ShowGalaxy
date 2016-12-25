package com.mas.showgalaxy.common;

import android.content.Context;
import android.content.pm.PackageManager;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;

public class Utils {
    public static final String TAG = "ShowGalaxy";
    public static final String SHOW_TYPE = "show_type";
    public static final String YOUTUBE_TRAILER = "YouTube";
    public static final String DIRECTOR = "Director";
    public static final String YOUTUBE_PACKAGE_NAME = "com.google.android.youtube";

    public static String constructYoutubeTrailerBackgroundImageUrl(String trailerId){
        if(trailerId == null || trailerId.equals(""))
            return null;
        return String.format(Locale.US, "https://img.youtube.com/vi/%s/hqdefault.jpg", trailerId);
    }

    public static boolean isAppInstalled(Context context, String packageName){
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
        }

        return false;
    }

    public static String getNetworkResponse(String urlValue){
        String responseBody = null;
        try {
            URL url = new URL(urlValue);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.connect();
            InputStream is = conn.getInputStream();
            responseBody = Utils.getStringByInputstream(is);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return responseBody;
    }

    private static String getStringByInputstream(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder total = new StringBuilder();
        String line;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                total.append(line);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            try {
                bufferedReader.close();
            }catch (IOException ex){
                ex.printStackTrace();
            }
        }
        return total.toString();
    }

    public static ArrayList<String> convertJsonArrayToList(JSONArray jsonArray){
        if(jsonArray == null)
            return null;
        ArrayList<String> arrayList = new ArrayList<String>();
        for(int i=0; i<jsonArray.length(); i++){
            try {
                arrayList.add((String) jsonArray.get(i));
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
        return arrayList;
    }
}
