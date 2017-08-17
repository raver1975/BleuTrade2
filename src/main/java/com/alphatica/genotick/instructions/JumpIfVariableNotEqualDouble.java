package com.alphatica.genotick.instructions;

import com.alphatica.genotick.processor.Processor;

import java.io.Serializable;

public class JumpIfVariableNotEqualDouble extends VarDoubleJumpInstruction {
    @SuppressWarnings("unused")
    private static final long serialVersionUID = 3542351953333254754L;

    private JumpIfVariableNotEqualDouble(JumpIfVariableNotEqualDouble i) {
        this.setVariableArgument(i.getVariableArgument());
        this.setDoubleArgument(i.getDoubleArgument());
        this.setAddress(i.getAddress());
    }

    @SuppressWarnings("unused")
    public JumpIfVariableNotEqualDouble() {
    }

    @Override
    public void executeOn(Processor processor)  {
        processor.execute(this);
    }

    @Override
    public JumpIfVariableNotEqualDouble copy() {
        return new JumpIfVariableNotEqualDouble(this);
    }
}
