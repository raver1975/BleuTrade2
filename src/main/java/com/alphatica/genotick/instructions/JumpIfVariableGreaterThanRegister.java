package com.alphatica.genotick.instructions;

import com.alphatica.genotick.processor.Processor;

public class JumpIfVariableGreaterThanRegister extends RegVarJumpInstruction {
    @SuppressWarnings("unused")
    private static final long serialVersionUID = 5018149141040073118L;

    private JumpIfVariableGreaterThanRegister(JumpIfVariableGreaterThanRegister i) {
        this.setRegister(i.getRegister());
        this.setVariableArgument(i.getVariableArgument());
        this.setAddress(i.getAddress());
    }

    @SuppressWarnings("unused")
    public JumpIfVariableGreaterThanRegister() {
    }

    @Override
    public void executeOn(Processor processor) {
        processor.execute(this);
    }

    @Override
    public JumpIfVariableGreaterThanRegister copy() {
        return new JumpIfVariableGreaterThanRegister(this);
    }
}
