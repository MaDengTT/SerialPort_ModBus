package com.mm.lib_modbus.modbus;

/**
 * Created by MaDeng on 2019/3/22
 */
public interface ModBusDataListener {

    void onSucceed(/*int unit,int fcode,int ref,int count,*/String hexValue,byte[] bytes);
    void onFailed(String str);

}
