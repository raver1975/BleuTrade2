package com.alphatica.genotick.instructions;

import com.alphatica.genotick.mutator.Mutator;

abstract class RegVarInstruction extends RegInstruction {
    @SuppressWarnings("unused")
    private static final long serialVersionUID = 2162434928345582409L;

    private int variableArgument;

    void setVariableArgument(int variable) {
        this.variableArgument = variable;
    }

    public int getVariableArgument() {
        return variableArgument;
    }


    @Override
    public void mutate(Mutator mutator) {
        super.mutate(mutator);
        variableArgument = mutator.getNextInt();
    }
}
