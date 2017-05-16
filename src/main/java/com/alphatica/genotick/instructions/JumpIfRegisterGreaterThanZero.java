package com.alphatica.genotick.instructions;

import com.alphatica.genotick.processor.Processor;

public class JumpIfRegisterGreaterThanZero extends RegJumpInstruction {
    @SuppressWarnings("unused")
    private static final long serialVersionUID = 2932427307222806723L;

    private JumpIfRegisterGreaterThanZero(JumpIfRegisterGreaterThanZero i) {
        this.setRegister(i.getRegister());
        this.setAddress(i.getAddress());
    }

    @SuppressWarnings("unused")
    public JumpIfRegisterGreaterThanZero() {
    }

    @Override
    public void executeOn(Processor processor)  {
        processor.execute(this);
    }

    @Override
    public JumpIfRegisterGreaterThanZero copy() {
        return new JumpIfRegisterGreaterThanZero(this);
    }
}
