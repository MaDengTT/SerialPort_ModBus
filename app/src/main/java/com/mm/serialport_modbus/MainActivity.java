package com.mm.serialport_modbus;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.mm.lib_modbus.SerialClient;
import com.mm.lib_modbus.SerialPortParams;
import com.mm.lib_modbus.SerialUtils;
import com.mm.lib_modbus.modbus.ModBusData;
import com.mm.lib_modbus.modbus.ModBusDataListener;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    /*        new Thread(new Runnable() {
            @Override
            public void run() {
                init("/dev");
            }
        }).start();*/

    init("/dev/ttyS3");
//        init("/dev/address");

}

    private void init(final String address) {
        SerialPortParams build = new SerialPortParams.Builder().serialPortPath(address).build();


        final SerialClient serialClient = SerialUtils.getInstance().getSerialClient(address);

        if (serialClient == null) {
            return;
        }

        serialClient.sendData(new ModBusData<Object>(1, 3, 0, 8, new ModBusDataListener() {
            @Override
            public void onSucceed(String hexValue, byte[] bytes) {
                Log.d(TAG, "onSucceed: " + hexValue);
            }

            @Override
            public void onFailed(String str) {

            }
        }));

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    SystemClock.sleep(500);
                    serialClient.sendData(new ModBusData<Object>(1, 0x04, 0, 8, new ModBusDataListener() {
                        @Override
                        public void onSucceed(String hexValue, byte[] bytes) {
                            Log.d(TAG, "读8位: " + hexValue);
                        }

                        @Override
                        public void onFailed(String str) {

                        }
                    }));
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
//                    Log.d(TAG, "run: "+Thread.currentThread().getId());
                    SystemClock.sleep(1000);
                    serialClient.sendData(new ModBusData<Object>(1, 0x04, 0, 3, new ModBusDataListener() {
                        @Override
                        public void onSucceed(String hexValue, byte[] bytes) {
                            Log.d(TAG, "读三位: " + hexValue);
                        }

                        @Override
                        public void onFailed(String str) {

                        }
                    }));
                }
            }
        }).start();

    }
}
