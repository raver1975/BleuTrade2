package com.alphatica.genotick.instructions;

import com.alphatica.genotick.processor.Processor;

import java.io.Serializable;

public class AverageOfColumn extends RegRegInstruction {

    @SuppressWarnings("unused")
    private static final long serialVersionUID = -329518949586814597L;

    @SuppressWarnings("unused")
    public AverageOfColumn() {
    }

    private AverageOfColumn(AverageOfColumn averageOfColumn) {
        this.setRegister1(averageOfColumn.getRegister1());
        this.setRegister2(averageOfColumn.getRegister2());
    }

    @Override
    public void executeOn(Processor processor) {
        processor.execute(this);
    }

    @Override
    public Instruction copy() {
        return new AverageOfColumn(this);
    }
}
