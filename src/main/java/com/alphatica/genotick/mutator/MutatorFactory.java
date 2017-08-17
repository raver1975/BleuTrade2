package com.alphatica.genotick.mutator;

public class MutatorFactory {
    public static Mutator getDefaultMutator(MutatorSettings mutatorSettings) {
        Mutator mutator = SimpleMutator.getInstance();
        mutator.setSettings(mutatorSettings);
        return mutator;
    }
}
