package com.bg.lib.DataCenterTest;


import com.bg.library.Base.Objects.JSON.JSON;
import com.bg.library.DataCenter.AData;

import org.json.JSONObject;

/**
 * Created by BinGe on 2017/8/31.
 * 此类为数据中心所有数据的基本类型
 */

public class HTData extends AData {

    public HTData() {
        super();
    }

    public HTData(String jsonString) {
        super(jsonString);
    }

    public HTData(JSONObject jsonObject) {
        super(jsonObject);
    }

    public HTData(JSON json) {
        super(json);
    }

    @Override
    public boolean isDataNormal() {
        return false;
    }

    @Override
    public void onDataRefresh() {

    }

}
