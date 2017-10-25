package com.bg.library.EventCenter;

import android.content.Intent;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by BinGe on 2016/12/9.
 * 事件总线的数据结构
 */

public class EventData {

    private final static String DEFAULT_KEY = "Event.DEFAULT_KEY";
    private Map<String, Object> dataMap = new HashMap<>();

    public EventData() {

    }

    public EventData(Object data) {
        this(DEFAULT_KEY, data);
    }

    public EventData(String key, Object data) {
        put(key, data);
    }

    public EventData put(Object data) {
        return put(DEFAULT_KEY, data);
    }

    public EventData put(String key, Object data) {
        if (data != null) {
            dataMap.put(key, data);
        }
        return this;
    }

    public EventData put(String key, EventData data) {
        if (data != null) {
            dataMap.put(key, data);
        }
        return this;
    }

    public Object get() {
        return get(DEFAULT_KEY);
    }

    public Object get(String key) {
        return dataMap.get(key);
    }

    public int getIntValue() {
        return getIntValue(DEFAULT_KEY);
    }

    public int getIntValue(String key) {
        try {
            return Integer.valueOf(dataMap.get(key).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public float getFloatValue() {
        return getFloatValue(DEFAULT_KEY);
    }

    public float getFloatValue(String key) {
        try {
            return Float.valueOf(dataMap.get(key).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public long getLongValue() {
        return getLongValue(DEFAULT_KEY);
    }

    public long getLongValue(String key) {
        try {
            return Long.valueOf(dataMap.get(key).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    public double getDoubleValue() {
        return getDoubleValue(DEFAULT_KEY);
    }

    public double getDoubleValue(String key) {
        try {
            return Double.valueOf(dataMap.get(key).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public String getStringValue() {
        return getStringValue(DEFAULT_KEY);
    }

    public String getStringValue(String key) {
        try {
            return dataMap.get(key).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean getBooleanValue() {
        return getBooleanValue(DEFAULT_KEY);
    }

    public boolean getBooleanValue(String key) {
        try {
            return (Boolean) dataMap.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Intent getIntentValue() {
        return getIntentValue(DEFAULT_KEY);
    }

    public Intent getIntentValue(String key) {
        try {
            return (Intent) dataMap.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Intent();
    }


}
