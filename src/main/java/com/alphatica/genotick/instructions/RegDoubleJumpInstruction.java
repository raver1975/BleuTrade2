package com.alphatica.genotick.instructions;

import com.alphatica.genotick.mutator.Mutator;

abstract class RegDoubleJumpInstruction extends RegDoubleInstruction implements JumpInstruction {
    @SuppressWarnings("unused")
    private static final long serialVersionUID = 2490196013277564185L;

    private int address;

    RegDoubleJumpInstruction() {
        address = 0;
    }
    @Override
    public int getAddress() {
        return address;
    }

    void setAddress(int address) {
        this.address = address;
    }

    @Override
    public void mutate(Mutator mutator) {
        super.mutate(mutator);
        address = mutator.getNextInt();
    }

}
