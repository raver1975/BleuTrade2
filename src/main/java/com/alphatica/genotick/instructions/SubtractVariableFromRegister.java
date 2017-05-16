package com.alphatica.genotick.instructions;

import com.alphatica.genotick.processor.Processor;

public class SubtractVariableFromRegister extends RegVarInstruction {
    @SuppressWarnings("unused")
    private static final long serialVersionUID = -8639456508845181659L;

    private SubtractVariableFromRegister(SubtractVariableFromRegister i) {
        this.setRegister(i.getRegister());
        this.setVariableArgument(i.getVariableArgument());
    }

    @SuppressWarnings("unused")
    public SubtractVariableFromRegister() {
    }

    @Override
    public void executeOn(Processor processor) {
        processor.execute(this);
    }

    @Override
    public SubtractVariableFromRegister copy() {
        return new SubtractVariableFromRegister(this);
    }

}
