package com.alphatica.genotick.instructions;

import com.alphatica.genotick.processor.Processor;

public class JumpIfVariableEqualVariable extends VarVarJumpInstruction {
    @SuppressWarnings("unused")
    private static final long serialVersionUID = 7095626127058648815L;

    private JumpIfVariableEqualVariable(JumpIfVariableEqualVariable i) {
        this.setVariable1Argument(i.getVariable1Argument());
        this.setVariable2Argument(i.getVariable2Argument());
        this.setAddress(i.getAddress());
    }

    @SuppressWarnings("unused")
    public JumpIfVariableEqualVariable() {
    }

    @Override
    public void executeOn(Processor processor) {
        processor.execute(this);
    }

    @Override
    public JumpIfVariableEqualVariable copy() {
        return new JumpIfVariableEqualVariable(this);
    }
}
