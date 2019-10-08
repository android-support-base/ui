package com.amlzq.android.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amlzq.android.view.ListItemType;

/**
 * 空数据项视图
 */
public class EmptyViewHolder extends RecyclerView.ViewHolder {
    public final View mView;
    public final ImageView mIcon;
    public final TextView mMessage;

    public EmptyViewHolder(View view) {
        super(view);
        mView = view;
        mIcon = mView.findViewById(R.id.iv_content_icon);
        mMessage = mView.findViewById(R.id.tv_content_message);
    }

    public void show(ListItemType item) {
        if (mMessage != null) mMessage.setText(item.getItemTypeName());
        if (mIcon != null) mIcon.setImageResource(item.getItemTypeIconResId());
    }

}