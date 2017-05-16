package com.alphatica.genotick.instructions;

class InstructionField {
    private final String name;
    private final String value;

    public InstructionField(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
