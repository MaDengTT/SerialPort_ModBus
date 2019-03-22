package com.mm.lib_modbus.modbus;

import com.ghgande.j2mod.modbus.Modbus;
import com.ghgande.j2mod.modbus.ModbusException;
import com.ghgande.j2mod.modbus.io.ModbusSerialTransaction;
import com.ghgande.j2mod.modbus.msg.ReadInputRegistersRequest;
import com.ghgande.j2mod.modbus.msg.ReadInputRegistersResponse;
import com.ghgande.j2mod.modbus.net.SerialConnection;
import com.ghgande.j2mod.modbus.util.SerialParameters;
import com.mm.lib_modbus.SerialPortParams;
import com.mm.lib_modbus.queue.DataHandler;

import java.io.IOException;

/**
 * Created by MaDeng on 2019/3/22
 */
public class ModBusDataHandler implements DataHandler {


    private final SerialConnection connection;
    private final ModbusSerialTransaction transaction;

    public ModBusDataHandler(SerialPortParams serialPortParams) throws IOException {
        SerialParameters parameters = new SerialParameters();
        parameters.setBaudRate(serialPortParams.getSerialPortBaudRate());
        parameters.setEncoding(Modbus.SERIAL_ENCODING_RTU);
        parameters.setDatabits(serialPortParams.getSerialPortDataBits());
        parameters.setStopbits(serialPortParams.getSerialPortStopBits());
        parameters.setParity(serialPortParams.getSerialPortParity());
        parameters.setEcho(false);
        parameters.setPortName(serialPortParams.getSerialPortPath());
        connection = new SerialConnection(parameters);
        connection.open();

        transaction = new ModbusSerialTransaction(connection);
    }

    @Override
    public void handlerData(ModBusData data) {
        switch (data.fcode) {
            case 0x04:
                ReadInputRegistersRequest req = new ReadInputRegistersRequest(data.ref, data.count);
                req.setUnitID(data.unitId);
                req.setHeadless();

                try {
                    transaction.setRequest(req);
                    transaction.execute();
                    ReadInputRegistersResponse response = (ReadInputRegistersResponse) transaction.getResponse();
                    data.listener.onSucceed(/*data.unitId,
                            data.fcode,
                            data.ref,
                            data.count,*/
                            response.getHexMessage(),
                            response.getMessage());
                } catch (ModbusException e) {
                    e.printStackTrace();
                    data.listener.onFailed("数据获取失败");
                }
                break;
        }
    }
}
