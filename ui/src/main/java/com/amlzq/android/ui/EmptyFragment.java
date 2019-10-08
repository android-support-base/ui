package com.amlzq.android.ui;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.amlzq.android.app.BaseFragment;

/**
 * 空白片段
 */
public class EmptyFragment extends BaseFragment {
    public static final String TAG = "EmptyFragment";

    private TextView mMessage;
    public String message = "";

    public EmptyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment EmptyFragment.
     */
    public static EmptyFragment newInstance() {
        return new EmptyFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_empty;
    }

    @Override
    protected void afterCreate(Bundle bundle) {
        mMessage = findViewById(R.id.tv_content_message);
        int visibility = TextUtils.isEmpty(message) ? View.VISIBLE : View.GONE;
        mMessage.setVisibility(visibility);
        mMessage.setText(message);
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

}
