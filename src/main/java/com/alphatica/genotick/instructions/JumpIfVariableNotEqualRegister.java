package com.alphatica.genotick.instructions;

import com.alphatica.genotick.processor.Processor;

public class JumpIfVariableNotEqualRegister extends RegVarJumpInstruction {
    @SuppressWarnings("unused")
    private static final long serialVersionUID = 196783147119700331L;

    private JumpIfVariableNotEqualRegister(JumpIfVariableNotEqualRegister i) {
        this.setRegister(i.getRegister());
        this.setVariableArgument(i.getVariableArgument());
        this.setAddress(i.getAddress());
    }

    @SuppressWarnings("unused")
    public JumpIfVariableNotEqualRegister() {
    }

    @Override
    public void executeOn(Processor processor) {
        processor.execute(this);
    }

    @Override
    public JumpIfVariableNotEqualRegister copy() {
        return new JumpIfVariableNotEqualRegister(this);
    }
}
