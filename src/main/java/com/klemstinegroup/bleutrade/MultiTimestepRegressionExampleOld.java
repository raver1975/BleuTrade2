package com.klemstinegroup.bleutrade;

import org.apache.commons.io.FileUtils;
import org.datavec.api.records.reader.SequenceRecordReader;
import org.datavec.api.records.reader.impl.csv.CSVSequenceRecordReader;
import org.datavec.api.split.NumberedFileInputSplit;
import org.deeplearning4j.datasets.datavec.SequenceRecordReaderDataSetIterator;
import org.deeplearning4j.eval.RegressionEvaluation;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.Updater;
import org.deeplearning4j.nn.conf.layers.GravesLSTM;
import org.deeplearning4j.nn.conf.layers.RnnOutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.dataset.api.preprocessor.NormalizerMinMaxScaler;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.lossfunctions.LossFunctions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;


/**
 * This example was inspired by Jason Brownlee's regression examples for Keras, found here:
 * http://machinelearningmastery.com/time-series-prediction-lstm-recurrent-neural-networks-python-keras/
 * <p>
 * It demonstrates multi time step regression using LSTM
 */

public class MultiTimestepRegressionExampleOld {
    private static final Logger LOGGER = LoggerFactory.getLogger(MultiTimestepRegressionExampleOld.class);
    private int trainSize = 10;
    private int testSize = 100;
    private int numberOfTimesteps = 50;
    int miniBatchSize = 50;
    int nEpochs = 5;
    private List<String> rawStrings;
    private static int cnter = 0;
    //Make sure miniBatchSize is divisable by trainSize and testSize,
    //as rnnTimeStep will not accept different sized examples


    private static File initBaseFile(String fileName) {
        try {
            return new File(fileName);
            //jar
//            return new ClassPathResource(fileName).getFile();
        } catch (Exception e) {
            throw new Error(e);
        }
    }


    private static File baseDir = initBaseFile("./rnnRegression");
    private static File baseTrainDir = new File(baseDir, "multiTimestepTrain");
    private static File featuresDirTrain = new File(baseTrainDir, "features");
    private static File labelsDirTrain = new File(baseTrainDir, "labels");
    private static File baseTestDir = new File(baseDir, "multiTimestepTest");
    private static File featuresDirTest = new File(baseTestDir, "features");
    private static File labelsDirTest = new File(baseTestDir, "labels");

    private static int numOfVariables = 0;  // in csv.

