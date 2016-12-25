package com.mas.showgalaxy.ui.navigation;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.mas.showgalaxy.R;
import com.mas.showgalaxy.adapter.IconCreditListViewAdapter;
import com.mas.showgalaxy.model.IconCredit;

import java.util.ArrayList;

public class AboutFragment extends Fragment{

    private static ArrayList<IconCredit> mIconCreditList = new ArrayList<IconCredit>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.about, container, false);

        ListView listView = (ListView) view.findViewById(R.id.lv_icon_credit);
        IconCreditListViewAdapter iconCreditListViewAdapter = new IconCreditListViewAdapter(view.getContext(), mIconCreditList);
        listView.setAdapter(iconCreditListViewAdapter);
        return view;
    }

    public static void constructIconCreditList(Context context){
        Spanned logoDesc = Html.fromHtml("Icons made by <a href=http://www.flaticon.com/authors/dimi-kazak>Dimi Kazak</a> from <a href=http://www.flaticon.com>FlatIcon</a>");
        Drawable logo = context.getResources().getDrawable(R.drawable.banner_logo);
        mIconCreditList.add(new IconCredit(logo, logoDesc));

        Spanned iconDesc = Html.fromHtml("Icon made by <a href=https://icons8.com>Icons8</a>");
        Drawable movie = context.getResources().getDrawable(R.drawable.ic_menu_movie);
        mIconCreditList.add(new IconCredit(movie, iconDesc));

        Drawable tvShow = context.getResources().getDrawable(R.drawable.ic_menu_tvshow);
        mIconCreditList.add(new IconCredit(tvShow, iconDesc));

        Drawable search = context.getResources().getDrawable(R.drawable.ic_menu_search);
        mIconCreditList.add(new IconCredit(search, iconDesc));

        Drawable about = context.getResources().getDrawable(R.drawable.ic_menu_about);
        mIconCreditList.add(new IconCredit(about, iconDesc));

        Drawable calendar = context.getResources().getDrawable(R.drawable.ic_calendar);
        mIconCreditList.add(new IconCredit(calendar, iconDesc));

        Drawable star = context.getResources().getDrawable(R.drawable.ic_star);
        mIconCreditList.add(new IconCredit(star, iconDesc));

        Drawable popular = context.getResources().getDrawable(R.drawable.ic_popular);
        mIconCreditList.add(new IconCredit(popular, iconDesc));

        Drawable clock = context.getResources().getDrawable(R.drawable.ic_clock);
        mIconCreditList.add(new IconCredit(clock, iconDesc));

        Drawable play = context.getResources().getDrawable(R.drawable.ic_play);
        mIconCreditList.add(new IconCredit(play, iconDesc));

    }

}
