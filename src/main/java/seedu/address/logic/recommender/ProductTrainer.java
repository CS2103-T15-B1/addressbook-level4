package seedu.address.logic.recommender;

import weka.attributeSelection.ChiSquaredAttributeEval;
import weka.attributeSelection.Ranker;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.meta.AttributeSelectedClassifier;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.instance.RemoveWithValues;

import java.util.Random;

public class ProductTrainer {
    private static final String MESSAGE_CANNOT_ISOLATE_PRODUCT = "Error when isolating orders of a given product. " +
            "Check that {@code isolator} has valid settings for orders";
    private static final String MESSAGE_CANNOT_BUILD_CLASSIFIER = "Error when building classifier. " +
            "Check {@code orders} format.";
    private static final String MESSAGE_CANNOT_EVALUATE_CLASSIFIER = "Error when evaluating classifier. " +
            "Invalid parameters for {@code crossValidateModel()} method, or orders modified after classifier built.";

    private static final int WEKA_NUM_FEATURES_USED = 2;
    private static final int WEKA_MIN_ORDERS = 5;
    private static final boolean WEKA_EVALUATE_CLASSIFIER = false;

    private Instances orders;
    private AttributeSelectedClassifier attrSelClassifier;
    private Evaluation evaluation;
    private boolean canBuild;

    public ProductTrainer (Instances trainingOrders, RemoveWithValues isolator) {
        orders = trainingOrders;
        isolateOrdersOfAProduct(isolator);
        trainClassifier();
    }

    public boolean hasTrained() {
        return canBuild;
    }

    public AttributeSelectedClassifier getClassifier() {
        // Should not ever get the classifier before training it.
        assert attrSelClassifier != null;

        return attrSelClassifier;
    }

    private void isolateOrdersOfAProduct(RemoveWithValues isolator) {
        try {
            isolator.setInputFormat(orders);
            orders = Filter.useFilter(orders, isolator);
        } catch (Exception e) {
            System.out.println(MESSAGE_CANNOT_ISOLATE_PRODUCT);
        }
    }

    private void trainClassifier() {
        if (hasEnoughOrdersToTrain()) {
            initClassifier();
            buildClassifier();
        }
        if (WEKA_EVALUATE_CLASSIFIER) {
            evaluateClassifier();
        }
    }

    private boolean hasEnoughOrdersToTrain() {
        return orders.numInstances() >= WEKA_MIN_ORDERS;
    }

    private void initClassifier() {
        attrSelClassifier = new AttributeSelectedClassifier();

        // Set classifier type
        NaiveBayes bayes = new NaiveBayes();
        attrSelClassifier.setClassifier(bayes);

        // Set feature evaluation criteria for classifier
        ChiSquaredAttributeEval chiEval = new ChiSquaredAttributeEval();
        attrSelClassifier.setEvaluator(chiEval);

        // Set feature search criteria for classifier
        Ranker ranker = new Ranker();
        ranker.setNumToSelect(WEKA_NUM_FEATURES_USED);
        attrSelClassifier.setSearch(ranker);
    }

    private void buildClassifier() {
        try {
            attrSelClassifier.buildClassifier(orders);
            canBuild = true;
        } catch (Exception e) {
            System.out.println(MESSAGE_CANNOT_BUILD_CLASSIFIER);
            canBuild = false;
        }
    }

    private void evaluateClassifier() {
        // Should not ever evaluate classifier if it has failed to build, because there is no classifier to evaluate.
        assert canBuild;

        try {
            // Evaluates the classifier with a n-fold cross validation, where n = {@code WEKA_MIN_ORDERS}
            evaluation = new Evaluation(orders);
            evaluation.crossValidateModel(attrSelClassifier, orders, WEKA_MIN_ORDERS, new Random(1));
            System.out.println(orders.classAttribute());
            System.out.println(evaluation.toSummaryString());
        } catch (Exception e) {
            System.out.println(MESSAGE_CANNOT_EVALUATE_CLASSIFIER);
        }
    }
}