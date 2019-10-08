package com.amlzq.android.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.amlzq.android.app.BaseDialogFragment;
import com.amlzq.android.util.ViewUtil;

/**
 * Created by amlzq on 2018/10/3.
 * <p>
 * 定制一款载入中对话框片段
 */

public class LoadingDialogFragment extends BaseDialogFragment {
    public static final String TAG = "LoadingDialogFragment";

    private ProgressBar mProgressBar;
    private TextView mTVMessage;
    private String mMessage;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(360, 360);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE); // no title
        // getDialog().getWindow().getAttributes().windowAnimations = R.style.dialogAnim;
        return inflater.inflate(R.layout.dialog_loading, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mProgressBar = getView().findViewById(R.id.pb_loading);
        ViewUtil.setProgressBarColors(mProgressBar,
                getColor(R.color.dialogBackground), // bgColor blue
                getColor(R.color.colorAccent) // progressColor red
        );
        mTVMessage = getView().findViewById(R.id.tv_message);
        if (TextUtils.isEmpty(getMessage())) {
            mTVMessage.setVisibility(View.GONE);
        } else {
            mTVMessage.setVisibility(View.VISIBLE);
            mTVMessage.setText(getMessage());
        }
    }

    // =================================================
    // TODO: 封装的方法
    // =================================================

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        this.mMessage = message;
    }

    @Override
    public void onClick(View v) {

    }

}