package com.mm.lib_modbus;

import com.mm.lib_modbus.queue.DataHandler;
import com.mm.lib_modbus.queue.DataQueue;
import com.mm.lib_modbus.modbus.ModBusData;

/**
 * Created by MaDeng on 2019/3/22
 */
public class SerialClient {

    private final String tag;

    public SerialClient(String tag) {
        this.tag = tag;
    }

    private DataQueue mDataQueue;

    public void init(DataHandler dataHandler) {
        if (null == mDataQueue) {
            synchronized (SerialClient.class) {
                if (null == mDataQueue) {
                    mDataQueue = new DataQueue(dataHandler,tag);
                    mDataQueue.start();
                }
            }
        }
    }

    public void sendData(ModBusData<?> request) {
        if (mDataQueue == null) {
            return;
        }
        mDataQueue.add(request);
    }


    public void stop() {
        if (mDataQueue != null) {
            mDataQueue.stop();
        }
    }

}
