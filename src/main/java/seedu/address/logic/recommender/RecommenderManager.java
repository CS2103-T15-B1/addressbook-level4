package seedu.address.logic.recommender;

import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;
import weka.classifiers.Classifier;
import weka.core.Instances;
import weka.filters.unsupervised.instance.RemoveWithValues;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

//@@author lowjiajin
public class RecommenderManager {
    private static final String MESSAGE_INVALID_ARFF_PATH = "%1$s does not refer to a valid ARFF file.";
    private static final String MESSAGE_ERROR_READING_ARFF = "File name or format invalid, error reading ARFF.";
    private static final String MESSAGE_CANNOT_CLOSE_READER = "Cannot close ARFF reader, reader still in use.";
    private static final String MESSAGE_BAD_REMOVER_SETTINGS = "{@code WEKA_REMOVER_SETTINGS} has invalid value.";
    private static final String MESSAGE_ORDERS_IS_NULL = "Cannot classify with a lack of orders in .arff." +
            " Check data entries in file.";

    private static final String WEKA_REMOVER_SETTINGS = "-S 0.0 -C last -L %1$d-%2$d -V -H";

    private File arff;
    private BufferedReader reader;
    private Instances orders;
    private RemoveWithValues isolator;
    private HashMap<String, Classifier> classifierDict;
    private ArrayList<String> productsWithClassifiers;

    /**
     * Manages the training of the recommendations classifier, and its subsequent use on a new {@code person} .
     * @param arffPath the data folder where the .arff orders file is stored.
     */
    public RecommenderManager(String arffPath, ReadOnlyAddressBook addressBook) {
        setTrainerFile(arffPath);
        writeOrdersAsTraningData(addressBook);
        parseOrdersFromFile();
        trainRecommenderOnOrders();
    }

    public void setTrainerFile(String path) {
        arff = new File(path);
    }

    /**
     * Sends previously computed {@code classifierDict} to the Recommender logic to obtain a list of recommended buys
     * for the given {@code person}, for all the products with sufficient {@code orders} to make a recommendation.
     */
    public String getRecommendations(Person person) {
        Recommender recommender = new Recommender();
        return recommender.getRecommendations(productsWithClassifiers, person, classifierDict);
    }

    private void writeOrdersAsTraningData(ReadOnlyAddressBook addressBook) {
        ArffWriter arffWriter = new ArffWriter(arff, addressBook);
        arffWriter.makeArffFromOrders();
    }

    private void parseOrdersFromFile() {
        getReaderFromArff();
        getOrdersFromReader();
        closeReader();
    }

    /**
     * Adds a binary classifier (i.e. a yes/no recommender) for every product to {@code classifierDict}
     * iff a given {@code trainer} can successfully perform the classifier training.
     */
    private void trainRecommenderOnOrders() {

        try {
            if (orders == null) {
                throw new NullPointerException();
            }
        } catch (NullPointerException npe) {
            throw new AssertionError(MESSAGE_ORDERS_IS_NULL);
        }

        classifierDict = new HashMap<>();
        productsWithClassifiers = new ArrayList<>();

        // Obtain distinct classifiers for each product to determine if a customer would buy that specific product
        int numOfProducts = orders.classAttribute().numValues();
        for (int productNum = 0; productNum < numOfProducts; productNum += 2) {
            initOrderIsolator(productNum);
            ProductTrainer trainer = new ProductTrainer(orders, isolator);

            if (trainer.hasTrained()) {
                addClassifier(productNum, trainer);
            }
        }
    }

    private void getReaderFromArff() {
        try {
            reader = new BufferedReader(new FileReader(arff));
        } catch (FileNotFoundException e) {
            throw new AssertionError(String.format(MESSAGE_INVALID_ARFF_PATH, arff));
        }
    }

    private void getOrdersFromReader() {
        try {
            orders = new Instances(reader);
            orders.setClassIndex(orders.numAttributes() - 1);
        } catch (IOException e) {
            throw new AssertionError(MESSAGE_ERROR_READING_ARFF);
        }
    }

    private void closeReader() {
        try {
            reader.close();
        } catch (IOException e) {
            throw new AssertionError(MESSAGE_CANNOT_CLOSE_READER);
        }
    }

    /**
     * Subsamples from our {@code orders}, only including orders from a given product, for binary classification use.
     * @param productNum index referring to a specific product in Weka's Instances.
     */
    private void initOrderIsolator(int productNum) {
        assert productNum < orders.classAttribute().numValues();

        isolator = new RemoveWithValues();
        try {
            isolator.setOptions(weka.core.Utils.splitOptions(String.format(
                    WEKA_REMOVER_SETTINGS, productNum + 1, productNum + 2)));
        } catch (Exception e) {
            throw new AssertionError(MESSAGE_BAD_REMOVER_SETTINGS);
        }
    }

    /**
     * Adds the new classifier in {@code trainer} to {@code classifierDict} and
     * records this addition in {@code productsWithClassifiers}
     */
    private void addClassifier(int productNum, ProductTrainer trainer) {
        String productId = orders.classAttribute().value(productNum);
        Classifier classifier = trainer.getClassifier();

        // Every classifier should never overwrite an existing one in each training cycle, as productID is primary key.
        assert classifierDict.get(productId) == null;
        classifierDict.put(productId, classifier);
        productsWithClassifiers.add(productId);
    }
}