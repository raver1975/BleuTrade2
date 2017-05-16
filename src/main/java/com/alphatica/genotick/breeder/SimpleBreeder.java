package com.alphatica.genotick.breeder;

import com.alphatica.genotick.instructions.Instruction;
import com.alphatica.genotick.instructions.InstructionList;
import com.alphatica.genotick.instructions.TerminateInstructionList;
import com.alphatica.genotick.mutator.Mutator;
import com.alphatica.genotick.population.Population;
import com.alphatica.genotick.population.Robot;
import com.alphatica.genotick.population.RobotInfo;
import com.alphatica.genotick.ui.UserInputOutputFactory;
import com.alphatica.genotick.ui.UserOutput;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SimpleBreeder implements RobotBreeder {
    private BreederSettings settings;
    private Mutator mutator;
    private final UserOutput output = UserInputOutputFactory.getUserOutput();

    public static RobotBreeder getInstance() {
        return new SimpleBreeder();
    }

    @Override
    public void breedPopulation(Population population, List<RobotInfo> robotInfos) {
        if (population.haveSpaceToBreed()) {
            int before = population.getSize(), after;
            addRequiredRandomRobots(population);
            after = population.getSize();
            output.debugMessage("requiredRandomRobots=" + (after - before));
            before = after;
            breedPopulationFromParents(population, robotInfos);
            after = population.getSize();
            output.debugMessage("breededRobots=" + (after - before));
            before = after;
            addOptionalRandomRobots(population);
            after = population.getSize();
            output.debugMessage("optionalRandomRobots=" + (after - before));
            output.debugMessage("totalRobots=" + after);
        }
    }

    private void addOptionalRandomRobots(Population population) {
        int count = population.getDesiredSize() - population.getSize();
        if (count > 0) {
            fillWithRobots(count, population);
        }
    }

    private void addRequiredRandomRobots(Population population) {
        if (settings.randomRobots > 0) {
            int count = (int) Math.round(settings.randomRobots * population.getDesiredSize());
            fillWithRobots(count, population);
        }
    }

    private void fillWithRobots(int count, Population population) {
        for (int i = 0; i < count; i++) {
            createNewRobot(population);
        }
    }

    private void createNewRobot(Population population) {
        Robot robot = Robot.createEmptyRobot(settings.dataMaximumOffset, settings.ignoreColumns);
        int instructionsCount = Math.abs(mutator.getNextInt() % 1024);
        InstructionList main = robot.getMainFunction();
        while (instructionsCount-- > 0) {
            addInstructionToMain(main);
        }
        population.saveRobot(robot);
    }

    private void addInstructionToMain(InstructionList main) {
        Instruction instruction = mutator.getRandomInstruction();
        instruction.mutate(mutator);
        main.addInstruction(instruction);
    }

    private void breedPopulationFromParents(Population population, List<RobotInfo> originalList) {
        List<RobotInfo> robotInfos = new ArrayList<>(originalList);
        removeNotAllowedRobots(robotInfos);
        breedPopulationFromList(population, robotInfos);
    }

    private void removeNotAllowedRobots(List<RobotInfo> robotInfos) {
        Iterator<RobotInfo> iterator = robotInfos.iterator();
        while (iterator.hasNext()) {
            RobotInfo robotInfo = iterator.next();
            if (!robotInfo.canBeParent(settings.minimumOutcomesToAllowBreeding, settings.outcomesBetweenBreeding))
                iterator.remove();
        }
    }

    private void breedPopulationFromList(Population population, List<RobotInfo> list) {
        while (population.haveSpaceToBreed()) {
            Robot parent1 = getPossibleParent(population, list);
            Robot parent2 = getPossibleParent(population, list);
            if (parent1 == null || parent2 == null)
                break;
            Robot child = Robot.createEmptyRobot(settings.dataMaximumOffset, settings.ignoreColumns);
            makeChild(parent1, parent2, child);
            population.saveRobot(child);
            parent1.increaseChildren();
            population.saveRobot(parent1);
            parent2.increaseChildren();
            population.saveRobot(parent2);
        }
    }

    private void makeChild(Robot parent1, Robot parent2, Robot child) {
        double weight = getParentsWeight(parent1, parent2);
        child.setInheritedWeight(weight);
        InstructionList instructionList = mixMainInstructionLists(parent1, parent2);
        child.setMainInstructionList(instructionList);
    }

    private double getParentsWeight(Robot parent1, Robot parent2) {
        return settings.inheritedWeightPercent * (parent1.getWeight() + parent2.getWeight()) / 2;
    }

    private InstructionList mixMainInstructionLists(Robot parent1, Robot parent2) {
        InstructionList source1 = parent1.getMainFunction();
        InstructionList source2 = parent2.getMainFunction();
        return blendInstructionLists(source1, source2);
    }

    /*
    This potentially will make robots gradually shorter.
    Let's say that list1.size == 4 and list2.size == 2. Average length is 3.
    Then, break1 will be between <0,3> and break2 <0,1>
    All possible lengths for new InstructionList will be: 0,1,2,3,1,2,3,4 with equal probability.
    Average length is 2.
    For higher numbers this change isn't so dramatic but may add up after many populations.
     */
    private InstructionList blendInstructionLists(InstructionList list1, InstructionList list2) {
        InstructionList instructionList = InstructionList.createInstructionList();
        int break1 = getBreakPoint(list1);
        int break2 = getBreakPoint(list2);
        copyBlock(instructionList, list1, 0, break1);
        copyBlock(instructionList, list2, break2, list2.getSize());
        return instructionList;
    }

    private int getBreakPoint(InstructionList list) {
        int size = list.getSize();
        if (size == 0)
            return 0;
        else
            return Math.abs(mutator.getNextInt() % size);
    }

    private void copyBlock(InstructionList destination, InstructionList source, int start, int stop) {
        assert start <= stop : "start > stop " + String.format("%d %d", start, stop);
        for (int i = start; i <= stop; i++) {
            Instruction instruction = source.getInstruction(i).copy();
            if(instruction instanceof TerminateInstructionList) {
                break;
            }
            addInstructionToInstructionList(instruction, destination);
        }
    }

    private void addInstructionToInstructionList(Instruction instruction, InstructionList instructionList) {
        if (mutator.skipNextInstruction()) {
            return;
        }
        possiblyAddNewInstruction(instructionList);
        possiblyMutateInstruction(instruction);
        instructionList.addInstruction(instruction);
    }

    private void possiblyMutateInstruction(Instruction instruction) {
        if (mutator.getAllowInstructionMutation()) {
            instruction.mutate(mutator);
        }
    }

    private void possiblyAddNewInstruction(InstructionList instructionList) {
        if (mutator.getAllowNewInstruction()) {
            Instruction newInstruction = mutator.getRandomInstruction();
            instructionList.addInstruction(newInstruction);
        }
    }

    private Robot getPossibleParent(Population population, List<RobotInfo> list) {
        double totalWeight = sumTotalWeight(list);
        double target = Math.abs(totalWeight * mutator.getNextDouble());
        double weightSoFar = 0;
        Iterator<RobotInfo> iterator = list.iterator();
        while (iterator.hasNext()) {
            RobotInfo robotInfo = iterator.next();
            weightSoFar += Math.abs(robotInfo.getWeight());
            if (weightSoFar >= target) {
                iterator.remove();
                return population.getRobot(robotInfo.getName());
            }
        }
        return null;
    }

    private double sumTotalWeight(List<RobotInfo> list) {
        double weight = 0;
        for (RobotInfo robotInfo : list) {
            weight += Math.abs(robotInfo.getWeight());
        }
        return weight;
    }

    @Override
    public void setSettings(BreederSettings breederSettings, Mutator mutator) {
        this.settings = breederSettings;
        this.mutator = mutator;
    }
}
