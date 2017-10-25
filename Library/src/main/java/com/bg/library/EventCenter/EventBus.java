package com.bg.library.EventCenter;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by BinGe on 2016/12/6.
 * 事件总线，用于模块之间的通讯
 */

public class EventBus {

    private static EventBus sEventBus;

    public static EventBus getInstance() {
        if (sEventBus == null) {
            sEventBus = new EventBus();
        }
        return sEventBus;
    }

    /**
     * 主线程handler，防止注册和反注册在不同线程引起bug
     */
    private Handler mThreadHandler;
    private Handler mHandler;
    private Map<EventHandler, EventFilter> mEventMap;

    private EventBus() {
        synchronized (this) {
            mEventMap = new HashMap<>();
            mHandler = new Handler(Looper.getMainLooper());

            HandlerThread thread = new HandlerThread("eventBus-thread");
            thread.start();
            mThreadHandler = new Handler(thread.getLooper());
        }
    }

    /**
     * 发送一个事件
     *
     * @param event
     */
    public void postEvent(String event) {
        postEvent(new Event(event));
    }

    /**
     * 发送一个带数据的事件
     *
     * @param event
     * @param data
     */
    public void postEvent(String event, Object data) {
        postEvent(new Event(event, data));
    }

    public void postEvent(String event, EventData data) {
        postEvent(new Event(event, data));
    }

    /**
     * 发送一个事件
     *
     * @param event
     */
    public void postEvent(final Event event) {
        if (event == null || event.event == null) {
            return;
        }
        mThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                Iterator<Map.Entry<EventHandler, EventFilter>> entries = mEventMap.entrySet().iterator();
                while (entries.hasNext()) {
                    Map.Entry<EventHandler, EventFilter> entry = entries.next();
                    EventFilter eventFilter = entry.getValue();
                    if (eventFilter.contains(event.event)) {
                        final EventHandler handler = entry.getKey();
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                Log.d("EventCenter", "处理了事件: <" + handler + ">" + " ---> <" + event + ">");
                                handler.onEvent(event);
                            }
                        });
                    }
                }
            }
        });
    }

    /**
     * 注册监听一个事件
     *
     * @param handler
     * @param event
     */
    public void registerEvent(final EventHandler handler, final EventFilter event) {
        mThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                mEventMap.put(handler, event);
                Log.d("EventCenter", "事件注册：<" + handler + ">, 当前数量：" + mEventMap.size());
            }
        });

    }

    /**
     * 反注册一个事件
     *
     * @param handler
     */
    public void unRegisterEvent(final EventHandler handler) {
        mThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mEventMap.containsKey(handler)) {
                    mEventMap.remove(handler);
                }
                Log.d("EventCenter", "事件反注册：<" + handler + ">, 当前数量：" + mEventMap.size());
            }
        });
    }


}
