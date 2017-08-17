package com.alphatica.genotick.instructions;

import com.alphatica.genotick.processor.Processor;

public class DivideVariableByDouble extends VarDoubleInstruction {
    @SuppressWarnings("unused")
    private static final long serialVersionUID = 2277032167143213475L;

    private DivideVariableByDouble(DivideVariableByDouble i) {
        this.setDoubleArgument(i.getDoubleArgument());
        this.setVariableArgument(i.getVariableArgument());
    }

    @SuppressWarnings("unused")
    public DivideVariableByDouble() {
    }

    @Override
    public void executeOn(Processor processor) {
        processor.execute(this);
    }

    @Override
    public DivideVariableByDouble copy() {
        return new DivideVariableByDouble(this);
    }

}
