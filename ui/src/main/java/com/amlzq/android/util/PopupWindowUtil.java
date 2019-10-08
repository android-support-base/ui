package com.amlzq.android.util;

import android.app.Activity;
import android.view.WindowManager;
import android.widget.PopupWindow;

public class PopupWindowUtil {

    /**
     * 让popupwindow以外区域阴影显示
     * https://www.cnblogs.com/epmouse/p/5422229.html
     *
     * @param popupWindow
     */
    private void popOutShadow(PopupWindow popupWindow, Activity activity) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = 0.7f;//设置阴影透明度
        activity.getWindow().setAttributes(lp);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
                lp.alpha = 1f;
                activity.getWindow().setAttributes(lp);
//                roteAnimation();
            }
        });
    }

}
