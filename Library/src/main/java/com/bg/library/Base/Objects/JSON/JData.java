package com.bg.library.Base.Objects.JSON;

import org.json.JSONObject;

/**
 * Created by BinGe on 2017/10/25.
 */

public class JData extends JSON {

    /**
     * 主要是记录非服务器错误信息的日志，如网络出错，IO错误等信息
     */
    private JError responseError;

    private String cookies;

    public JData() {
        super();
    }

    public JData(String jsonString) {
        super(jsonString);
    }

    public JData(JSONObject object) {
        super(object);
    }

    public JData(JError error) {
        super();
        setError(error);
    }

    public void setError(JError error) {
        this.responseError = error;
    }

    public boolean isDataNormal() {
        return this.responseError == null;
    }

    public JError getResponseError() {
        return responseError;
    }

    public void setCookies(String cookies) {
        this.cookies = cookies;
    }

    public String getCookies() {
        return cookies;
    }

}
