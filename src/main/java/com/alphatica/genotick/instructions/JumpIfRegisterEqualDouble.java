package com.alphatica.genotick.instructions;

import com.alphatica.genotick.processor.Processor;

public class JumpIfRegisterEqualDouble extends RegDoubleJumpInstruction {
    @SuppressWarnings("unused")
    private static final long serialVersionUID = -3677241217620353564L;

    private JumpIfRegisterEqualDouble(JumpIfRegisterEqualDouble i) {
        this.setDoubleArgument(i.getDoubleArgument());
        this.setRegister(i.getRegister());
        this.setAddress(i.getAddress());
    }

    @SuppressWarnings("unused")
    public JumpIfRegisterEqualDouble() {
    }

    @Override
    public void executeOn(Processor processor)  {
        processor.execute(this);
    }

    @Override
    public JumpIfRegisterEqualDouble copy() {
        return new JumpIfRegisterEqualDouble(this);
    }
}
