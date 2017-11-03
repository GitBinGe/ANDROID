package com.bg.library.UI.Presenter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by BinGe on 2017/9/1.
 * 在页面比较复杂的情况下使用MVP模式，该类为P层
 */

public class Presenter {

    private Context context;
    private View rootView;

    public Presenter(View rootView) {
        this.rootView = rootView;
        if (rootView != null) {
            context = rootView.getContext();
        }
    }

    public Presenter(Context context) {
        this.context = context;
        if (context instanceof Activity) {
            Activity a = (Activity) context;
            ViewGroup content = (ViewGroup) a.findViewById(android.R.id.content);
            this.rootView = content.getChildAt(0);
            if (this.rootView == null) {
                throw new NullPointerException("请先调用 Activity 的 setContentView方法");
            }
        }
    }

    public Context getContext() {
        return context;
    }

    public View getRootView() {
        return rootView;
    }

    public View findViewById(int id) {
        return rootView.findViewById(id);
    }

    /**
     * 同activity的onStart()
     */
    public void onStart() {

    }

    /**
     * 同activity的onResume()
     */
    public void onResume() {

    }

    /**
     * 同activity的onPause()
     */
    public void onPause() {

    }

    /**
     * 同activity的onStop()
     */
    public void onStop() {

    }

    /**
     * 同activity的onDestroy()
     */
    public void onDestroy() {

    }


}
