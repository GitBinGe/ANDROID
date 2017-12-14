package com.bg.library.Utils.Localize.DB;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by BinGe on 2017/12/13.
 */

public class DB {

    private static Map<String, DBTable> dbs;

    /**
     * 打开数据库，并找到指定的表
     * @param db 数据库名字
     * @return
     */
    public static DBTable open(String db) {
        if (dbs == null) {
            dbs = new HashMap<>();
        }
        DBTable table = dbs.get(db);
        if (table == null) {
            table = new DBTable(db);
            dbs.put(db, table);
        }
        return table;
    }

}
