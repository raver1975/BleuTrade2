package com.alphatica.genotick.genotick;

import com.alphatica.genotick.data.*;
import com.alphatica.genotick.population.Population;
import com.alphatica.genotick.population.PopulationDAOFileSystem;
import com.alphatica.genotick.population.Robot;
import com.alphatica.genotick.population.RobotInfo;
import com.alphatica.genotick.reversal.Reversal;
import com.alphatica.genotick.ui.Parameters;
import com.alphatica.genotick.ui.UserInput;
import com.alphatica.genotick.ui.UserInputOutputFactory;
import com.alphatica.genotick.ui.UserOutput;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class MainModified {
    public static final String DEFAULT_DATA_DIR = "data";
    private static final String VERSION = "Genotick version 0.11-SNAPSHOT (copyleft 2017)";
    private static UserInput input;
    private static UserOutput output;

    public static void main(String... args) throws IOException, IllegalAccessException {
        Parameters parameters = new Parameters(args);
        checkHelp(parameters);
        checkVersionRequest(parameters);
        checkShowPopulation(parameters);
        checkShowRobot(parameters);
        getUserIO(parameters);
        setupExceptionHandler();
        checkReverse(parameters);
        checkYahoo(parameters);
        checkSimulation(parameters);
    }


    private static void checkShowRobot(Parameters parameters) {
        String value = parameters.getValue("showRobot");
        if (value != null) {
            try {
                showRobot(value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                output.errorMessage(e.getMessage());
            }
            System.exit(0);
        }
    }

    private static void showRobot(String value) throws IllegalAccessException {
        String robotString = getRobotString(value);
        System.out.println(robotString);
    }

    private static String getRobotString(String path) throws IllegalAccessException {
        File file = new File(path);
        Robot robot = PopulationDAOFileSystem.getRobotFromFile(file);
        return robot.showRobot();
    }

    private static void checkShowPopulation(Parameters parameters) {
        String value = parameters.getValue("showPopulation");
        if (value != null) {
            try {
                showPopulation(value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                output.errorMessage(e.getMessage());
            }
            System.exit(0);
        }
    }

    private static void showPopulation(String path) throws IllegalAccessException {
        PopulationDAOFileSystem dao = new PopulationDAOFileSystem(path);
        Population population = PopulationFactory.getDefaultPopulation(dao);
        showHeader();
        showRobots(population);
    }

    private static void showRobots(Population population) throws IllegalAccessException {
        for (RobotInfo robotInfo : population.getRobotInfoList()) {
            String info = getRobotInfoString(robotInfo);
            System.out.println(info);
        }
    }

    private static String getRobotInfoString(RobotInfo robotInfo) throws IllegalAccessException {
        StringBuilder sb = new StringBuilder();
        Field[] fields = robotInfo.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (!Modifier.isStatic(field.getModifiers())) {
                Object object = field.get(robotInfo);
                if (sb.length() > 0) {
                    sb.append(",");
                }
                sb.append(object.toString());
            }
        }
        return sb.toString();
    }

    private static void showHeader() {
        Class infoClass = RobotInfo.class;
        List<Field> fields = buildFields(infoClass);
        String line = buildLine(fields);
        System.out.println(line);
    }

    private static List<Field> buildFields(Class infoClass) {
        List<Field> fields = new ArrayList<>();
        for (Field field : infoClass.getDeclaredFields()) {
            if (!Modifier.isStatic(field.getModifiers())) {
                fields.add(field);
            }
        }
        return fields;
    }

    private static String buildLine(List<Field> fields) {
        StringBuilder sb = new StringBuilder();
        for (Field field : fields) {
            if (sb.length() > 0) {
                sb.append(",");
            }
            sb.append(field.getName());
        }
        return sb.toString();
    }

    private static void setupExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler(output.createExceptionHandler());
    }

    private static void checkVersionRequest(Parameters parameters) {
        if (parameters.getValue("showVersion") != null) {
            System.out.println(MainModified.VERSION);
            System.exit(0);
        }
    }

    private static void checkHelp(Parameters parameters) {
        if (parameters.getValue("help") != null
                || parameters.getValue("--help") != null
                || parameters.getValue("-h") != null) {
            System.out.print("Displaying version: ");
            System.out.println("	java -jar Genotick-version.jar showVersion");
            System.out.print("Reversing data: ");
            System.out.println("	java -jar Genotick-version.jar reverse=mydata");
            System.out.print("Inputs from a file: ");
            System.out.println("	java -jar Genotick-version.jar input=file:path\\to\\file");
            System.out.print("Output to a file: ");
            System.out.println("	java -jar Genotick-version.jar output=csv");
            System.out.print("Output to a file: ");
            System.out.println("	java -jar Genotick-version.jar output=csv");
            System.out.print("show population: ");
            System.out.println("	java -jar Genotick.jar showPopulation=directory_with_population");
            System.out.print("show robot info: ");
            System.out.println("	java -jar Genotick.jar showRobot=directory_with_population\\system name.prg");
            System.out.println("contact: 		lukasz.wojtow@gmail.com");
            System.out.println("more info: 		genotick.com");

            System.exit(0);
        }
    }

    private static void checkYahoo(Parameters parameters) {
        String yahooValue = parameters.getValue("fixYahoo");
        if (yahooValue == null) {
            return;
        }
        YahooFixer yahooFixer = new YahooFixer(yahooValue);
        yahooFixer.fixFiles();
        System.exit(0);
    }

    private static void getUserIO(Parameters parameters) throws IOException {
        input = UserInputOutputFactory.getUserInput(parameters);
        if (input == null) {
            exit(errorCodes.NO_INPUT);
        }
        output = UserInputOutputFactory.getUserOutput(parameters);
        //noinspection ConstantConditions
        if (output == null) {
            exit(errorCodes.NO_OUTPUT);
        }
    }

    private static void checkReverse(Parameters parameters) {
//        String reverseValue = parameters.getValue("reverse");
//        if(reverseValue == null)
//            return;
        File f = new File("data");
        for (File ff : f.listFiles()) {
            if (ff.getAbsolutePath().contains("reverse_")) ff.delete();
        }
        Reversal reversal = new Reversal("data");
        reversal.reverse();
//        System.exit(0);
    }

    private static void checkSimulation(Parameters parameters) throws IllegalAccessException {
        if (!parameters.allConsumed()) {
            output.errorMessage("Not all arguments processed: " + parameters.getUnconsumed());
            exit(errorCodes.UNKNOWN_ARGUMENT);
        }
        Simulation simulation = new Simulation();
        input.setSimulation(simulation);
        MainSettings settings = input.getSettings();
        MainAppData data = input.getData(settings.dataSettings);
        settings.validateTimePoints(data);
        simulation.start(settings, data);
    }


    private static void exit(errorCodes code) {
        System.exit(code.getValue());
    }
}

