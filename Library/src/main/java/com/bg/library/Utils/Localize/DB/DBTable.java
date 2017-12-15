package com.bg.library.Utils.Localize.DB;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bg.library.Base.os.SystemInfo;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by BinGe on 2017/12/15.
 */

public class DBTable {

    private static Map<String, SQLiteDatabase> dbs;

    private SQLiteDatabase db;
    private String table;

    DBTable(String db, String table) {
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
    }

    public boolean set(String key, String value) {
        ContentValues cv = new ContentValues();
        cv.put(key, value);
        if (get(key) == null) {
            return db.insert(table, null, cv) > 0;
        } else {
            return db.update(table, cv, "key=?", new String[]{key}) > 0;
        }
    }

    public String get(String key) {
        String value = null;
        Cursor c = db.query(table, null, "key=?", new String[]{key}, null, null, null);
        if (c != null) {
            if (c.moveToFirst()) {
                value = c.getString(1);
            }
            c.close();
        }
        return value;
    }

    public List<String> getAll() {
        List<String> list = new ArrayList<>();
        Cursor c = db.query(table, null, null, null, null, null, null);
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    String value = c.getString(1);
                    list.add(value);
                } while (c.moveToNext());
            }
            c.close();
        }
        return list;
    }

    public boolean remove(String key) {
        return db.delete(table, "key=?", new String[]{key}) > 0;
    }


}
