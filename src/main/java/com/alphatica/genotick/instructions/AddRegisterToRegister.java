package com.alphatica.genotick.instructions;

import com.alphatica.genotick.processor.Processor;

public class AddRegisterToRegister extends RegRegInstruction {

    @SuppressWarnings("unused")
    private static final long serialVersionUID = 3465536183323672440L;

    private AddRegisterToRegister(AddRegisterToRegister i) {
        this.setRegister1(i.getRegister1());
        this.setRegister2(i.getRegister2());
    }

    @SuppressWarnings("unused")
    public AddRegisterToRegister() {
    }

    @Override
    public void executeOn(Processor processor) {
        processor.execute(this);
    }

    @Override
    public AddRegisterToRegister copy() {
        return new AddRegisterToRegister(this);
    }

}
