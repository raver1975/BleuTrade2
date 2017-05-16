package com.alphatica.genotick.instructions;

import com.alphatica.genotick.processor.Processor;

public class JumpIfRegisterNotEqualDouble extends RegDoubleJumpInstruction {
    @SuppressWarnings("unused")
    private static final long serialVersionUID = -8395514990644799759L;

    private JumpIfRegisterNotEqualDouble(JumpIfRegisterNotEqualDouble i) {
        this.setRegister(i.getRegister());
        this.setDoubleArgument(i.getDoubleArgument());
        this.setAddress(i.getAddress());
    }

    @SuppressWarnings("unused")
    public JumpIfRegisterNotEqualDouble() {
    }

    @Override
    public void executeOn(Processor processor)  {
        processor.execute(this);
    }

    @Override
    public JumpIfRegisterNotEqualDouble copy() {
        return new JumpIfRegisterNotEqualDouble(this);
    }
}
