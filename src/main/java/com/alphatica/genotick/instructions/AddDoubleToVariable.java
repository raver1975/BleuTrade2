package com.alphatica.genotick.instructions;

import com.alphatica.genotick.processor.Processor;

public class AddDoubleToVariable extends VarDoubleInstruction {

    @SuppressWarnings("unused")
    private static final long serialVersionUID = -6197886980513050186L;

    private AddDoubleToVariable(AddDoubleToVariable i) {
        this.setDoubleArgument(i.getDoubleArgument());
        this.setVariableArgument(i.getVariableArgument());
    }

    @SuppressWarnings("unused")
    public AddDoubleToVariable() {
    }

    @Override
    public void executeOn(Processor processor) {
        processor.execute(this);
    }

    @Override
    public AddDoubleToVariable copy() {
        return new AddDoubleToVariable(this);
    }

}
