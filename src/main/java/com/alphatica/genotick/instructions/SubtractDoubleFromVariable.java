package com.alphatica.genotick.instructions;

import com.alphatica.genotick.processor.Processor;

public class SubtractDoubleFromVariable extends VarDoubleInstruction {
    @SuppressWarnings("unused")
    private static final long serialVersionUID = 8293191797685003121L;

    private SubtractDoubleFromVariable(SubtractDoubleFromVariable i) {
        this.setVariableArgument(i.getVariableArgument());
        this.setDoubleArgument(i.getDoubleArgument());
    }

    @SuppressWarnings("unused")
    public SubtractDoubleFromVariable() {
    }

    @Override
    public void executeOn(Processor processor) {
        processor.execute(this);
    }

    @Override
    public SubtractDoubleFromVariable copy() {
        return new SubtractDoubleFromVariable(this);
    }

}
