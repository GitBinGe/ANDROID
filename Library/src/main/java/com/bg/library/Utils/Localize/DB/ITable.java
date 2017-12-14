package com.bg.library.Utils.Localize.DB;

import android.content.ContentValues;

import java.util.Map;

/**
 * Created by BinGe on 2017/12/14.
 */

public interface ITable {

    /**
     * 获取表结构
     *
     * @return
     */
    public Map<String, String> getTableStructure();

    /**
     * 从数据库获取到数据后转成map数据，可以实现这个方法把map数据获获取出来
     *
     * @param data
     */
    public void toObject(Map<String, Object> data);

    /**
     * 用于插入数据
     *
     * @return
     */
    public ContentValues toContentValues();

}
