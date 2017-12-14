package com.bg.library.Utils.Localize.DB;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bg.library.Base.os.SystemInfo;
import com.bg.library.Utils.Log.LogUtils;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by BinGe on 2017/12/13.
 */

public class DBHelper {

    private static SQLiteDatabase db;
    private String tableName;
    private Class<? extends ITable> tableClass;

    DBHelper(String dbName, Class<? extends ITable> table) {
        tableClass = table;
        tableName = table.getName().replaceAll("\\.", "_");
        if (db == null) {
            File file = new File("/data/data/" + SystemInfo.PackageName + "/databases/");
            if (!file.exists()) {
                file.mkdirs();
            }
            db = SQLiteDatabase.openOrCreateDatabase("/data/data/" + SystemInfo.PackageName + "/databases/" + dbName + ".db", null);
        }

        try {
            ITable obj = table.newInstance();
            if (obj.getTableStructure() == null || obj.getTableStructure().size() == 0) {
                throw new RuntimeException("DB:" + table + "表的列数据不能为空, getTableStructure方法不能返回空数据");
            }

            StringBuffer sb = new StringBuffer("create table if not exists " + tableName + "(_id integer primary key autoincrement");
            for (Map.Entry<String, String> entry : obj.getTableStructure().entrySet()) {
                sb.append(", " + entry.getKey() + " " + entry.getValue());
            }
            sb.append(");");
            db.execSQL(sb.toString());
        } catch (InstantiationException e) {
            throw new RuntimeException("DB:" + table + "ITable了类必须复写默认（无参数）构造方法");
        } catch (Exception e) {
            throw new RuntimeException("DB:" + table + "创建表出错：" + e.getMessage());
        }

    }

    public long insert(ITable row) {
        ContentValues cv = row.toContentValues();
        return db.insert(tableName, null, cv);
    }

    public int delete(String key, Object value) {
        return db.delete(tableName, key + "=?", new String[]{value.toString()});
    }

    public List<ITable> selectAll() {
        List<ITable> objs = new ArrayList<>();
        Cursor c = db.query(tableName, null, null, null, null, null, null);
        if (c != null) {
            Map<String, Object> map = new HashMap<>();
            if (c.moveToFirst()) {
                do {
                    int col = c.getColumnCount();
                    for (int i = 0; i < col; i++) {
                        String name = c.getColumnName(i);
                        switch (c.getType(i)) {
                            case Cursor.FIELD_TYPE_NULL:
                                map.put(name, c.getString(i));
                                break;
                            case Cursor.FIELD_TYPE_INTEGER:
                                map.put(name, c.getInt(i));
                                break;
                            case Cursor.FIELD_TYPE_FLOAT:
                                map.put(name, c.getFloat(i));
                                break;
                            case Cursor.FIELD_TYPE_STRING:
                                map.put(name, c.getString(i));
                                break;
                            case Cursor.FIELD_TYPE_BLOB:
                                map.put(name, c.getBlob(i));
                                break;
                        }
                    }
                    try {
                        ITable obj = tableClass.newInstance();
                        obj.toObject(map);
                        objs.add(obj);
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                } while (c.moveToNext());
            }
            c.close();
        }
        return objs;
    }

}
