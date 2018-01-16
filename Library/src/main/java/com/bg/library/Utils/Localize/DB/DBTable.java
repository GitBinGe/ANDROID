package com.bg.library.Utils.Localize.DB;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.bg.library.Base.os.SystemInfo;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by BinGe on 2017/12/15.
 */

public class DBTable {

    private static ExecutorService singleThread;

    private static Map<String, SQLiteDatabase> dbs;

    private SQLiteDatabase db;
    private String table;
    private Map<String, String> cache;
    private Map<String, String> dbCache;

    DBTable(String db, String table) {
        if (singleThread == null) {
            singleThread = Executors.newSingleThreadExecutor();
        }

        this.table = table;

        if (dbs == null) {
            dbs = new HashMap<>();
        }

        SQLiteDatabase database = dbs.get(db);
        if (database == null) {
            String path = "/data/data/" + SystemInfo.PackageName + "/databases/";
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
            database = SQLiteDatabase.openOrCreateDatabase(path + db + ".db", null);
            dbs.put(db, database);
        }

        StringBuffer sb = new StringBuffer("create table if not exists " + table
                + "(_id integer primary key autoincrement,"
                + "key text,"
                + "value text);"
        );
        database.execSQL(sb.toString());
        this.db = database;

        Map<String, String> history = getAllFromDB();
        this.cache = new HashMap<>(history);
        this.dbCache = new HashMap<>(history);

    }

    public boolean set(final String key, final String value) {
        if (key == null) {
            return false;
        }

        this.cache.put(key, value);
        singleThread.execute(new Runnable() {
            @Override
            public void run() {
                ContentValues cv = new ContentValues();
                cv.put("key", key);
                cv.put("value", value);
                if (dbCache.get(key) == null) {
                    if (db.insert(table, null, cv) > 0) {
                        dbCache.put(key, value);
                    } else {
                        cache.remove(key);
                    }
                } else {
                    if (db.update(table, cv, "key=?", new String[]{key}) > 0) {
                        dbCache.put(key, value);
                    } else {
                        cache.put(key, dbCache.get(key));
                    }
                }
            }
        });
        return true;
    }

    public String get(String key) {
        return this.cache.get(key);
    }

    public Map<String, String> getAll() {
        return new LinkedHashMap<>(this.cache);
    }

    public boolean remove(final String key) {
        if (TextUtils.isEmpty(key)) {
            return false;
        }
        singleThread.execute(new Runnable() {
            @Override
            public void run() {
                db.delete(table, "key=?", new String[]{key});
            }
        });
        return this.cache.remove(key) != null;
    }

    private Map<String, String> getAllFromDB() {
        Map<String, String> all = new LinkedHashMap<>();
        Cursor c = db.query(table, null, null, null, null, null, null);
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    String key = c.getString(c.getColumnIndex("key"));
                    String value = c.getString(c.getColumnIndex("value"));
                    if (!TextUtils.isEmpty(key)) {
                        all.put(key, value);
                    }
                } while (c.moveToNext());
            }
            c.close();
        }
        return all;
    }


}
