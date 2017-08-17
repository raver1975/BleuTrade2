package com.alphatica.genotick.instructions;

import com.alphatica.genotick.processor.Processor;

public class MoveRelativeDataToRegister extends DataRegInstruction {
    @SuppressWarnings("unused")
    private static final long serialVersionUID = -2247072351675972683L;

    private MoveRelativeDataToRegister(MoveRelativeDataToRegister i) {
        this.setDataOffsetIndex(i.getDataOffsetIndex());
        this.setDataTableIndex(i.getDataColumnIndex());
        this.setRegister(i.getRegister());
    }

    @SuppressWarnings("unused")
    public MoveRelativeDataToRegister() {
    }

    @Override
    public void executeOn(Processor processor)  {
        processor.execute(this);
    }

    @Override
    public MoveRelativeDataToRegister copy() {
        return new MoveRelativeDataToRegister(this);
    }

}
