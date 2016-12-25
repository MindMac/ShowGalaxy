package com.mas.showgalaxy.adapter;

import android.content.Context;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mas.showgalaxy.R;
import com.mas.showgalaxy.model.IconCredit;

import java.util.ArrayList;

public class IconCreditListViewAdapter extends ArrayAdapter<IconCredit> {

    private LayoutInflater mInflater = null;

    public IconCreditListViewAdapter(Context context, ArrayList<IconCredit> iconCreditList) {
        super(context, R.layout.icon_credit, iconCreditList);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.icon_credit, null);
            holder = new ViewHolder(convertView, position);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            holder.mPosition = position;
        }

        // Get IconCredit
        final IconCredit iconCredit = getItem(holder.mPosition);

        holder.mTvIconDesc.setText(iconCredit.getIconDesc());
        holder.mTvIconDesc.setMovementMethod(LinkMovementMethod.getInstance());
        holder.mIvIcon.setImageDrawable(iconCredit.getIconDrawable());

        return convertView;
    }


    // ViewHolder
    private class ViewHolder{
        public int mPosition;
        public ImageView mIvIcon;
        public TextView mTvIconDesc;

        public ViewHolder(View row, int position) {
            mPosition = position;
            mIvIcon = (ImageView) row.findViewById(R.id.iv_icon);
            mTvIconDesc = (TextView) row.findViewById(R.id.tv_icon_desc);
        }
    }
}