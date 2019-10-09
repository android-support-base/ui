package com.amlzq.asb;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.amlzq.android.util.ActivityUtil;

public class MainActivity extends MyBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // 用来计算返回键的点击间隔时间
    private long mExitClickTime = 0;

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - mExitClickTime) > 2000) {
            // 弹出提示，可以有多种方式
            showToastShort(R.string.press_again_to_exit_the_program);
            mExitClickTime = System.currentTimeMillis();
        } else {
            super.onBackPressed(); // finish this activity
            ActivityUtil.removeAll(); // finish all activity
            // No need to kill the application process
        }
    }

    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        super.onTitleChanged(title, color);
    }

    // 演示输入框随软键盘上移
    public void onSignIn(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void onSystemUI(View view) {
        startActivity(new Intent(this, SystemUIActivity.class));
    }
}
