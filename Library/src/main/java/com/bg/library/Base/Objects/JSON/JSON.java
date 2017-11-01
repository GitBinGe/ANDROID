package com.bg.library.Base.Objects.JSON;

import com.bg.library.Base.Objects.BaseObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BinGe on 2017/8/31.
 * 用于封装json数据
 */

public class JSON extends BaseObject {

    /**
     * 原始数据
     */
    private JSONObject json;

    public JSON() {
        setJSONObject(null);
    }

    public JSON(String jsonString) {
        setJSONString(jsonString);
    }

    public JSON(JSONObject jsonObject) {
        setJSONObject(jsonObject);
    }

    public JSON(JSON json) {
        setJSONObject(json == null ? null : json.getJSONObject());
    }

    /**
     * 通过字符串类型的json数据初始化
     *
     * @param jsonString
     * @return
     */
    public final boolean setJSONString(String jsonString) {
        if (jsonString != null) {
            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                return setJSONObject(jsonObject);
            } catch (JSONException e) {
                setJSONObject(null);
            }
        }
        return false;
    }

    /**
     * 通过json数据初始化
     *
     * @param jsonObject
     * @return
     */
    public final boolean setJSONObject(JSONObject jsonObject) {
        this.json = jsonObject;
        onJSONRefresh();
        return this.json != null;
    }

    /**
     * json数据改变时调用
     */
    public void onJSONRefresh() {

    }


    public void put(String key, Object value) {
        if (json == null) {
            json = new JSONObject();
        }
        try {
            json.put(key, value);
            onJSONRefresh();
        } catch (JSONException e) {
        }
    }

    /**
     * 判断数据是否为空
     */
    public boolean isJSONEmpty() {
        return json == null || json.toString().equals("{}");
    }

    /**
     * 判断数据是否包含key对应的数据
     *
     * @param key
     * @return
     */
    public boolean isContainsKey(String key) {
        return json != null && json.has(key);
    }

    /**
     * 返回key对应的String类型值
     *
     * @param key
     * @return
     */
    public String stringByKey(String key) {
        return stringByKey(key, null);
    }

    public String stringByKey(String key, String defaultValue) {
        try {
            return this.json.getString(key);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 返回key对应的int类型值
     *
     * @param key
     * @return
     */
    public int integerByKey(String key) {
        return integerByKey(key, -1);
    }

    public int integerByKey(String key, int defaultValue) {
        try {
            return this.json.getInt(key);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 返回key对应的long类型值
     *
     * @param key
     * @return
     */
    public long longByKey(String key) {
        return longByKey(key, -1);
    }

    public long longByKey(String key, long defaultValue) {
        try {
            return this.json.getLong(key);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 返回key对应的boolean类型值
     *
     * @param key
     * @return
     */
    public boolean booleanByKey(String key) {
        return booleanByKey(key, false);
    }

    public boolean booleanByKey(String key, boolean defaultValue) {
        try {
            return this.json.getBoolean(key);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 返回key对应的JSONObject对像
     *
     * @param key
     * @return
     */
    public JSONObject JSONObjectByKey(String key) {
        try {
            return this.json.getJSONObject(key);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 返回key对应的JSONArray对像
     *
     * @param key
     * @return
     */
    public JSONArray JSONArrayByKey(String key) {
        try {
            return this.json.getJSONArray(key);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 返回key对应的字符串数组对像
     *
     * @param key
     * @return
     */
    public List<String> stringArrayByKey(String key) {
        List<String> list = new ArrayList<>();
        JSONArray arr;
        try {
            arr = this.json.getJSONArray(key);
            for (int i = 0; i < arr.length(); i++) {
                list.add(arr.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public JSONObject getJSONObject() {
        return json;
    }


    /**
     * 生成一个JSONObject数据
     * @param args key,value,key,value...
     * @return
     */
    public static JSON make(Object... args) {
        JSON json = new JSON();
        int length = args.length;
        try {
            for (int i = 0; i < length; ) {
                Object key = args[i];
                Object value = args[i + 1];
                json.put(key.toString(), value);
                i += 2;
            }
        } catch (Exception e) {
            throw new RuntimeException("JSON数据异常");
        }
        return json;
    }

    /**
     * JSONArray 转 ArrayList
     * @param arr
     * @return
     */
    public static ArrayList<JSON> JSONArray2ArrayList(JSONArray arr) {
        ArrayList<JSON> list = new ArrayList<>();
        try {
            for (int i = 0; i < arr.length(); i++) {
                JSONObject jsonObject = arr.getJSONObject(i);
                list.add(new JSON(jsonObject));
            }
        } catch (Exception e) {
        }
        return list;
    }

}
