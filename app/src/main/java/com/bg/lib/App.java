package com.bg.lib;

import android.app.Application;

import com.bg.library.Library;
import com.bg.library.Utils.Localize.DB.DB;
import com.bg.library.Utils.Log.LogUtils;

import java.util.List;
import java.util.Map;


/**
 * Created by BinGe on 2017/10/25.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Library.initialize(this);

        DB.get().table("history").set("1", "1234567");
        DB.get().table("history").set("2", "1234567");
        DB.get().table("history").set("3", "1234567");
        DB.get().table("history").set("4", "1234567");

        DB.get().table("history").remove("4");

        String value1 = DB.get().table("history").get("1");
        String value2 = DB.get().table("history").get("2");
        String value3 = DB.get().table("history").get("3");
        String value4 = DB.get().table("history").get("4");

        Map<String, String> values = DB.get().table("history").getAll();
        LogUtils.d("values");

    }
}
