package com.bg.lib.DataCenterTest;


import com.bg.library.DataCenter.DataHandler;

/**
 * Created by BinGe on 2017/9/12.
 * 数据中心获取相应数据
 */

public enum DataType {

    ;

    private Class<? extends DataHandler> handlerClass;

    DataType(Class<? extends DataHandler> cls) {
        handlerClass = cls;
    }

    public Class<? extends DataHandler> getHandlerClass() {
        return handlerClass;
    }

}

