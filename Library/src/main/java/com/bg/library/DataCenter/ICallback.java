package com.bg.library.DataCenter;

/**
 * Created by BinGe on 2017/11/1.
 * 调用数据中心DataCenter处理任务时如果需要数据回调，则实现这个接口
 */

public interface ICallback {

    void onCallback(String operation, AData data);

}
