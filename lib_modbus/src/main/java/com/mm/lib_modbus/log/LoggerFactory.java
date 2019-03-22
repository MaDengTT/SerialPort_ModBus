package com.mm.lib_modbus.log;

public class LoggerFactory {
    private static Logger log = new Logger();
    public static Logger getLog(Class tagClass){
        return log;
    }
}
