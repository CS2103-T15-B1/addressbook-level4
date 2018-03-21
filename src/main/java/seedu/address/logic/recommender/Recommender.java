package seedu.address.logic.recommender;

import weka.classifiers.Classifier;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Normalize;
import weka.filters.unsupervised.instance.RemoveWithValues;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Recommender {
    private static final String MESSAGE_INVALID_ARFF_PATH = "%1$s does not refer to a valid ARFF file.";
    private static final String MESSAGE_ERROR_READING_ARFF = "Error reading ARFF, check file name and format.";
    private static final String MESSAGE_CANNOT_CLOSE_READER = "Cannot close ARFF reader, reader still in use.";
    private static final String MESSAGE_CANNOT_NORMALIZE = "Cannot apply normalization, please check arff format.";
    private static final String MESSAGE_BAD_NORMALIZE_SETTINGS = "{@code WEKA_NORMALIZE_SETTINGS} has invalid value.";
    private static final String MESSAGE_BAD_REMOVER_SETTINGS = "{@code WEKA_REMOVER_SETTINGS} has invalid value.";

    private static final String WEKA_NORMALIZE_SETTINGS = "-S 1.0 -T 0.0";
    private static final String WEKA_REMOVER_SETTINGS = "-S 0.0 -C last -L %1$d -V";

    private String arffPath;
    private BufferedReader reader;
    private Instances orders;
    private Normalize normalize;
    private RemoveWithValues isolator;
    private HashMap<String, Classifier> classifierDict;

    public Recommender(String arffPath) {
        setFilePath(arffPath);
        parseOrdersFromFile();
        trainRecommenderOnOrders();
    }

    public void setFilePath(String path) {
        arffPath = path;
    }

    public ArrayList<String> getRecommendations(String customerId) throws IllegalArgumentException {
        return new ArrayList<>();
    }

    private void parseOrdersFromFile() {
        getReaderFromArff();
        getOrdersFromReader();
        closeReader();
        initNormalizer();
        normalizeOrderAttrs();
    }

    private void trainRecommenderOnOrders() {
        classifierDict = new HashMap<>();

        // Obtain distinct classifiers for each product to determine if a customer would buy that specific product
        int numOfProducts = orders.classAttribute().numValues();
        for (int productNum = 0; productNum < numOfProducts; productNum++) {
            initOrderIsolator(productNum);
            ProductTrainer trainer = new ProductTrainer(orders, isolator);

            if (trainer.hasTrained()) {
                addClassifier(productNum, trainer);
            }
        }
    }

    private void getReaderFromArff() {
        try {
            reader = new BufferedReader(new FileReader(arffPath));
        } catch (FileNotFoundException e) {
            System.out.println(String.format(MESSAGE_INVALID_ARFF_PATH, arffPath));
        }
    }

    private void getOrdersFromReader() {
        try {
            orders = new Instances(reader);
            orders.setClassIndex(orders.numAttributes() - 1);
        } catch (IOException e) {
            System.out.println(MESSAGE_ERROR_READING_ARFF);
        }
    }

    private void closeReader() {
        try {
            reader.close();
        } catch (IOException e) {
            System.out.println(MESSAGE_CANNOT_CLOSE_READER);
        }
    }

    private void initNormalizer() {
        normalize = new Normalize();
        try {
            normalize.setOptions(weka.core.Utils.splitOptions(WEKA_NORMALIZE_SETTINGS));
        } catch (Exception e) {
            System.out.println(MESSAGE_BAD_NORMALIZE_SETTINGS);
        }
    }

    private void normalizeOrderAttrs() {
        try {
            normalize.setInputFormat(orders);
            orders = Filter.useFilter(orders, normalize);
        } catch (Exception e) {
            System.out.println(MESSAGE_CANNOT_NORMALIZE);
        }
    }

    private void initOrderIsolator(int productNum) {
        assert productNum < orders.classAttribute().numValues();

        isolator = new RemoveWithValues();
        try {
            isolator.setOptions(weka.core.Utils.splitOptions(String.format(WEKA_REMOVER_SETTINGS, productNum + 1)));
        } catch (Exception e) {
            System.out.println(MESSAGE_BAD_REMOVER_SETTINGS);
        }
    }

    private void addClassifier(int productNum, ProductTrainer trainer) {
        String productId = orders.classAttribute().value(productNum);
        Classifier classifier = trainer.getClassifier();

        // Every classifier should never overwrite an existing one in each training cycle, as productID is primary key.
        assert classifierDict.get(productId) == null;
        classifierDict.put(productId, classifier);
    }
}