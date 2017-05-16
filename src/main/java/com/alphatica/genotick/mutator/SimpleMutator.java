package com.alphatica.genotick.mutator;

import com.alphatica.genotick.genotick.RandomGenerator;
import com.alphatica.genotick.instructions.Instruction;
import com.alphatica.genotick.processor.Processor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class SimpleMutator implements Mutator {
    private MutatorSettings settings;
    private final Random random;
    private final List<Class< ? super Instruction>> instructionList;
    private int totalInstructions;

    private SimpleMutator() throws ClassNotFoundException {
        random = RandomGenerator.assignRandom();
        instructionList = new ArrayList<>();
        buildInstructionList(instructionList);
    }

    @SuppressWarnings("unchecked")
    private void buildInstructionList(List<Class<? super Instruction>> instructionList) throws ClassNotFoundException {
        Class processorClass = Processor.class;
        Method[] methods = processorClass.getDeclaredMethods();
        for(Method m: methods) {
            Class [] types = m.getParameterTypes();
            for(Class t: types) {
                Class<Instruction> c = (Class<Instruction>)Class.forName(t.getName());
                instructionList.add(c);
            }
        }
        totalInstructions = instructionList.size();
    }

    static Mutator getInstance() {
        try {
            return new SimpleMutator();
        } catch(ClassNotFoundException ex) {
            throw new RuntimeException("Unable to get Class", ex);
        }
    }

    @Override
    public Instruction getRandomInstruction() {
        int index = random.nextInt(totalInstructions);
        return createNewInstruction(index);
    }

    private Instruction createNewInstruction(int index) {
        try {
            return (Instruction) instructionList.get(index).newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean getAllowInstructionMutation() {
        return random.nextDouble() < settings.getInstructionMutationProbability();
    }

    @Override
    public boolean getAllowNewInstruction() {
        return random.nextDouble() < settings.getNewInstructionProbability();
    }

    @Override
    public int getNextInt() {
        return random.nextInt();
    }

    @Override
    public double getNextDouble() {
        if(random.nextBoolean()) {
            return random.nextDouble();
        } else {
            return -random.nextDouble();
        }
    }

    @Override
    public byte getNextByte() {
        return (byte)random.nextInt();
    }

    @Override
    public void setSettings(MutatorSettings mutatorSettings) {
        this.settings = mutatorSettings;
    }

    @Override
    public boolean skipNextInstruction() {
        return random.nextDouble() < settings.getSkipInstructionProbability();
    }
}
