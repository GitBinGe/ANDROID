package com.bg.library.Base.os;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by BinGe on 2017/9/19.
 * 屏幕的相关信息
 */

public class Screen {

    /**
     * 屏幕宽
     */
    public int width  = 720;

    /**
     * 屏幕高
     */
    public int height = 1280;

    /**
     * 屏幕高
     */
    public int statusBarHeight = 40;

    /**
     * DP与PX的比例
     */
    public float scale = 2;


    Screen(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        scale = dm.density;
        width = dm.widthPixels;
        height = dm.heightPixels;
        statusBarHeight = getStatusBarHeight(context);
    }

    public int dip2px(float dpValue) {
        return (int) (dpValue * scale + 0.5f);
    }

    private int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier(
                "status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

}
