package com.bg.lib.RecycleViewDemo;

import android.content.ContentValues;

import com.bg.library.Utils.Localize.DB.ITable;

import junit.framework.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by BinGe on 2017/12/14.
 */

public class TestTable implements ITable {

    private String name;
    private int age;

    public TestTable() {

    }

    public TestTable(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public Map<String, String> getTableStructure() {
        return new HashMap<String, String>(){{
            put("t_name", "text");
            put("t_age", "integer");
        }};
    }

    @Override
    public void toObject(Map<String, Object> data) {
        name = (String) data.get("t_name");
        age = (int)data.get("t_age");
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put("t_name", name);
        values.put("t_age", age);
        return values;
    }
}
