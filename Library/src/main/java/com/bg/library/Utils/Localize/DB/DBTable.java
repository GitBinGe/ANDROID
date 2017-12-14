package com.bg.library.Utils.Localize.DB;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by BinGe on 2017/12/13.
 */

public class DBTable {

    private String dbName;
    private static Map<String, DBHelper> tables;

    DBTable(String dbName) {
        this.dbName = dbName;
        tables = new HashMap<>();
    }

    /**
     * 创建一个表
     * @param table 表
     * @return
     */
    public DBHelper table(Class<? extends ITable> table) {
        if (table == null) {
            throw new RuntimeException("DB:" + table + "表不能为空");
        }

        DBHelper helper = tables.get(table.getName());
        if (helper == null) {
            helper = new DBHelper(this.dbName, table);
            tables.put(table.getName(), helper);
        }
        return helper;
    }

}
