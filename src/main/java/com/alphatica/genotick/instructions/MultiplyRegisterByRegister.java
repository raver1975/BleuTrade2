package com.alphatica.genotick.instructions;

import com.alphatica.genotick.processor.Processor;

public class MultiplyRegisterByRegister extends RegRegInstruction {
    @SuppressWarnings("unused")
    private static final long serialVersionUID = 7185538925515388482L;

    private MultiplyRegisterByRegister(MultiplyRegisterByRegister i) {
        this.setRegister1(i.getRegister1());
        this.setRegister2(i.getRegister2());
    }

    @SuppressWarnings("unused")
    public MultiplyRegisterByRegister() {
    }

    @Override
    public void executeOn(Processor processor) {
        processor.execute(this);
    }

    @Override
    public MultiplyRegisterByRegister copy() {
        return new MultiplyRegisterByRegister(this);
    }

}
