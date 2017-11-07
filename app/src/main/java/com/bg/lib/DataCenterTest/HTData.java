package com.bg.lib.DataCenterTest;


import com.bg.library.DataCenter.Data;
import com.bg.library.Utils.Log.LogUtils;

/**
 * Created by BinGe on 2017/8/31.
 * 此类为数据中心所有数据的基本类型
 */

public class HTData extends Data {


    @Override
    protected void onData() {
        LogUtils.d(this.getSource());
    }

    @Override
    public boolean isDataNormal() {
        return false;
    }
}
