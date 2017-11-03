package com.bg.lib;

import android.app.Application;

import com.bg.library.Library;
import com.bg.library.Utils.Log.LogUtils;

/**
 * Created by BinGe on 2017/10/25.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Library.initialize(this);
        LogUtils.setLogSwitch(false);
        LogUtils.d("test log1");
        LogUtils.setLogSwitch(true);
        LogUtils.d("test log2");
    }
}
