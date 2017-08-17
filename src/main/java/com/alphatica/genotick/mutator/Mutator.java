package com.alphatica.genotick.mutator;

import com.alphatica.genotick.instructions.Instruction;

public interface Mutator {
    Instruction getRandomInstruction();

    boolean getAllowInstructionMutation();

    boolean getAllowNewInstruction();

    int getNextInt();

    double getNextDouble();

    byte getNextByte();

    void setSettings(MutatorSettings mutatorSettings);

    boolean skipNextInstruction();

}
