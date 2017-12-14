package com.bg.lib;

import android.app.Application;

import com.bg.lib.RecycleViewDemo.TestTable;
import com.bg.library.Library;
import com.bg.library.Utils.Localize.DB.DB;
import com.bg.library.Utils.Localize.DB.ITable;
import com.bg.library.Utils.Log.LogUtils;

import java.util.List;

/**
 * Created by BinGe on 2017/10/25.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Library.initialize(this);

        //创建数据库和表, 可以在app启动的时候执行，如果数据库中已存在表则不会重复创建
        TestTable tt = new TestTable("名字", 30);
        long id = DB.open("test").table(TestTable.class).insert(tt);
        List<ITable> list = DB.open("test").table(TestTable.class).selectAll();
//        ContentValues values = new ContentValues();
//        values.put("uid", "123123123");
//        values.put("userdata", "{json数据}");
//        long count = DB.open("test").getTable("table1").insert(values);
//        int row = DB.open("test").getTable("table1").delete("uid", "123123123");
        LogUtils.d("");

    }
}
