package com.alphatica.genotick.instructions;

import com.alphatica.genotick.processor.Processor;

public class SqRootOfRegister extends RegRegInstruction {
    @SuppressWarnings("unused")
    private static final long serialVersionUID = -2097327161652030023L;

    private SqRootOfRegister(SqRootOfRegister i) {
        this.setRegister1(i.getRegister1());
        this.setRegister2(i.getRegister2());
    }

    @SuppressWarnings("unused")
    public SqRootOfRegister() {
    }

    @Override
    public void executeOn(Processor processor) {
        processor.execute(this);
    }

    @Override
    public SqRootOfRegister copy() {
        return new SqRootOfRegister(this);
    }
}
