package com.mas.showgalaxy.model;

import android.graphics.drawable.Drawable;
import android.text.Spanned;


public class IconCredit {
    private Drawable mIconDrawable;
    private Spanned mIconDesc;

    public IconCredit(Drawable iconDrawable, Spanned iconDesc){
        this.mIconDrawable = iconDrawable;
        this.mIconDesc = iconDesc;
    }

    public Drawable getIconDrawable(){
        return mIconDrawable;
    }

    public Spanned getIconDesc(){
        return mIconDesc;
    }
}
