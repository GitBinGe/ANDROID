package com.bg.lib.DataCenterTest;


import com.bg.library.DataCenter.ADataHandler;

/**
 * Created by BinGe on 2017/9/12.
 * 数据中心获取相应数据
 */

public enum DataType {

    ;

    private Class<? extends ADataHandler> handlerClass;

    DataType(Class<? extends ADataHandler> cls) {
        handlerClass = cls;
    }

    public Class<? extends ADataHandler> getHandlerClass() {
        return handlerClass;
    }

}

