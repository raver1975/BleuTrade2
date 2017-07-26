package com.alphatica.genotick.timepoint;

import com.alphatica.genotick.genotick.*;
import com.alphatica.genotick.population.*;
import com.alphatica.genotick.processor.RobotExecutorFactory;
import com.alphatica.genotick.ui.UserOutput;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

class SimpleTimePointExecutor implements TimePointExecutor {

    private final ExecutorService executorService;
    private final List<RobotInfo> robotInfos;
    private final UserOutput output;
    private DataSetExecutor dataSetExecutor;
    private RobotExecutorFactory robotExecutorFactory;


    public SimpleTimePointExecutor(UserOutput output) {
        int cores = Runtime.getRuntime().availableProcessors();
        executorService = Executors.newFixedThreadPool(cores * 2, new DaemonThreadFactory());
        robotInfos = Collections.synchronizedList(new ArrayList<RobotInfo>());
        this.output = output;
    }

    public List<RobotInfo> getRobotInfos() {
        return robotInfos;
    }

    @Override
    public TimePointResult execute(List<RobotData> robotDataList,
                                   Population population, boolean updateRobots, boolean requireSymmetrical) {
        robotInfos.clear();
        TimePointResult timePointResult = new TimePointResult();
        if(robotDataList.isEmpty())
            return timePointResult;
        List<Future<List<RobotResult>>> tasks = submitTasks(robotDataList,population,updateRobots);
        getResults(timePointResult,tasks, requireSymmetrical);
        return timePointResult;
    }

    private void getResults(TimePointResult timePointResult, List<Future<List<RobotResult>>> tasks, boolean requireSymmetrical) {
        while(!tasks.isEmpty()) {
            try {
                int lastIndex = tasks.size() - 1;
                Future<List<RobotResult>> future = tasks.get(lastIndex);
                List<RobotResult> results = future.get();
                tasks.remove(lastIndex);
                if(!requireSymmetrical || resultsSymmetrical(results)) {
                    results.forEach(timePointResult::addRobotResult);
                }
            } catch (InterruptedException ignore) {
                /* Do nothing, try again */
            } catch (ExecutionException e) {
                output.errorMessage(e.getMessage());
                e.printStackTrace();
                System.exit(1);
            }
        }
    }

    private boolean resultsSymmetrical(List<RobotResult> results) {
        long votesUp = results.stream().filter(result -> result.getPrediction() == Prediction.UP).count();
        long votesDown = results.stream().filter(result -> result.getPrediction() == Prediction.DOWN).count();
        return votesUp == votesDown;
    }


    @Override
    public void setSettings(DataSetExecutor dataSetExecutor, RobotExecutorFactory robotExecutorFactory) {
        this.dataSetExecutor = dataSetExecutor;
        this.robotExecutorFactory = robotExecutorFactory;
    }

    /*
       Return type here is as ugly as it gets and I'm not proud. However, it seems to be the quickest.
       */
    private List<Future<List<RobotResult>>> submitTasks(List<RobotData> robotDataList,
                                                        Population population,
                                                        boolean updateRobots) {
        List<Future<List<RobotResult>>> tasks = new ArrayList<>();
        for(RobotName robotName : population.listRobotsNames()) {
            Task task = new Task(robotName, robotDataList, population, updateRobots);
            Future<List<RobotResult>> future = executorService.submit(task);
            tasks.add(future);
        }
        return tasks;
    }

    private class Task implements Callable<List<RobotResult>> {

        private final RobotName robotName;
        private final List<RobotData> robotDataList;
        private final Population population;
        private final boolean updateRobots;

        public Task(RobotName robotName, List<RobotData> robotDataList, Population population, boolean updateRobots) {
            this.robotName = robotName;
            this.robotDataList = robotDataList;
            this.population = population;
            this.updateRobots = updateRobots;
        }

        @Override
        public List<RobotResult> call() throws Exception {
            RobotExecutor robotExecutor = robotExecutorFactory.getDefaultRobotExecutor();
            Robot robot = population.getRobot(robotName);
            List<RobotResult> list = dataSetExecutor.execute(robotDataList, robot, robotExecutor);
            if(updateRobots) {
                updateRobots(robot,list);
            }
            RobotInfo robotInfo = new RobotInfo(robot);
            robotInfos.add(robotInfo);
            return list;
        }

        private void updateRobots(Robot robot, List<RobotResult> list) {
            List<Outcome> outcomes = new ArrayList<>();
            for(RobotResult result: list) {
                robot.recordPrediction(result.getPrediction());
                Outcome outcome = Outcome.getOutcome(result.getPrediction(),result.getData().getActualChange());
                outcomes.add(outcome);
            }
            robot.recordOutcomes(outcomes);
            population.saveRobot(robot);
        }
    }
}

