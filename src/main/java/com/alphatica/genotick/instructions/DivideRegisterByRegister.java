package com.alphatica.genotick.instructions;

import com.alphatica.genotick.processor.Processor;

public class DivideRegisterByRegister extends RegRegInstruction {
    @SuppressWarnings("unused")
    private static final long serialVersionUID = 5202607381101727036L;

    private DivideRegisterByRegister(DivideRegisterByRegister i) {
        this.setRegister1(i.getRegister1());
        this.setRegister2(i.getRegister2());
    }

    @SuppressWarnings("unused")
    public DivideRegisterByRegister() {
    }

    @Override
    public void executeOn(Processor processor) {
        processor.execute(this);
    }

    @Override
    public DivideRegisterByRegister copy() {
        return new DivideRegisterByRegister(this);
    }

}
