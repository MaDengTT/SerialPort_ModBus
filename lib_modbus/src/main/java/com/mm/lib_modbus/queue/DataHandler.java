package com.mm.lib_modbus.queue;

import com.mm.lib_modbus.modbus.ModBusData;

/**
 * Created by MaDeng on 2019/3/22
 */
public interface DataHandler {
    public void handlerData(ModBusData data);
}
