package com.bg.library.DataCenter;

import com.bg.library.Base.Objects.JSON.JData;
import com.bg.library.Base.Objects.JSON.JError;
import com.bg.library.Base.Objects.JSON.JSON;

import org.json.JSONObject;

/**
 * Created by BinGe on 2017/8/31.
 * 此类为数据中心所有数据的基本类型
 */

public abstract class AData extends JData{

    public AData() {
        super();
    }

    public AData(String jsonString) {
        super(jsonString);
    }

    public AData(JSONObject jsonObject) {
        super(jsonObject);
    }

    public AData(JSON json) {
        super(json);
    }

    /**
     * 判断数据是否正常
     * @return
     */
    public abstract boolean isDataNormal();

    /**
     * 数据初始化完成后会调用这个方法，供子类做一些初始化工作
     */
    public abstract void onData();

    @Override
    public final void onJDataRefresh() {
        onData();
    }
}
