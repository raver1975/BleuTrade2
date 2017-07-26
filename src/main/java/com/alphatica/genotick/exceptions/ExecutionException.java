package com.alphatica.genotick.exceptions;

public class ExecutionException extends RuntimeException {

    public ExecutionException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
