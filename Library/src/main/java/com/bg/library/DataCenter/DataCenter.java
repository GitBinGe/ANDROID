package com.bg.library.DataCenter;


import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created by BinGe on 2017/8/31.
 * 数据中心，app中所有的数据都通过DataCenter请求或获取
 * 前面的A表示这个类为抽象类
 */

public abstract class DataCenter {

    /**
     * 用于任务的主线程和子线程的分发工作
     */
    private Handler mHandler;

    private ExecutorService mThreadPool;

    /**
     * 用户操作处理器数组
     */
    private List<DataHandler> mOptionHandlers = new ArrayList<>();

    /**
     * 私有构造方法
     */
    protected DataCenter() {
        mHandler = new Handler(Looper.getMainLooper());
        mThreadPool = Executors.newFixedThreadPool(3);
        initDataHandlers(getOptionHandlers());
    }

    public void setThreadCount(int threadCount) {
        mThreadPool.shutdown();
        mThreadPool = Executors.newFixedThreadPool(threadCount);
    }

    /**
     * 返回需要初始化的Handler列表
     * @return
     */
    public abstract ArrayList<Class<? extends DataHandler>> getOptionHandlers();

    /**
     * 初始化处理器
     */
    private void initDataHandlers(List<Class<? extends DataHandler>> handlers) {
        for (Class<? extends DataHandler> cls : handlers) {
            try {
                DataHandler handler = cls.newInstance();
                mOptionHandlers.add(handler);
            } catch (Exception e) {
            }
        }
    }

    /**
     * 数据中心对外的数据接口，通过传入type返回相应的数据
     */
    protected final Data getDataByHandler(Class<? extends DataHandler> handler) {
        if (handler != null) {
            for (DataHandler h : mOptionHandlers) {
                if (h.getClass() == handler) {
                    return h.getData();
                }
            }
        }
        return null;
    }

    /**
     * 请求数据
     *
     * @param operation
     * @param params    请求对应的参数
     */
    protected final void performOperation(final String operation, final Object params, final ICallback callback) {
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                dispatchOperation(operation, params, callback);
            }
        });
    }

    /**
     * 分配操作,如果operation有多个处理器绑定，则只会由先绑定的处理器去处理
     *
     * @param operation 操作
     * @param params    参数
     * @param callback  回调方法
     */
    private void dispatchOperation(final String operation, final Object params, final ICallback callback) {
        for (DataHandler handler : mOptionHandlers) {
            if (handler.isContainsOperation(operation)) {
                IPerformer performer = handler.getOperationPerformer(operation);
                if (performer.isAsynchronous()) {
                    performer.performOperation(operation, params, new ICallback() {
                        @Override
                        public void onCallback(String operation, Data data) {
                            handleData(operation, data, callback);
                        }
                    });
                } else {
                    Data data = performer.performOperation(operation, params, null);
                    handleData(operation, data, callback);
                }
                return;
            }
        }
        //如果上面没有找到对应的操作处理器，则返回错误信息
        throw new RuntimeException("<<<没有找到对应的OperationPerformer处理器，请确认处理器是否已绑定>>>");
    }

    private void handleData(final String operation, final Data data, final ICallback callback) {
        //原始数据经过对应解析器解析后的数据，如果没有相应的解析器，则直接以原始数据返回
        final Data finalData = dispatchDataParser(operation, data);

        //一个Operation在处理完成并解析完数据后，在主线程以解析后的数据通过Callback回调给请求操作者
        if (callback == null) {
            return;
        }
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onCallback(operation, finalData);
            }
        });
    }

    /**
     * 在处理完一个operation后，会得到一会原始数据Data，需要找到对应的解析器去解析这份数据
     * 如果operation有多个数据解析器绑定，则只会由先绑定的处理器去处理
     * 如果没有对应的解析器，则返回原始数据
     *
     * @param operation
     * @param data
     * @return
     */
    private Data dispatchDataParser(String operation, Data data) {
        for (DataHandler handler : mOptionHandlers) {
            if (handler.isContainsParser(operation)) {
                return handler.parseData(operation, data);
            }
        }
        return data;
    }
}
