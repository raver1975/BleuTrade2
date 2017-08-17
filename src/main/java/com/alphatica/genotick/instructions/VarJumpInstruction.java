package com.alphatica.genotick.instructions;

import com.alphatica.genotick.mutator.Mutator;

abstract class VarJumpInstruction extends VarInstruction implements JumpInstruction {
    @SuppressWarnings("unused")
    private static final long serialVersionUID = -7018453916150975326L;

    private int address;
    VarJumpInstruction() {
        address = 0;
    }
    @Override
    public int getAddress() {
        return address;
    }

    @Override
    public void mutate(Mutator mutator) {
        super.mutate(mutator);
        address = mutator.getNextInt();
    }

    void setAddress(int address) {
        this.address = address;
    }
}
