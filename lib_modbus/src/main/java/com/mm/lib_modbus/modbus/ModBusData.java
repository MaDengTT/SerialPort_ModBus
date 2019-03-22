package com.mm.lib_modbus.modbus;

import com.ghgande.j2mod.modbus.facade.ModbusSerialMaster;

import java.util.List;

/**
 * Created by MaDeng on 2019/3/22
 */
public class ModBusData<T> implements Comparable<ModBusData<T>> {

    private final String TAG = this.getClass().getSimpleName();
    protected T data;

    public int unitId;

    public int fcode;

    public int ref;

    public int count;

    public boolean coliB;

    public List<byte[]> writeData;

    public ModBusData(int unitId, int fcode, int ref, int count, ModBusDataListener listener) {
        this.unitId = unitId;
        this.fcode = fcode;
        this.ref = ref;
        this.count = count;
        this.listener = listener;
    }

    public ModBusData(int unitId, int fcode, int ref, boolean coliB, ModBusDataListener listener) {
        this.unitId = unitId;
        this.fcode = fcode;
        this.ref = ref;
        this.coliB = coliB;
        this.listener = listener;
    }


    public ModBusData(int unitId, int fcode, int ref, List<byte[]> writeData) {
        this.unitId = unitId;
        this.fcode = fcode;
        this.ref = ref;
        this.writeData = writeData;
    }

    public ModBusDataListener listener;

    public ModBusDataListener getListener() {
        return listener;
    }

    @Override
    public int compareTo(ModBusData<T> o) {
        if (o == null) {
            return -1;
        }
        return 0;
    }
}
