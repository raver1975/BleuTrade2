package com.alphatica.genotick.instructions;

import com.alphatica.genotick.processor.Processor;

public class AddVariableToVariable extends VarVarInstruction {
    @SuppressWarnings("unused")
    private static final long serialVersionUID = 232466498704321646L;

    private AddVariableToVariable(AddVariableToVariable i) {
        this.setVariable1Argument(i.getVariable1Argument());
        this.setVariable2Argument(i.getVariable2Argument());
    }

    @SuppressWarnings("unused")
    public AddVariableToVariable() {
    }

    @Override
    public void executeOn(Processor processor) {
        processor.execute(this);
    }

    @Override
    public AddVariableToVariable copy() {
        return new AddVariableToVariable(this);
    }

}
