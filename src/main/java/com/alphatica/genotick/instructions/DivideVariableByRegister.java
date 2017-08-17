package com.alphatica.genotick.instructions;

import com.alphatica.genotick.processor.Processor;

public class DivideVariableByRegister extends RegVarInstruction {
    @SuppressWarnings("unused")
    private static final long serialVersionUID = -8300592248680778047L;

    private DivideVariableByRegister(DivideVariableByRegister i) {
        this.setRegister(i.getRegister());
        this.setVariableArgument(i.getVariableArgument());
    }

    @SuppressWarnings("unused")
    public DivideVariableByRegister() {
    }

    @Override
    public void executeOn(Processor processor) {
        processor.execute(this);
    }

    @Override
    public DivideVariableByRegister copy() {
        return new  DivideVariableByRegister(this);
    }

}
