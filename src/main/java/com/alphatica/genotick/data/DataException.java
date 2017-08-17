package com.alphatica.genotick.data;

class DataException extends RuntimeException {

    @SuppressWarnings("unused")
    private static final long serialVersionUID = -5264726885012607329L;

    DataException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public DataException(String s) {
        super(s);
    }
}
