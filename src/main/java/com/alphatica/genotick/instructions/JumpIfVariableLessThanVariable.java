package com.alphatica.genotick.instructions;

import com.alphatica.genotick.processor.Processor;

public class JumpIfVariableLessThanVariable extends VarVarJumpInstruction {
    @SuppressWarnings("unused")
    private static final long serialVersionUID = -849210590326352304L;

    private JumpIfVariableLessThanVariable(JumpIfVariableLessThanVariable i) {
        this.setVariable1Argument(i.getVariable1Argument());
        this.setVariable2Argument(i.getVariable2Argument());
        this.setAddress(i.getAddress());
    }

    @SuppressWarnings("unused")
    public JumpIfVariableLessThanVariable() {
    }

    @Override
    public void executeOn(Processor processor) {
        processor.execute(this);
    }

    @Override
    public JumpIfVariableLessThanVariable copy() {
        return new JumpIfVariableLessThanVariable(this);
    }
}
