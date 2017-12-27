package com.bg.library.Utils.Localize.DB;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by BinGe on 2017/12/13.
 * 数据库工具类
 */

public class DB {

    private static DB db;

    /**
     * 默认数据库default.db
     * @return
     */
    public static DB get() {
        if (db == null) {
            db = new DB();
        }
        return db;
    }

    /**
     * 数据库
     * @param db 数据库字句
     * @return
     */
    public static DB get(String db) {
        DB d = get();
        d.open(db);
        return d;
    }

    private Map<String, DBTable> tables;

    private String dbName;

    private DB() {
        tables = new HashMap<>();
    }

    /**
     * 打开数据库，并找到指定的表
     *
     * @param db 数据库名字
     * @return
     */
    private DB open(String db) {
        dbName = db;
        return this;
    }

    public DBTable table(String table) {
        String db = TextUtils.isEmpty(dbName) ? "default" : dbName;
        String key = db + "_" + table;
        DBTable t = tables.get(key);
        if (t == null) {
            t = new DBTable(db, table);
            tables.put(key, t);
        }
        return t;
    }

}
