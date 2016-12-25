package com.mas.showgalaxy.ui.navigation;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ToxicBakery.viewpager.transforms.AccordionTransformer;
import com.mas.showgalaxy.R;
import com.mas.showgalaxy.adapter.ShowCategoryViewPagerAdapter;
import com.viewpagerindicator.TitlePageIndicator;
import com.viewpagerindicator.TitlePageIndicator.IndicatorStyle;

public class ShowCategoryFragment extends Fragment {
    private String mShowType = null;
    public static final String SHOW_TYPE = "showType";

    public static ShowCategoryFragment newInstance(String showType) {
        ShowCategoryFragment showCategoryFragment = new ShowCategoryFragment();
        Bundle arguments = new Bundle();
        arguments.putString(SHOW_TYPE, showType);
        showCategoryFragment.setArguments(arguments);
        return showCategoryFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mShowType = getArguments().getString(SHOW_TYPE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.show_category_fragment, container, false);

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.vp_show_category);
        ShowCategoryViewPagerAdapter showCategoryViewPagerAdapter = new
                ShowCategoryViewPagerAdapter(getChildFragmentManager(), mShowType, this.getContext());
        viewPager.setAdapter(showCategoryViewPagerAdapter);

        // Transformer
        viewPager.setPageTransformer(true, new AccordionTransformer());

        // Page title
        TitlePageIndicator indicator = (TitlePageIndicator) view.findViewById(R.id.tpi_show_category);
        indicator.setViewPager(viewPager);
        setIndicatorStyle(indicator);

        return view;
    }

    private void setIndicatorStyle(TitlePageIndicator indicator){
        final float density = getResources().getDisplayMetrics().density;
        indicator.setBackgroundColor(ContextCompat.getColor(this.getContext(), R.color.colorPrimary));
        indicator.setFooterColor(ContextCompat.getColor(this.getContext(), R.color.colorPrimaryLight));
        indicator.setFooterLineHeight(1 * density);
        indicator.setFooterIndicatorHeight(3 * density);
        indicator.setFooterIndicatorStyle(IndicatorStyle.Underline);
        indicator.setTextSize(15 * density);
        indicator.setTextColor(ContextCompat.getColor(this.getContext(), R.color.colorLightGray));
        indicator.setSelectedColor(Color.WHITE);
        indicator.setSelectedBold(true);
    }
}
