package com.alphatica.genotick.mutator;

public class MutatorSettings {
    private final double instructionMutationProbability;
    private final double newInstructionProbability;
    private final double skipInstructionProbability;

    public MutatorSettings(double instructionMutationProbability,
                           double newInstructionProbability,
                           double skipInstructionProbability) {
        this.instructionMutationProbability = instructionMutationProbability;
        this.newInstructionProbability = newInstructionProbability;
        this.skipInstructionProbability = skipInstructionProbability;
    }


    public double getInstructionMutationProbability() {
        return instructionMutationProbability;
    }

    public double getNewInstructionProbability() {
        return newInstructionProbability;
    }

    public double getSkipInstructionProbability() {
        return skipInstructionProbability;
    }

}
