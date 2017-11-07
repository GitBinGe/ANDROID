package com.bg.lib.DataCenterTest;


import com.bg.library.DataCenter.DataHandler;
import com.bg.library.DataCenter.ICallback;

import java.util.ArrayList;


/**
 * Created by BinGe on 2017/8/31.
 * 数据中心，app中所有的数据都通过DataCenter请求或获取
 */

public class DataCenter extends com.bg.library.DataCenter.DataCenter {

    /**
     * 获取数据中心单例
     */
    public static DataCenter get() {
        return getInstance();
    }

    /**
     * 数据中心对外的数据接口，通过传入type返回相应的数据
     *
     * @param type {@link DataType}
     */
    public HTData getData(DataType type) {
        return (HTData) getDataByHandler(type.getHandlerClass());
    }

    /**
     * 执行一次操作
     *
     * @param params    请求对应的参数
     */
    public void perform(String operation, Object params, ICallback callback) {
        performOperation(operation, params, callback);
    }

    /**
     * 数据中心单例变量
     */
    private static DataCenter sDataCenter;

    /**
     * 获取数据中心单例
     *
     * @return
     */
    private static DataCenter getInstance() {
        if (sDataCenter == null) {
            sDataCenter = new DataCenter();
        }
        return sDataCenter;
    }


    @Override
    public ArrayList<Class<? extends DataHandler>> getOptionHandlers() {
        return new ArrayList<Class<? extends DataHandler>>() {
            {
                /**
                 * 公共处理器
                 */
                add(GlobalHandler.class);
            }
        };
    }
}
