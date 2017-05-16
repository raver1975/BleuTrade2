package com.alphatica.genotick.instructions;

import com.alphatica.genotick.mutator.Mutator;

abstract class RegRegJumpInstruction extends  RegRegInstruction implements JumpInstruction {
    @SuppressWarnings("unused")
    private static final long serialVersionUID = 714019783545690635L;

    private int address;

    RegRegJumpInstruction() {
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
