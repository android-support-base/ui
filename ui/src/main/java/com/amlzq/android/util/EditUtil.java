package com.amlzq.android.util;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by amlzq on 2018/11/29.
 * <p>
 * 图片工具，解决兼容性问题
 */
public class EditUtil {

    /**
     * @hide
     */
    EditUtil() {
    }

    public static void requestFocus(final EditText et) {
        et.setFocusable(true);
        et.requestFocus();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            public void run() {
                InputMethodManager inputManager = (InputMethodManager) et.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(et, 0);
            }
        }, 998);
    }

}
