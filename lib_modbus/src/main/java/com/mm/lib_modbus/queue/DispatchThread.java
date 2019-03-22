package com.mm.lib_modbus.queue;

import android.util.Log;

import com.mm.lib_modbus.log.Logger;
import com.mm.lib_modbus.log.LoggerFactory;
import com.mm.lib_modbus.modbus.ModBusData;

import java.util.concurrent.BlockingQueue;


/**
 * Created by MaDeng on 2019/3/22
 */
public class DispatchThread extends Thread {

    private String tag = "";

    private final BlockingQueue<ModBusData<?>> queue;

    private volatile boolean quit = false;
    private DataHandler dataHandler;

    private Logger logger = LoggerFactory.getLog(this.getClass());

    public DispatchThread(BlockingQueue<ModBusData<?>> queue, DataHandler messageHandler, String tag) {
        this.queue = queue;
        dataHandler = messageHandler;
        this.tag = tag;
    }

    public void quit() {
        quit = true;
        interrupt();
    }

    @Override
    public void run() {
        log(tag + "start run");
        while (true) {
            if (quit) {
                log(tag+"stop run");
                return;
            }
            ModBusData<?> request = null;

            try {
                log(tag + "take request");
                request = queue.take();
                dataHandler.handlerData(request);
            } catch (InterruptedException e) {
                e.printStackTrace();
                log(tag+"stop run");
            }

        }
    }

    private void log(String str) {
        Log.i(tag, str);
        logger.info(str);
    }
}
