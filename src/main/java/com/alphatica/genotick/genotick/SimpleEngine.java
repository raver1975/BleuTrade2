package com.alphatica.genotick.genotick;

import com.alphatica.genotick.breeder.RobotBreeder;
import com.alphatica.genotick.data.DataSetName;
import com.alphatica.genotick.data.MainAppData;
import com.alphatica.genotick.killer.RobotKiller;
import com.alphatica.genotick.population.Population;
import com.alphatica.genotick.population.RobotInfo;
import com.alphatica.genotick.timepoint.TimePoint;
import com.alphatica.genotick.timepoint.TimePointExecutor;
import com.alphatica.genotick.timepoint.TimePointResult;
import com.alphatica.genotick.timepoint.TimePointStats;
import com.alphatica.genotick.ui.UserInputOutputFactory;
import com.alphatica.genotick.ui.UserOutput;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class SimpleEngine implements Engine {
    private EngineSettings engineSettings;
    private TimePointExecutor timePointExecutor;
    private RobotKiller killer;
    private RobotBreeder breeder;
    private Population population;
    private MainAppData data;
    private final ProfitRecorder profitRecorder;
    private final UserOutput output = UserInputOutputFactory.getUserOutput();

    private SimpleEngine() {
        profitRecorder = new ProfitRecorder();
    }

    public static Engine getEngine() {
        return new SimpleEngine();
    }

    @Override
    public List<TimePointStats> start() {
        Thread.currentThread().setName("Main engine execution thread");
        double result = 1;
        initPopulation();
        TimePoint timePoint = new TimePoint(engineSettings.startTimePoint);
        List<TimePointStats> timePointStats = new ArrayList<>();
        while (engineSettings.endTimePoint.compareTo(timePoint) >= 0) {
            TimePointStats stat = executeTimePoint(timePoint);
            if (stat != null) {
                timePointStats.add(stat);
                result *= (stat.getPercentEarned() / 100 + 1);
                profitRecorder.recordProfit(stat);
                output.reportProfitForTimePoint(timePoint, (result - 1) * 100, stat.getPercentEarned());
            }
            timePoint = data.getNextTimePint(timePoint);
            if(timePoint == null) {
                break;
            }
        }
        if (engineSettings.performTraining) {
            savePopulation(output);
        }
        showSummary(output);
        return timePointStats;
    }

    private void showSummary(UserOutput output) {
        profitRecorder.showPercentCorrectPredictions(data.listSets());
        output.infoMessage("Total: Profit: " + profitRecorder.getProfit() + " Drawdown: " + profitRecorder.getMaxDrawdown()
                + " Profit / DD: " + profitRecorder.getProfit() / profitRecorder.getMaxDrawdown());
        output.infoMessage("Second Half: Profit: " + profitRecorder.getProfitSecondHalf() + " Drawdown: " + profitRecorder.getMaxDrawdownSecondHalf()
                + " Profit / DD: " + profitRecorder.getProfitSecondHalf() / profitRecorder.getMaxDrawdownSecondHalf());
    }

    private void savePopulation(UserOutput output) {
        String dirName = getSavedPopulationDirName();
        File dirFile = new File(dirName);
        if (!dirFile.exists() && !dirFile.mkdirs()) {
            output.errorMessage("Unable to create directory " + dirName);
        } else {
            population.savePopulation(dirName);
        }
    }

    @Override
    public void setSettings(EngineSettings engineSettings,
                            TimePointExecutor timePointExecutor,
                            MainAppData data,
                            RobotKiller killer,
                            RobotBreeder breeder,
                            Population population) {
        this.engineSettings = engineSettings;
        this.timePointExecutor = timePointExecutor;
        this.killer = killer;
        this.breeder = breeder;
        this.population = population;
        this.data = data;
    }

    private String getSavedPopulationDirName() {
        return "savedPopulation_" + Tools.getPidString();
    }

    private void initPopulation() {
        if (population.getSize() == 0 && engineSettings.performTraining)
            breeder.breedPopulation(population, timePointExecutor.getRobotInfos());
    }

    private TimePointStats executeTimePoint(TimePoint timePoint) {
        List<RobotData> robotDataList = data.prepareRobotDataList(timePoint);
        if (robotDataList.isEmpty())
            return null;
        TimePointResult timePointResult = timePointExecutor.execute(robotDataList, population, engineSettings.performTraining, engineSettings.requireSymmetrical);
        TimePointStats timePointStats = TimePointStats.getNewStats(timePoint);
        for (DataSetResult dataSetResult : timePointResult.listDataSetResults()) {
            Prediction prediction = dataSetResult.getCumulativePrediction(engineSettings.resultThreshold);
            output.showPrediction(timePoint, dataSetResult.getName(), prediction);
            tryUpdate(dataSetResult, timePoint, prediction, timePointStats);
        }
        if (engineSettings.performTraining) {
            updatePopulation();
            showAverageRobotWeight();
        }
        return timePointStats;
    }

    private void showAverageRobotWeight() {
        output.infoMessage("Average weight: " + String.valueOf(population.getAverageWeight()));
    }

    private void tryUpdate(DataSetResult dataSetResult, TimePoint timePoint, Prediction prediction, TimePointStats timePointStats) {
        Double actualChange = data.getActualChange(dataSetResult.getName(), timePoint);
        if (!actualChange.isNaN()) {
            double totalProfit = data.recordProfit(dataSetResult.getName(), prediction.toProfit(actualChange));
            output.showCumulativeProfit(timePoint, dataSetResult.getName(), totalProfit);
            printPercentEarned(timePoint, dataSetResult.getName(), actualChange, prediction);
            if (!actualChange.isInfinite() && !actualChange.isNaN()) {
                timePointStats.update(dataSetResult.getName(), actualChange, prediction);
            }
        }
    }

    private void printPercentEarned(TimePoint timepoint, DataSetName name, double actualChange, Prediction prediction) {
        double percent;
        if (prediction == Prediction.OUT) {
            return;
        }
        if (prediction.isCorrect(actualChange))
            percent = Math.abs(actualChange);
        else
            percent = -Math.abs(actualChange);
        output.infoMessage(timepoint.getValue() + " Profit for " + name + " " + percent);
    }

    private void updatePopulation() {
        List<RobotInfo> list = timePointExecutor.getRobotInfos();
        killer.killRobots(population,list);
        breeder.breedPopulation(population,list);
        output.debugMessage("averageAge=" + population.getAverageAge());
    }
}
