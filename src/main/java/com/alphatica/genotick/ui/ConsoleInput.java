package com.alphatica.genotick.ui;

import com.alphatica.genotick.data.MainAppData;
import com.alphatica.genotick.genotick.Main;
import com.alphatica.genotick.genotick.MainSettings;
import com.alphatica.genotick.timepoint.TimePoint;

import java.io.Console;

@SuppressWarnings("unused")
class ConsoleInput extends BasicUserInput {
    private final Console console;

    ConsoleInput() {
        console = null;//System.console();
        if(console == null) {
            throw new RuntimeException("Unable to create system console");
        }
    }

    @Override
    public MainSettings getSettings() {
        String dataDirectory = getString("Data directory", "data");
        MainAppData data = getData(dataDirectory);
        MainSettings settings = MainSettings.getSettings();
        settings.dataSettings = dataDirectory;
        settings.performTraining = getBoolean("Perform training", settings.performTraining);
        settings.startTimePoint = new TimePoint(getLong("Start time point",data.getFirstTimePoint().getValue()));
        settings.endTimePoint = new TimePoint(getLong("End time point", data.getLastTimePoint().getValue()));
        settings.populationDAO = getString("Population storage", settings.populationDAO);
        settings.processorInstructionLimit = getInteger("Processor instruction limit", settings.processorInstructionLimit);
        settings.resultThreshold = getDouble("Result threshold",settings.resultThreshold);
        settings.requireSymmetricalRobots = getBoolean("Require symmetrical robots", settings.requireSymmetricalRobots);
        if (settings.performTraining) {
            settings.dataMaximumOffset = getInteger("Data maximum offset", settings.dataMaximumOffset);
            settings.populationDesiredSize = getInteger("Population desired size", settings.populationDesiredSize);
            settings.maximumDeathByAge = getDouble("Maximum death rate by age", settings.maximumDeathByAge);
            settings.maximumDeathByWeight = getDouble("Maximum death rate by weight", settings.maximumDeathByWeight);
            settings.probabilityOfDeathByAge = getDouble("Probability of death by age", settings.probabilityOfDeathByAge);
            settings.probabilityOfDeathByWeight = getDouble("Probability of death by weight", settings.probabilityOfDeathByWeight);
            settings.inheritedChildWeight = getDouble("Inherited child's weight", settings.inheritedChildWeight);
            settings.protectRobotsUntilOutcomes = getInteger("Protect robots until outcomes", settings.protectRobotsUntilOutcomes);
            settings.newInstructionProbability = getDouble("Probability of new instruction", settings.newInstructionProbability);
            // This looks like an error but it's not. Default value for 'skipInstruction...' is same as 'newInstruction'
            // to keep robots more or less constant size.
            settings.skipInstructionProbability = getDouble("Probability of skipping instruction", settings.newInstructionProbability);
            settings.instructionMutationProbability = getDouble("Instruction mutation probability", settings.instructionMutationProbability);
            settings.minimumOutcomesToAllowBreeding = getLong("Minimum outcomes to allow breeding", settings.minimumOutcomesToAllowBreeding);
            settings.minimumOutcomesBetweenBreeding = getLong("Minimum outcomes between breeding", settings.minimumOutcomesBetweenBreeding);
            settings.killNonPredictingRobots = getBoolean("Kill non-predicting robots", settings.killNonPredictingRobots);
            settings.randomRobotsAtEachUpdate = getDouble("Random robots at each update", settings.randomRobotsAtEachUpdate);
            settings.protectBestRobots = getDouble("Protect best robots", settings.protectBestRobots);
            settings.ignoreColumns = getInteger("Ignore columns for training",settings.ignoreColumns);
        }
        return settings;
    }

    private double getDouble(String s, double value) {
        String str = String.valueOf(value);
        displayMessage(s,str);
        String response = getString(null,str);
        return Double.parseDouble(response);
    }

    private int getInteger(String message, int def) {
        String str = String.valueOf(def);
        displayMessage(message,str);
        String response = getString(null,str);
        return Integer.parseInt(response);
    }

    private boolean getBoolean(String message, boolean def) {
        String str = String.valueOf(def);
        displayMessage(message, str);
        String response = getString(null,str);
        return Boolean.valueOf(response);
    }

    private long getLong(String message, long def) {
        String str = String.valueOf(def);
        displayMessage(message,str);
        String response = getString(null,str);
        return Long.parseLong(response);
    }

    private String getString(String message, String def) {
        displayMessage(message, def);
        String response = console.readLine();
        if(response.equals(""))
            return def;
        else
            return response;
    }

    private void displayMessage(String message, String def) {
        if(message != null)
            System.out.print(String.format("%s [%s]: ",message,def));
    }
}
