package com.mas.showgalaxy.application;

import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


public class MyApplication extends Application {
    private static MyApplication myApplicationInst;

    private volatile static RequestQueue mRequestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        myApplicationInst = this;
    }

    public static synchronized MyApplication getInstance() {
        return myApplicationInst;
    }

    // Singleton, lazy load
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            synchronized (MyApplication.class) {
                if (mRequestQueue == null) {
                    mRequestQueue = Volley.newRequestQueue(getApplicationContext());
                }
            }
        }
        return mRequestQueue;
    }


    public <T> void addToRequestQueue(Request<T> request) {
        getRequestQueue().add(request);
    }

}