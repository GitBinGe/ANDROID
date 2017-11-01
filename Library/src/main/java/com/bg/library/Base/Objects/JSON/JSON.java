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
        setJSON(json);
    }

    /**
     * 通过字符串类型的json数据初始化
     *
     * @param jsonString
     * @return
     */
    public final JSON setJSONString(String jsonString) {
        if (jsonString != null) {
            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                setJSONObject(jsonObject);
            } catch (JSONException e) {
                setJSONObject(null);
            }
        }
        return this;
    }

    /**
     * 通过JSON类型的数据初始化
     *
     * @param json
     * @return
     */
    public final JSON setJSON(JSON json) {
        try {
            setJSONObject(json.getSource());
        } catch (Exception e) {
        }
        return this;
    }

    /**
     * 通过json数据初始化
     *
     * @param jsonObject
     * @return
     */
    public final JSON setJSONObject(JSONObject jsonObject) {
        this.json = jsonObject;
        onJSONRefresh();
        return this;
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
    public String getString(String key) {
        return getString(key, null);
    }

    public String getString(String key, String defaultValue) {
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
    public int getInteger(String key) {
        return getInteger(key, -1);
    }

    public int getInteger(String key, int defaultValue) {
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
    public long getLong(String key) {
        return getLong(key, -1);
    }

    public long getLong(String key, long defaultValue) {
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
    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        try {
            return this.json.getBoolean(key);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 获取非空的JSON数据，
     *
     * @param key
     * @return
     * {@link ()} 方法判断是否原始数据为空
     */
    public JSON getJSON(String key) {
        try {
            JSONObject jsonObject = this.json.getJSONObject(key);
            if (jsonObject != null) {
                return new JSON(jsonObject);
            }
        } catch (Exception e) {
        }
        return new JSON();
    }

    /**
     * 获取列表数据,只获取数组中的json结构数据，string或int会过滤掉
     *
     * @param key
     * @return
     */
    public List<JSON> getJSONList(String key) {
        try {
            JSONArray array = this.json.getJSONArray(key);
            if (array != null) {
                List<JSON> jsonList = new ArrayList<>();
                for (int i = 0; i < array.length(); i++) {
                    try {
                        jsonList.add(new JSON(array.getJSONObject(i)));
                    } catch (Exception e) {
                    }
                }
                return jsonList;
            }
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 获取String列表数据,可以获取JSONArray的所有数据
     *
     * @param key
     * @return
     */
    public List<String> getStringList(String key) {
        try {
            JSONArray array = this.json.getJSONArray(key);
            if (array != null) {
                List<String> jsonList = new ArrayList<>();
                for (int i = 0; i < array.length(); i++) {
                    try {
                        jsonList.add(array.getString(i));
                    } catch (Exception e) {
                    }
                }
                return jsonList;
            }
        } catch (Exception e) {
        }
        return null;
    }


    /**
     * 返回key对应的JSONArray对像
     *
     * @param key
     * @return
     */
    public JSONArray getJSONArray(String key) {
        try {
            return this.json.getJSONArray(key);
        } catch (Exception e) {
            return null;
        }
    }

    public JSONObject getSource() {
        return json;
    }


    /**
     * 生成一个JSONObject数据
     *
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
     *
     * @param arr
     * @return
     */
    public static List<JSON> toList(JSONArray arr) {
        List<JSON> list = new ArrayList<>();
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
