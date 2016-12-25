package com.mas.showgalaxy.ui.content;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mas.showgalaxy.R;
import com.mas.showgalaxy.common.Utils;
import com.mas.showgalaxy.moviedb.ImagePathConfigure;

public class ShowOverviewBackdropFragment extends Fragment {
    private static final String SHOW_ID = "show_id";
    private static final String IMAGE_URL = "image_url";
    private static final String SHOW_TITLE = "show_title";

    private String mShowType;
    private int mShowId;
    private String mImageUrl = null;
    private String mShowTitle = null;



    public static ShowOverviewBackdropFragment newInstance(String showType, int showId, String imageUrl,
                                                           String showTitle) {
        ShowOverviewBackdropFragment movieOverviewBackdropFragment = new ShowOverviewBackdropFragment();
        Bundle arguments = new Bundle();
        arguments.putString(Utils.SHOW_TYPE, showType);
        arguments.putInt(SHOW_ID, showId);
        arguments.putString(IMAGE_URL, imageUrl);
        arguments.putString(SHOW_TITLE, showTitle);
        movieOverviewBackdropFragment.setArguments(arguments);
        return movieOverviewBackdropFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mShowType = getArguments().getString(Utils.SHOW_TYPE);
        mShowId = getArguments().getInt(SHOW_ID);
        mImageUrl = getArguments().getString(IMAGE_URL);
        mShowTitle = getArguments().getString(SHOW_TITLE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.show_overview_backdrop, container, false);

        TextView textView = (TextView) view.findViewById(R.id.tv_popular_movie_title);
        textView.setText(mShowTitle);

        ImageView imageView = (ImageView) view.findViewById(R.id.iv_show_backdrop);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), ShowDetailActivity.class);
                intent.putExtra(ShowDetailActivity.SHOW_ID, mShowId);
                intent.putExtra(Utils.SHOW_TYPE, mShowType);
                startActivity(intent);
            }
        });

        Glide.with(this.getContext())
                .load(ImagePathConfigure.getBackdropImageUrl(mImageUrl))
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .crossFade()
                .into(imageView);
        return view;
    }
}
