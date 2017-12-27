package com.bg.library.DataCenter;



/**
 * Created by BinGe on 2017/9/12.
 * 此接口为Operation的处理器，实现此接可有处理Operation的能力
 */

public interface IPerformer<T> {

    /**
     * 对操作进行处理后，返回处理后的数据
     * @param operation 操作的名称
     * @param params    操作的参数
     * @return  返回操作的结果
     */
    Data performOperation(String operation, T params, ICallback callback);

    /**
     * 返回此操作是否为异步返回到数据中心，数据中心本身是子线程的，
     * 如果操作时的方法内容也用异步回调，则以下方法需求返回true，
     * 并在异步中处理回调
     * YES:异步
     * NO:同步
     */
    boolean isAsynchronous();


}
