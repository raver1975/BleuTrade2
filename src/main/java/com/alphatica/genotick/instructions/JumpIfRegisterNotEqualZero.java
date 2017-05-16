package com.alphatica.genotick.instructions;

import com.alphatica.genotick.processor.Processor;

public class JumpIfRegisterNotEqualZero extends RegJumpInstruction {
    @SuppressWarnings("unused")
    private static final long serialVersionUID = -6429106660478254250L;

    @SuppressWarnings("unused")
    public JumpIfRegisterNotEqualZero() {
    }

    private JumpIfRegisterNotEqualZero(JumpIfRegisterNotEqualZero i) {
        this.setRegister(i.getRegister());
        this.setAddress(i.getAddress());
    }

    @Override
    public void executeOn(Processor processor)  {
        processor.execute(this);
    }

    @Override
    public JumpIfRegisterNotEqualZero copy() {
        return new JumpIfRegisterNotEqualZero(this);
    }
}