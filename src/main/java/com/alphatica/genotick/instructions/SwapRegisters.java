package com.alphatica.genotick.instructions;


import com.alphatica.genotick.processor.Processor;

public class SwapRegisters extends RegRegInstruction {
    @SuppressWarnings("unused")
    private static final long serialVersionUID = -3433775138789900573L;

    private SwapRegisters(SwapRegisters i) {
        this.setRegister1(i.getRegister1());
        this.setRegister2(i.getRegister2());
    }

    @SuppressWarnings("unused")
    public SwapRegisters() {
    }

    @Override
    public void executeOn(Processor processor) {
        processor.execute(this);
    }

    @Override
    public SwapRegisters copy() {
        return new SwapRegisters(this);
    }
}
