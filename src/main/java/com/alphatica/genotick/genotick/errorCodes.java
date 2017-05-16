package com.alphatica.genotick.genotick;


enum errorCodes {
    NO_INPUT(1),
    UNKNOWN_ARGUMENT(2),
    NO_OUTPUT(3);

    private final int code;

    errorCodes(int code) {
        this.code = code;
    }

    public int getValue() {
        return code;
    }
}
