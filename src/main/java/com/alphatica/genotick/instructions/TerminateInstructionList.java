package com.alphatica.genotick.instructions;

import com.alphatica.genotick.mutator.Mutator;
import com.alphatica.genotick.processor.Processor;

public class TerminateInstructionList extends Instruction {
    @SuppressWarnings("unused")
    private static final long serialVersionUID = 5432002295875235819L;

    public TerminateInstructionList() {
    }

    @Override
    public void executeOn(Processor processor) {
        processor.execute(this);
    }


    @Override
    public void mutate(Mutator mutator) {
        /*
        Empty. Nothing to mutate.
         */
    }

    @Override
    public Instruction copy() {
        return new TerminateInstructionList();
    }
}
