package com.example.Box_management.exceptionHandler;

public class BatteryLowException extends RuntimeException{
    public BatteryLowException(String message) {
        super(message);
    }
}
