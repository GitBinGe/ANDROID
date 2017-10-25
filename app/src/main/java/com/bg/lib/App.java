package com.bg.lib;

import android.app.Application;

import com.bg.library.Library;

/**
 * Created by BinGe on 2017/10/25.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Library.initialize(this);
    }
}