    public MultiTimestepRegressionExampleOld() throws Exception {
        System.out.println(new File(".").toPath().toAbsolutePath().toString());
        //Set number of examples for training, testing, and time steps


        //Prepare multi time step data, see method comments for more info
        rawStrings = prepareTrainAndTest();


        // ----- Load the training data -----
        SequenceRecordReader trainFeatures = new CSVSequenceRecordReader();
        trainFeatures.initialize(new NumberedFileInputSplit(featuresDirTrain.getAbsolutePath() + "/train_%d.csv", 0, trainSize +miniBatchSize- 1));
        SequenceRecordReader trainLabels = new CSVSequenceRecordReader();
        trainLabels.initialize(new NumberedFileInputSplit(labelsDirTrain.getAbsolutePath() + "/train_%d.csv", 0, trainSize +miniBatchSize- 1));

        DataSetIterator trainDataIter = new SequenceRecordReaderDataSetIterator(trainFeatures, trainLabels, miniBatchSize, -1, true, SequenceRecordReaderDataSetIterator.AlignmentMode.ALIGN_END);

        //Normalize the training data
        NormalizerMinMaxScaler normalizer = new NormalizerMinMaxScaler(0, 1);
        normalizer.fitLabel(true);
        normalizer.fit(trainDataIter);              //Collect training data statistics
        trainDataIter.reset();


        // ----- Load the test data -----
        //Same process as for the training data.
        SequenceRecordReader testFeatures = new CSVSequenceRecordReader();
        testFeatures.initialize(new NumberedFileInputSplit(featuresDirTest.getAbsolutePath() + "/test_%d.csv", trainSize, trainSize + testSize - 1));
        SequenceRecordReader testLabels = new CSVSequenceRecordReader();
        testLabels.initialize(new NumberedFileInputSplit(labelsDirTest.getAbsolutePath() + "/test_%d.csv", trainSize, trainSize + testSize - 1));

        DataSetIterator testDataIter = new SequenceRecordReaderDataSetIterator(testFeatures, testLabels, miniBatchSize, -1, true, SequenceRecordReaderDataSetIterator.AlignmentMode.ALIGN_END);

        trainDataIter.setPreProcessor(normalizer);
        testDataIter.setPreProcessor(normalizer);
//Create data set from iterator here since we only have a single data set
        DataSet trainData = trainDataIter.next();
        DataSet testData = testDataIter.next();


        // ----- Configure the network -----
        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
                .seed(140)
                .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
                .iterations(1)
                .weightInit(WeightInit.XAVIER)
                .updater(Updater.NESTEROVS).momentum(0.9)
                .learningRate(0.15)
                .list()
                .layer(0, new GravesLSTM.Builder().activation(Activation.TANH).nIn(numOfVariables).nOut(10)
                        .build())
                .layer(1, new RnnOutputLayer.Builder(LossFunctions.LossFunction.MSE)
                        .activation(Activation.IDENTITY).nIn(10).nOut(numOfVariables).build())
                .build();

        MultiLayerNetwork net = new MultiLayerNetwork(conf);
        net.init();
//        net.setListeners(new GradientsAndParamsListener(net,100),new ScoreIterationListener(20));

        // ----- Train the network, evaluating the test set performance at each epoch -----
//        int nEpochs = 5;

        for (int i = 0; i < nEpochs; i++) {
            net.fit(trainData);
            LOGGER.info("Epoch " + i + " complete. Time series evaluation:");

            RegressionEvaluation evaluation = new RegressionEvaluation(2);

            //Run evaluation. This is on 25k reviews, so can take some time
            INDArray features = trainData.getFeatureMatrix();
            INDArray lables = trainData.getLabels();
            INDArray predicted = net.output(features, true);
            evaluation.evalTimeSeries(lables, predicted);
            System.out.println(evaluation.stats());

        }
        INDArray predicted = net.rnnTimeStep(testData.getFeatureMatrix());
//        INDArray predicted1 = net.rnnTimeStep(predicted);
//        INDArray predicted2 = net.rnnTimeStep(predicted1);

        normalizer.revertLabels(predicted);
//        normalizer.revertLabels(predicted1);
//        normalizer.revertLabels(predicted2);


        ArrayList<Double> ask = new ArrayList<>();
        ArrayList<Double> bid = new ArrayList<>();
        ArrayList<AskBid> askbid = new ArrayList<>();
        trainDataIter.reset();
        for (String s : rawStrings) {
            System.out.println(s);
            String[] gg = s.split(",");
            ask.add(Double.parseDouble(gg[1]));
            bid.add(Double.parseDouble(gg[0]));
            askbid.add(new AskBid(Double.parseDouble(gg[1]), Double.parseDouble(gg[0])));
        }
        System.out.println("predicted size=" + predicted.size(0));
        System.out.println(predicted);
        int nRows = predicted.shape()[2];
        for (int i = 0; i < nRows; i++) {
            askbid.add(new AskBid(predicted.slice(0).slice(1).getDouble(i), predicted.slice(0).slice(0).getDouble(i)));
        }

        int ducnt = 0;
        System.out.println("-----------------------------------------------------------------");
        for (AskBid ab : askbid) {
            System.out.println((ducnt++) + "\t" + ab);
        }


        //Convert raw string data to IndArrays for plotting
        INDArray trainArray = createIndArrayFromStringList(rawStrings, 0, trainSize);
        INDArray testArray = createIndArrayFromStringList(rawStrings, trainSize, testSize);
        INDArray fullArray = createIndArrayFromStringList(rawStrings, 0, trainSize + testSize+miniBatchSize);

        //Create plot with out data
        XYSeriesCollection c = new XYSeriesCollection();
        createSeries(c, fullArray, 0, "data");
//        createSeries(c, testArray, trainSize - 1, "Actual test data");
        createSeries(c, predicted, trainSize - 1, "Predicted data");
//        createSeries(c, predicted1, trainSize + testSize - 1
//                , "Predicted future data");
//        createSeries(c, predicted2, trainSize + 2 * testSize - 1
//                , "Predicted future data");

        plotDataset(c);

        LOGGER.info("----- Example Complete -----");
    }


    /**
     * Creates an IndArray from a list of strings
     * Used for plotting purposes
     */
    private INDArray createIndArrayFromStringList(List<String> rawStrings, int startIndex, int length) {
        List<String> stringList = rawStrings.subList(startIndex, startIndex + length);

        double[][] primitives = new double[numOfVariables][stringList.size()];
        for (int i = 0; i < stringList.size(); i++) {
            String[] vals = stringList.get(i).split(",");
            for (int j = 0; j < vals.length; j++) {
                primitives[j][i] = Double.valueOf(vals[j]);
            }
        }

        return Nd4j.create(new int[]{1, length}, primitives);
    }

