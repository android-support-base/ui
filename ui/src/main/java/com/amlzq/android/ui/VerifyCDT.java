package com.amlzq.android.ui;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by amlzq on 2017/7/24.
 * millisInFuture
 * 获取验证码倒计时
 */
public class VerifyCDT extends CountDownTimer {

    private Context mContext;
    private EditText mETVoucher, mETVerifyCode;
    private Button mBtnAskCode;
    private int[] mColorResIds; // 正常颜色，按钮置灰

    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */
    public VerifyCDT(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    public void initView(Context context, EditText etVoucher, EditText etVerifyCode, Button btnAskCode) {
        mContext = context;
        mETVoucher = etVoucher;
        mETVerifyCode = etVerifyCode;
        mBtnAskCode = btnAskCode;
    }

    public void startCDT() {
        this.start();

        if (mETVoucher != null) mETVoucher.setEnabled(false);

        mBtnAskCode.setEnabled(false);
        mBtnAskCode.setTextColor(ContextCompat.getColor(mContext, mColorResIds[1]));
    }

    public void stopCDT() {
        this.cancel();

        mETVerifyCode.setText("");

        if (mETVoucher != null) mETVoucher.setEnabled(true);

        mBtnAskCode.setEnabled(true);
        mBtnAskCode.setText(mContext.getResources().getString(R.string.ask_for_verify_code));
        mBtnAskCode.setTextColor(ContextCompat.getColor(mContext, mColorResIds[0]));
    }

    @Override
    public void onTick(long millisUntilFinished) {
        mBtnAskCode.setText((millisUntilFinished / 1000) + "s");
    }

    /**
     * 倒计时结束的回调
     */
    @Override
    public void onFinish() {
        // mETVerifyCode.setText("");

        if (mETVoucher != null) mETVoucher.setEnabled(true);

        mBtnAskCode.setEnabled(true);
        mBtnAskCode.setText(mContext.getString(R.string.ask_for_verify_code));
        mBtnAskCode.setTextColor(ContextCompat.getColor(mContext, mColorResIds[0]));
    }

    public int[] getColorResIds() {
        return mColorResIds;
    }

    public void setColorResIds(int[] mColorResIds) {
        this.mColorResIds = mColorResIds;
    }

}