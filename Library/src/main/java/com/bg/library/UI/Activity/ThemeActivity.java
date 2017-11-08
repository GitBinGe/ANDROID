package com.bg.library.UI.Activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.bg.library.Utils.UI.ActivityUtils;

/**
 * Created by BinGe on 2017/8/31.
 * 所有Activity的基类
 */

public class ThemeActivity extends PermissionsActivity {

    /**
     * 通知栏字体与icon的风格
     */
    public enum StatusBarContentStyle {
        Light,//亮色
        BLACK//深色
    }

    public static class Theme {
        /**
         * 通知栏的背景颜色
         */
        public int statusBarBackgroundColor = Color.WHITE;

        /**
         * 通知栏的字体颜色
         * 只支持两种
         */
        public StatusBarContentStyle statusBarContentStyle = StatusBarContentStyle.BLACK;

        public Theme(StatusBarContentStyle style) {
            statusBarContentStyle = style;
            statusBarBackgroundColor = style == StatusBarContentStyle.BLACK ? Color.WHITE : Color.BLACK;
        }

        public Theme(StatusBarContentStyle style, int color) {
            statusBarContentStyle = style;
            statusBarBackgroundColor = color;
        }

    }


    private Theme mTheme = new Theme(StatusBarContentStyle.BLACK);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isAndroidKitkatOrLollipop()) {
            translucentStatusBar();
        } else {
            applyTheme(getMyTheme());
        }
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        if (isAndroidKitkatOrLollipop()) {
            setFitsSystemWindows();
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        if (isAndroidKitkatOrLollipop()) {
            setFitsSystemWindows();
        }
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        if (isAndroidKitkatOrLollipop()) {
            setFitsSystemWindows();
        }
    }

    private void translucentStatusBar() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }

    private void setFitsSystemWindows() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //set child View not fill the system window
        ViewGroup mContentView = (ViewGroup) findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            ViewCompat.setFitsSystemWindows(mChildView, true);
        }
    }

    /**
     * 应用主题
     *
     * @param theme
     */
    private void applyTheme(Theme theme) {
        if (theme != null) {
            ActivityUtils.setWindowStatusBarColor(getWindow(), theme.statusBarBackgroundColor);
            ActivityUtils.setDarkStatusIcon(this, theme.statusBarContentStyle == StatusBarContentStyle.BLACK);
        } else {
            ActivityUtils.setWindowStatusBarColor(getWindow(), Color.WHITE);
            ActivityUtils.setDarkStatusIcon(this, true);
        }
    }

    /**
     * Activity中跟通知栏，title相关的主题信息
     */
    protected Theme getMyTheme() {
        return mTheme;
    }

    private boolean isAndroidKitkatOrLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.M;
    }

}
