package com.bg.lib;

import android.app.Application;

import com.bg.lib.DataCenterTest.HTData;
import com.bg.library.Library;
import com.bg.library.Utils.Http.Http;

/**
 * Created by BinGe on 2017/10/25.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Library.initialize(this);

        new Thread() {
            @Override
            public void run() {
                Http.ResponseData data = Http.post("", "");
                if (data.isResponseOK()) {

                } else {

                }

                HTData data1 = new HTData();
                data1.setData("{}");


            }
        }.start();

    }
}
