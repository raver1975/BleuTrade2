package com.alphatica.genotick.instructions;

import com.alphatica.genotick.mutator.Mutator;

abstract class RegVarJumpInstruction extends RegVarInstruction implements JumpInstruction {
    @SuppressWarnings("unused")
    private static final long serialVersionUID = 3071849519057144513L;

    private int address;

    RegVarJumpInstruction() {
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
