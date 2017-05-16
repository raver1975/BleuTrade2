package com.alphatica.genotick.instructions;


import com.alphatica.genotick.processor.Processor;

public class DecrementRegister extends RegInstruction {
    @SuppressWarnings("unused")
    private static final long serialVersionUID = -7630060970364313126L;

    private DecrementRegister(DecrementRegister i) {
        this.setRegister(i.getRegister());
    }

    @SuppressWarnings("unused")
    public DecrementRegister() {
    }

    @Override
    public void executeOn(Processor processor) {
        processor.execute(this);
    }

    @Override
    public DecrementRegister copy() {
        return new DecrementRegister(this);
    }

}
