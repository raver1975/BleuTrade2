package com.alphatica.genotick.instructions;


import com.alphatica.genotick.processor.Processor;

public class SwapVariables extends VarVarInstruction {
    @SuppressWarnings("unused")
    private static final long serialVersionUID = -6328103475159894381L;

    private SwapVariables(SwapVariables i) {
        this.setVariable1Argument(i.getVariable1Argument());
        this.setVariable2Argument(i.getVariable2Argument());
    }

    @SuppressWarnings("unused")
    public SwapVariables() {
    }

    @Override
    public void executeOn(Processor processor) {
        processor.execute(this);
    }

    @Override
    public SwapVariables copy() {
        return new SwapVariables(this);
    }

}
