package com.bg.library.DataCenter;

import com.bg.library.Base.Objects.JSON.JError;
import com.bg.library.Base.Objects.JSON.JSON;

import org.json.JSONObject;

/**
 * Created by BinGe on 2017/8/31.
 * 此类为数据中心所有数据的基本类型
 */

public class Data extends JSON {

    private static final String NULL_VALUE = "空数据的HTData";

    /**
     * 错误信息
     */
    public JError error;

    public Data() {
        this(new JError(NULL_VALUE));
    }

    public Data(JError error) {
        this.error = error;
    }

    public Data(String jsonString) {
        setData(jsonString);
    }

    public Data(JSONObject jsonObject) {
        setData(jsonObject);
    }

    public Data(JSON object) {
        setData(object);
    }

    public void setData(JSONObject jsonObject) {
        setData(new JSON(jsonObject));
    }

    public void setData(String jsonString) {
        setData(new JSON(jsonString));
    }

    public void setData(JSON jsonObject) {
        //后台的原始结构
        if (jsonObject == null) {
            setJSONString("{}");
            error = new JError();
            error.errorInfo = "null object error!";
        }
        else if ((jsonObject.isContainsKey("status") && jsonObject.isContainsKey("message"))
                ||(jsonObject.isContainsKey("status") && jsonObject.isContainsKey("data"))) {
            String status = jsonObject.stringByKey("status");
            if (status.equals("success")) {
                JSONObject json = jsonObject.JSONObjectByKey("data");
                jsonObject = new JSON(json);
                setJSONObject(jsonObject.getJSONObject());
                error = null;
            } else {
                error = new JError();
                error.errorCode = jsonObject.integerByKey("errorLevel", JError.DEFAULT_ERROR_CODE);
                error.errorInfo = jsonObject.stringByKey("message");
                setJSONObject(jsonObject.getJSONObject());
            }
        }
        //普通的json数据
        else {
            setJSONObject(jsonObject.getJSONObject());
            error = null;
        }
        onData(this);
    }

    public boolean isDataNormal() {
        return error == null;
    }

    /**
     * 数据初始化完成后会调用这个方法，供子类做一些初始化工作
     * @param jsonData
     */
    public void onData(JSON jsonData) {

    }
}
