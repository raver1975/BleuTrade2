package com.alphatica.genotick.instructions;

import com.alphatica.genotick.mutator.Mutator;

abstract class VarVarJumpInstruction extends VarVarInstruction implements JumpInstruction {
    @SuppressWarnings("unused")
    private static final long serialVersionUID = 6418593915852218659L;

    private int address;

    VarVarJumpInstruction() {
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
