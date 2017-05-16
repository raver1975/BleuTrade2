package com.alphatica.genotick.instructions;

import com.alphatica.genotick.mutator.Mutator;

abstract public class DataInstruction extends Instruction {
    @SuppressWarnings("unused")
    private static final long serialVersionUID = -2955270878126863352L;
    private int dataTableIndex;
    private int dataOffsetIndex;

    void setDataTableIndex(int dataTableIndex) {
        this.dataTableIndex = dataTableIndex;
    }

    void setDataOffsetIndex(int dataOffsetIndex) {
        this.dataOffsetIndex = dataOffsetIndex;
    }

    public int getDataColumnIndex() {
        return dataTableIndex;
    }

    public int getDataOffsetIndex() {
        return dataOffsetIndex;
    }

    @Override
    public void mutate(Mutator mutator) {
        dataTableIndex = mutator.getNextInt();
        dataOffsetIndex = mutator.getNextInt();
    }
}
