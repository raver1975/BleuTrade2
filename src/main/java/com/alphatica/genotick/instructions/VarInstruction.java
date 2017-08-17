package com.alphatica.genotick.instructions;

import com.alphatica.genotick.mutator.Mutator;

abstract class VarInstruction extends Instruction {
    @SuppressWarnings("unused")
    private static final long serialVersionUID = 5052271226112971349L;

    private int variableArgument;

    public int getVariableArgument() {
        return variableArgument;
    }

    void setVariableArgument(int variableArgument) {
        this.variableArgument = variableArgument;
    }

    @Override
    public void mutate(Mutator mutator) {
        variableArgument = mutator.getNextInt();
    }
}
