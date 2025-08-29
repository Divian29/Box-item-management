package com.example.Box_management.exceptionHandler;

public class WeightLimitExceededException extends RuntimeException{
    public WeightLimitExceededException(String message) {
        super(message);
    }

}
