package com.alphatica.genotick.instructions;

import com.alphatica.genotick.processor.Processor;

public class MoveRelativeDataToVariable extends DataVarInstruction {
    @SuppressWarnings("unused")
    private static final long serialVersionUID = 1308815201295846632L;

    private MoveRelativeDataToVariable(MoveRelativeDataToVariable i) {
        this.setDataOffsetIndex(i.getDataOffsetIndex());
        this.setDataTableIndex(i.getDataColumnIndex());
        this.setVariableArgument(i.getVariableArgument());
    }

    @SuppressWarnings("unused")
    public MoveRelativeDataToVariable() {
    }

    @Override
    public void executeOn(Processor processor)   {
        processor.execute(this);
    }

    @Override
    public MoveRelativeDataToVariable copy() {
        return new MoveRelativeDataToVariable(this);
    }

}
