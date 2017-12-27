package com.bg.library.Utils.Localize.DB;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by BinGe on 2017/12/27.
 * 用于数据库的缓存，主要目的是为了不需要每一次get操作都去操作本地数据库，直接从缓存中获取
 */

public class DBCache {

    private Map<String, Table> cache;

    public DBCache() {
        cache = new HashMap<>();
    }

    public Table getTable(String table) {
        if (TextUtils.isEmpty(table)) {
            throw new RuntimeException("表名不能为空");
        }
        Table tableMap = cache.get(table);
        if (tableMap == null) {
            tableMap = new Table(table);
            cache.put(table, tableMap);
        }
        return tableMap;
    }

    class Table {
        private String name;
        private Map<String, String> data;

        private Table(String name) {
            this.name = name;
            data = new LinkedHashMap<>();
        }

        public void set(String key, String value) {
            data.put(key, value);
        }

        public String get(String key) {
            return data.get(key);
        }

        public Map<String, String> getAll() {
            return data;
        }

    }


}
