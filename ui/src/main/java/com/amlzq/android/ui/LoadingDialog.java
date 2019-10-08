package com.amlzq.android.ui;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;

import com.amlzq.android.util.DisplayUtil;

/**
 * Created by amlzq on 2018/10/3.
 * <p>
 * 定制一款载入中对话框
 */
public class LoadingDialog extends Dialog {
    public static final String TAG = "LoadingDialog";

    private Context mContext;
    private TextView mMessageView;

    public LoadingDialog(Context context) {
        super(context, R.style.Dialog_Loading);
        this.mContext = context;

        setCancelable(false);
        setCanceledOnTouchOutside(false);

        setContentView(R.layout.dialog_loading);
        this.mMessageView = ((TextView) findViewById(R.id.tv_message));
        setSize(126, 126);
    }

    public void setMessage(CharSequence msg, int color) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        this.mMessageView.setText(msg);
        this.mMessageView.setTextColor(color);
    }

    public void setMessage(CharSequence msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        this.mMessageView.setText(msg);
    }

    private void setSize(int width, int height) {
        if (null != getWindow()) {
            LayoutParams params = getWindow().getAttributes();
            params.width = DisplayUtil.getInt(this.mContext, width);
            params.height = DisplayUtil.getInt(this.mContext, height);
            getWindow().setAttributes(params);
        }
    }

}