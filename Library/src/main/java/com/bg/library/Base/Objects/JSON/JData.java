package com.bg.library.Base.Objects.JSON;

import org.json.JSONObject;

/**
 * Created by BinGe on 2017/10/25.
 * 封闭HTTP工具返回的数据结构，包含了cookies和返回的错误信息
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

    public JData(JError responseError) {
        super();
        setResponseError(responseError);
    }

    public void setResponseError(JError error) {
        this.responseError = error;
    }

    public boolean isResponseOK() {
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
