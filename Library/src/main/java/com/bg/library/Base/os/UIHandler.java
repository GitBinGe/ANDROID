package com.bg.library.Base.os;


import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

import com.bg.library.Base.Objects.BaseObject;

/**
 * Created by BinGe on 2017/8/31.
 * handler类，有子线程和主线程处理能力
 */

public class UIHandler extends BaseObject {

    /**
     * handler的名字
     */
    private String mName;
    /**
     * 子线程handler
     */
    private Handler mChildThreadHandler;
    /**
     * 主线程handler
     */
    private Handler mMainThreadHandler;

    /**
     * 传入handler的名字，后期会做子线程的管理，添加一个HandlerManager来限制handler的数量
     *
     * @param name
     */
    public UIHandler(String name) {
        this.mName = name;
    }

    /**
     * 在主线程运行
     *
     * @param runnable
     */
    public void runOnMainThread(Runnable runnable) {
        runOnMainThread(runnable, 0);
    }

    /**
     * 在主线程运行
     * @param runnable
     * * @param delayMillis 延迟
     */
    public void runOnMainThread(Runnable runnable, long delayMillis) {
        if (mMainThreadHandler == null) {
            mMainThreadHandler = new Handler(Looper.getMainLooper());
        }
        mMainThreadHandler.postDelayed(runnable, delayMillis);
    }

    /**
     * 在子线程运行
     * @param runnable
     */
    public void runOnChildThread(Runnable runnable) {
        runOnChildThread(runnable, 0);
    }

    /**
     * 在子线程运行
     * @param runnable
     * @param delayMillis 延迟
     */
    public void runOnChildThread(Runnable runnable, long delayMillis) {
        if (mChildThreadHandler == null) {
            String name = mName == null ? "HTThread" : mName;
            HandlerThread ht = new HandlerThread(name + ":" + System.currentTimeMillis());
            ht.start();
            mChildThreadHandler = new Handler(ht.getLooper());
        }
        mChildThreadHandler.postDelayed(runnable, delayMillis);
    }

}
