package com.alphatica.genotick.instructions;

import com.alphatica.genotick.processor.Processor;

public class JumpIfRegisterLessThanDouble extends RegDoubleJumpInstruction {
    @SuppressWarnings("unused")
    private static final long serialVersionUID = -8871392325622765389L;

    private JumpIfRegisterLessThanDouble(JumpIfRegisterLessThanDouble i) {
        this.setRegister(i.getRegister());
        this.setDoubleArgument(i.getDoubleArgument());
        this.setAddress(i.getAddress());
    }

    @SuppressWarnings("unused")
    public JumpIfRegisterLessThanDouble() {
    }

    @Override
    public void executeOn(Processor processor)  {
        processor.execute(this);
    }

    @Override
    public JumpIfRegisterLessThanDouble copy() {
        return new JumpIfRegisterLessThanDouble(this);
    }
}