    /**
     * Used to create the different time series for ploting purposes
     */
    private XYSeriesCollection createSeries(XYSeriesCollection seriesCollection, INDArray data, int offset, String name) {
        int nRows = data.shape()[2];
        boolean predicted = name.startsWith("Predicted");
        int repeat = predicted ? data.shape()[1] : data.shape()[0];

        for (int j = 0; j < repeat; j++) {
            XYSeries series = new XYSeries(name + j);
            for (int i = 0; i < nRows; i++) {
                if (predicted)
                    series.add(i + offset, data.slice(0).slice(j).getDouble(i));
                else
                    series.add(i + offset, data.slice(j).getDouble(i));
            }
            seriesCollection.addSeries(series);
        }

        return seriesCollection;
    }

    /**
     * Generate an xy plot of the datasets provided.
     */
    private void plotDataset(XYSeriesCollection c) {

        String title = "Regression example";
        String xAxisLabel = "Timestep";
        String yAxisLabel = "Coin Price";
        PlotOrientation orientation = PlotOrientation.VERTICAL;
        boolean legend = true;
        boolean tooltips = false;
        boolean urls = false;
        JFreeChart chart = ChartFactory.createXYLineChart(title, xAxisLabel, yAxisLabel, c, orientation, legend, tooltips, urls);

        // get a reference to the plot for further customisation...
        final XYPlot plot = chart.getXYPlot();
        plot.getDomainAxis().setAutoRange(true);
        plot.getDomainAxis().setRange(rawStrings.size() - 200, rawStrings.size()-50);

        // Auto zoom to fit time series in initial window`
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setAutoRange(true);
        rangeAxis.setAutoRangeIncludesZero(false);
        try {
            ChartUtilities.saveChartAsPNG(new File("./images/image" + (cnter++) + ".png"), chart, 1400, 800);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * This method shows how you based on a CSV file can preprocess your data the structure expected for a
     * multi time step problem. This examples uses a single column CSV as input, but the example should be easy to modify
     * for use with a multi column input as well.
     *
     * @return
     * @throws IOException
     */
    private List<String> prepareTrainAndTest() throws IOException {
        Path rawPath = Paths.get(baseDir.getAbsolutePath() + "/passengers_raw.csv");

        List<String> rawStrings = Files.readAllLines(rawPath, Charset.defaultCharset());
//        testSize=40;
//        numberOfTimesteps=40;
        trainSize = miniBatchSize * (int) ((rawStrings.size() - testSize - numberOfTimesteps) / miniBatchSize);
        int cgg = rawStrings.size() - trainSize - numberOfTimesteps - testSize;
        while(cgg-->0)rawStrings.remove(0);
        setNumOfVariables(rawStrings);

        //Remove all files before generating new ones
        FileUtils.cleanDirectory(featuresDirTrain);
        FileUtils.cleanDirectory(labelsDirTrain);
        FileUtils.cleanDirectory(featuresDirTest);
        FileUtils.cleanDirectory(labelsDirTest);

        for (int i = 0; i < trainSize; i++) {
            Path featuresPath = Paths.get(featuresDirTrain.getAbsolutePath() + "/train_" + i + ".csv");
            Path labelsPath = Paths.get(labelsDirTrain + "/train_" + i+ ".csv");
            int j;
            for (j = 0; j < numberOfTimesteps; j++) {
                Files.write(featuresPath, rawStrings.get(i + j).concat(System.lineSeparator()).getBytes(), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
            }
            Files.write(labelsPath, rawStrings.get(i + j).concat(System.lineSeparator()).getBytes(), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
        }

        for (int i = trainSize; i < testSize + trainSize; i++) {
            Path featuresPath = Paths.get(featuresDirTest + "/test_" + i + ".csv");
            Path labelsPath = Paths.get(labelsDirTest + "/test_" + i + ".csv");
            int j;
            for (j =0; j < numberOfTimesteps; j++) {
                Files.write(featuresPath, rawStrings.get(i + j).concat(System.lineSeparator()).getBytes(), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
            }
            Files.write(labelsPath, rawStrings.get(i + j).concat(System.lineSeparator()).getBytes(), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
        }

        return rawStrings;
    }

    private void setNumOfVariables(List<String> rawStrings) {
        numOfVariables = rawStrings.get(0).split(",").length;
    }


}