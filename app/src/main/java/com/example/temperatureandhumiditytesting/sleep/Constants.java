package com.example.temperatureandhumiditytesting.sleep;

public class Constants {
    public static final String DEFAULT_TIME_FORMAT = "yyyy-MM-dd HH:mm";

    public interface EventFlag {
        int IMPORTANT = 1;
        int NORMAL = 0;
    }


    public interface EventClockFlag {
        int NONE = 0;
        int CLOCKED = 10;
    }

    public static final int HANDLER_SUCCESS = 0x0001;
    public static final int HANDLER_FAILED = 0x0000;
}
