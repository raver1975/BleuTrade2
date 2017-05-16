package com.alphatica.genotick.instructions;

import com.alphatica.genotick.genotick.RandomGenerator;
import com.alphatica.genotick.ui.UserOutput;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class InstructionList implements Serializable {
    @SuppressWarnings("unused")
    private static final long serialVersionUID = 267402795981161615L;

    private final Random random;
    private final List<Instruction> list;
    private final int variablesCount;
    private final double[] variables;

    private int validateVariableNumber(int index) {
        return Math.abs(index % variablesCount);
    }

    private InstructionList() {
        random = RandomGenerator.assignRandom();
        list = new ArrayList<>();
        variablesCount = 1 + Math.abs(random.nextInt() % 1024);
        variables = new double[variablesCount];
    }

    public static InstructionList createInstructionList() {
        return new InstructionList();
    }

    public Instruction getInstruction(int index) {
        if (index < 0 || index >= list.size())
            return new TerminateInstructionList();
        else
            return list.get(index);
    }

    public double getVariable(int index) {
        return variables[validateVariableNumber(index)];
    }

    public void setVariable(int index, double value) {
        variables[validateVariableNumber(index)] = value;
    }

    public void zeroOutVariables() {
        for(int i = 0; i < variables.length; i++)
            variables[i] = 0;
    }

    public void addInstruction(Instruction instruction) {
        list.add(instruction);
    }
    public int getSize() {
        return list.size();
    }

    @SuppressWarnings("unused")
    public void addInstructionAtPosition(Instruction instruction, int position) {
        position = fixPosition(position);
        list.add(position,instruction);
    }

    private int fixPosition(int position) {
        if(position >= 0 && position < list.size())
            return position;
        else
            return random.nextInt(list.size());
    }

    public int getVariablesCount() {
        return variablesCount;
    }
}
