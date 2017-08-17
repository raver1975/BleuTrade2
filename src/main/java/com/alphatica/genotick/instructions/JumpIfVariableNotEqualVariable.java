package com.alphatica.genotick.instructions;

import com.alphatica.genotick.processor.Processor;

import java.io.Serializable;

public class JumpIfVariableNotEqualVariable extends VarVarJumpInstruction {
    @SuppressWarnings("unused")
    private static final long serialVersionUID = 3271032134612877777L;

    private JumpIfVariableNotEqualVariable(JumpIfVariableNotEqualVariable i) {
        this.setVariable1Argument(i.getVariable1Argument());
        this.setVariable2Argument(i.getVariable2Argument());
        this.setAddress(i.getAddress());
    }

    @SuppressWarnings("unused")
    public JumpIfVariableNotEqualVariable() {
    }

    @Override
    public void executeOn(Processor processor)  {
        processor.execute(this);
    }

    @Override
    public JumpIfVariableNotEqualVariable copy() {
        return new JumpIfVariableNotEqualVariable(this);
    }
}
