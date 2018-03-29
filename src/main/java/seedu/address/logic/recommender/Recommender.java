package seedu.address.logic.recommender;

import seedu.address.model.person.Person;
import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.unsupervised.instance.RemoveWithValues;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import static java.lang.Double.compare;

public class Recommender {
    private static final String MESSAGE_INVALID_ARFF_PATH = "%1$s does not refer to a valid ARFF file.";
    private static final String MESSAGE_ERROR_READING_ARFF = "Error reading ARFF, check file name and format.";
    private static final String MESSAGE_CANNOT_CLOSE_READER = "Cannot close ARFF reader, reader still in use.";
    private static final String MESSAGE_BAD_REMOVER_SETTINGS = "{@code WEKA_REMOVER_SETTINGS} has invalid value.";
    private static final String MESSAGE_CANNOT_CLASSIFY_INSTANCE = "Cannot classify instance.";

    private static final String WEKA_REMOVER_SETTINGS = "-S 0.0 -C last -L %1$d-%2$d -V -H";

    private String arffPath;
    private BufferedReader reader;
    private Instances orders;
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

    public String getRecommendations(Person person) {
        Instance personInstance = parsePerson(person);
        ArrayList<RecommenderProductDecision> ProductRecOfAPerson = new ArrayList<>();
        for (int i = 0; i < orders.classAttribute().numValues(); i += 2) {
            String currentProductPredicted = orders.classAttribute().value(i);
            Classifier classifier = classifierDict.get(currentProductPredicted);
            try {
                RecommenderProductDecision decision = new RecommenderProductDecision(
                        currentProductPredicted, classifier.distributionForInstance(personInstance)[0]);
                ProductRecOfAPerson.add(decision);
            } catch (Exception e) {
                System.out.println(MESSAGE_CANNOT_CLASSIFY_INSTANCE);
            }
        }
        Collections.sort(ProductRecOfAPerson);
        return Arrays.toString(ProductRecOfAPerson.toArray());
    }

    private Instance parsePerson(Person person) {

        ArrayList<String> genderNominals = new ArrayList<String>(Arrays.asList("m", "f"));
        Attribute ageAttribute = new Attribute("age");
        Attribute genderAttribute = new Attribute("gender", genderNominals);
        Attribute classAttribute = new Attribute("class", new ArrayList<>());

        ArrayList<Attribute> attributes = new ArrayList<Attribute>(
                Arrays.asList(ageAttribute, genderAttribute, classAttribute));

        Instances persons = new Instances("persons", attributes, 1);

        Instance personInstance = new DenseInstance(3);
        personInstance.setDataset(persons);
        personInstance.setValue(0, Double.parseDouble(person.getAge().value));
        personInstance.setValue(1, person.getGender().value.toLowerCase());

        return personInstance;
    }

    private void parseOrdersFromFile() {
        getReaderFromArff();
        getOrdersFromReader();
        closeReader();
    }

    private void trainRecommenderOnOrders() {
        classifierDict = new HashMap<>();

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

    private void initOrderIsolator(int productNum) {
        assert productNum < orders.classAttribute().numValues();

        isolator = new RemoveWithValues();
        try {
            isolator.setOptions(weka.core.Utils.splitOptions(String.format(
                    WEKA_REMOVER_SETTINGS, productNum + 1, productNum + 2)));
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

    private final class RecommenderProductDecision implements Comparable<RecommenderProductDecision> {
        private String productId;
        private double buyProb;

        private RecommenderProductDecision(String productId, double buyProb) {
            this.productId = productId;
            this.buyProb = buyProb;
        }

        private String getProductId() {
            return productId;
        }

        private double getBuyProb() {
            return buyProb;
        }

        @Override
        public int compareTo(RecommenderProductDecision other) {
            return compare(other.getBuyProb(), buyProb);
        }

        @Override
        public String toString() {
            return String.format("%1$s: %2$f", productId, buyProb);
        }
    }
}