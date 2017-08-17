package com.alphatica.genotick.instructions;

import com.alphatica.genotick.mutator.Mutator;
import com.alphatica.genotick.processor.NotEnoughDataException;
import com.alphatica.genotick.processor.Processor;

public class JumpTo extends Instruction implements JumpInstruction {
    @SuppressWarnings("unused")
    private static final long serialVersionUID = 8996188434274451095L;

    private int address;

    private JumpTo(JumpTo i) {
        this.address = i.getAddress();
    }

    @SuppressWarnings("unused")
    public JumpTo() {
        address = 0;
    }
    @Override
    public void executeOn(Processor processor) throws NotEnoughDataException {
        processor.execute(this);
    }

    @Override
    public void mutate(Mutator mutator) {
        address = mutator.getNextInt();
    }

    @Override
    public JumpTo copy() {
        return new JumpTo(this);
    }

    @Override
    public int getAddress() {
        return address;
    }
}
