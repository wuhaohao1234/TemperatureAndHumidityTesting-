package com.example.temperatureandhumiditytesting.sleep;


//自定义异常，便于统一处理
public class TextException extends RuntimeException {
    public TextException(Throwable t) {
        super(t);
    }

}
