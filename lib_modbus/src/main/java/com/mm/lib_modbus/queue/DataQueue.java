package com.mm.lib_modbus.queue;

import com.mm.lib_modbus.modbus.ModBusData;

import java.util.concurrent.PriorityBlockingQueue;

/**
 * Created by MaDeng on 2019/3/22
 */
public class DataQueue {

    private String mTag = "";

    private final PriorityBlockingQueue<ModBusData<?>> mSignalRequestQueue = new PriorityBlockingQueue<>();
    private DispatchThread mDispatcher;
    private DataHandler mMessageHandler;

    public DataQueue(DataHandler messageHandler, String tag) {
        mMessageHandler = messageHandler;
        mTag = tag;
    }

    public void start() {
        stop();
        mDispatcher = new DispatchThread(mSignalRequestQueue, mMessageHandler, mTag);
        mDispatcher.start();
    }

    public void stop() {
        if (mDispatcher != null) {
            mDispatcher.quit();
        }
    }

    public <T> ModBusData<T> add(ModBusData<T> request) {
        synchronized (mSignalRequestQueue) {
            mSignalRequestQueue.add(request);
        }
        return request;
    }
}
