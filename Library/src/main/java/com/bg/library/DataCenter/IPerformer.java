package com.bg.library.DataCenter;



/**
 * Created by BinGe on 2017/9/12.
 * 此接口为Operation的处理器，实现此接可有处理Operation的能力
 */

public interface IPerformer<T> {

    /**
     * 对操作进行处理后，返回处理后的数据
     * @param operation
     * @param params
     * @return
     */
    Data performOperation(String operation, T params, ICallback callback);

    /**
     * 返回此操作是否为异步返回到数据中心
     * YES:异步
     * NO:同步
     */
    boolean isAsynchronous();


}
