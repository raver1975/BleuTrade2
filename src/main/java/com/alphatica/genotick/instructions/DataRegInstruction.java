package com.alphatica.genotick.instructions;

import com.alphatica.genotick.mutator.Mutator;

abstract public class DataRegInstruction extends DataInstruction {
    @SuppressWarnings("unused")
    private static final long serialVersionUID = 4226724935141470072L;

    private byte register;

    public byte getRegister() {
        return register;
    }

    void setRegister(byte register) {
        this.register = Registers.validateRegister(register);
    }

    @Override
    public void mutate(Mutator mutator) {
        super.mutate(mutator);
        register = Registers.validateRegister(mutator.getNextByte());
    }
}
