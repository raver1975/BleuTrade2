package com.alphatica.genotick.instructions;


import com.alphatica.genotick.processor.Processor;

public class MoveRegisterToVariable extends RegVarInstruction {
    @SuppressWarnings("unused")
    private static final long serialVersionUID = -6846019505484559555L;

    private MoveRegisterToVariable(MoveRegisterToVariable i) {
        this.setRegister(i.getRegister());
        this.setVariableArgument(i.getVariableArgument());
    }

    @SuppressWarnings("unused")
    public MoveRegisterToVariable() {
    }

    @Override
    public void executeOn(Processor processor) {
        processor.execute(this);
    }

    @Override
    public MoveRegisterToVariable copy() {
        return new MoveRegisterToVariable(this);
    }

}
