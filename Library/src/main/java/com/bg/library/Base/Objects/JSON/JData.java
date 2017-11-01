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
    private JError jDataError;

    private String cookies;

    public JData() {
        super();
    }

    public JData(String jsonString) {
        super(jsonString);
    }

    public JData(JSONObject jsonObject) {
        super(jsonObject);
    }

    public JData(JSON json) {
        super(json);
    }

    @Override
    public final void onJSONRefresh() {
        if (isJSONEmpty()) {
            jDataError = new JError(JError.DEFAULT_ERROR_CODE, "空JSON数据", null);
        } else {
            jDataError = null;
        }
        onJDataRefresh();
    }

    public void onJDataRefresh() {

    }

    public void setJDataError(JError error) {
        this.jDataError = error;
    }

    public boolean isJDataOK() {
        return this.jDataError == null && isJSONEmpty();
    }

    public JError getJDataError() {
        return jDataError;
    }

    public void setCookies(String cookies) {
        this.cookies = cookies;
    }

    public String getCookies() {
        return cookies;
    }

}
