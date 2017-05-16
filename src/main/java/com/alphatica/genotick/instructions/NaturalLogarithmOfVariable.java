package com.alphatica.genotick.instructions;

import com.alphatica.genotick.processor.Processor;

public class NaturalLogarithmOfVariable extends VarVarInstruction {
    @SuppressWarnings("unused")
    private static final long serialVersionUID = -3112125542251877233L;

    private NaturalLogarithmOfVariable(NaturalLogarithmOfVariable i) {
        this.setVariable1Argument(i.getVariable1Argument());
        this.setVariable2Argument(i.getVariable2Argument());
    }

    @SuppressWarnings("unused")
    public NaturalLogarithmOfVariable() {
    }

    @Override
    public void executeOn(Processor processor) {
        processor.execute(this);
    }

    @Override
    public NaturalLogarithmOfVariable copy() {
        return new NaturalLogarithmOfVariable(this);
    }
}
