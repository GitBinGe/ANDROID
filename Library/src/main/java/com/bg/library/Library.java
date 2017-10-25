package com.bg.library;

import android.app.Application;
import android.content.Context;

import com.bg.library.Base.os.SystemInfo;
import com.bg.library.Utils.Localize.Saver;

/**
 * Created by BinGe on 2017/10/25.
 * 整个库的初始化入口
 */

public class Library {

    private static Application application;

    public static void initialize(Application app) {
        application = app;
        SystemInfo.initialize(app);
    }

    public static Application getApplication() {
        return application;
    }

}
