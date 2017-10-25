package com.bg.library.DataCenter;


import android.os.Handler;
import android.os.Looper;

import com.bg.library.Base.Objects.JError;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created by BinGe on 2017/8/31.
 * 数据中心，app中所有的数据都通过DataCenter请求或获取
 */

public class DataCenter {

    //*******************************************************************************************
    //*******************************   数据中心对外接口  start  **********************************
    //*******************************************************************************************

    /**
     * 调用{@link DataCenter#perform(String, Object, Callback)}时
     * 如果需要数据回调，则实现这个接口等待回调
     */
    public interface Callback {
        void onCallback(String operation, Data data);
    }

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
    public Data getData(DataType type) {
        return getDataByDataType(type);
    }

    /**
     * 执行一次操作
     *
     * @param operation
     * @param params    请求对应的参数
     */
    public void perform(String operation, Object params, Callback callback) {
        performOperation(operation, params, callback);
    }

    //*******************************************************************************************
    //*******************************   数据中心对外接口  end    **********************************
    //*******************************************************************************************


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
    private DataCenter() {
        mHandler = new Handler(Looper.getMainLooper());
        mThreadPool = Executors.newFixedThreadPool(3);
    }

    /**
     * 初始化处理器
     */
    public void initDataHandlers(List<Class<? extends DataHandler>> handlers) {
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
     *
     * @param type
     * @return
     */
    private Data getDataByDataType(DataType type) {
        Class<?> cls = type.handler;
        if (cls != null) {
            for (DataHandler handler : mOptionHandlers) {
                if (handler.getClass() == cls) {
                    return handler.getData();
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
    private void performOperation(final String operation, final Object params, final Callback callback) {
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
    private void dispatchOperation(final String operation, final Object params, final Callback callback) {
        for (DataHandler handler : mOptionHandlers) {
            if (handler.isContainsOperation(operation)) {
                OperationPerformer performer = handler.getOperationPerformer(operation);
                if (performer.isAsynchronous()) {
                    performer.performOperation(operation, params, new Callback() {
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
        Data data = new Data(new JError("<<<没有找到对应的OperationPerformer处理器，请确认处理器是否已绑定>>>"));
        throw new RuntimeException(data.error.errorInfo);
    }

    private void handleData(final String operation, final Data data, final Callback callback) {
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
