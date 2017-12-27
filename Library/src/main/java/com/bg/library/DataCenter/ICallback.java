package com.bg.library.DataCenter;

/**
 * Created by BinGe on 2017/11/1.
 * 调用数据中心DataCenter处理任务时如果需要数据回调，则实现这个接口
 */

public interface ICallback {

    /**
     * @param operation 操作的名称
     * @param data  操作后返回的数据
     */
    void onCallback(String operation, Data data);

}
