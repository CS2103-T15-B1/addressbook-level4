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

//@@author lowjiajin

/**
 * Trains a classifier to predict whether or not one specific product will be bought.
 */
public class ProductTrainer {
    private static final String MESSAGE_CANNOT_ISOLATE_PRODUCT = "{@code isolator} has invalid settings for orders. " +
            "Error when isolating orders of a given product.";
    private static final String MESSAGE_CANNOT_BUILD_CLASSIFIER = "{@code orders} format is invalid. " +
            "Error building classifier.";
    private static final String MESSAGE_CANNOT_EVALUATE_CLASSIFIER = "Invalid parameters for " +
            "{@code crossValidateModel()} method, or orders modified after classifier built. " +
            "Error evaluating classifier.";

    private static final int WEKA_NUM_FEATURES_USED = 2;
    private static final int WEKA_MIN_ORDERS = 5;
    
    // Flag to print evaluation data for debugging
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

    /**
     * Remove all orders not involving a given product from the training dataset.
     * Allows a binary decision on whether or not to buy a given product.
     */
    private void isolateOrdersOfAProduct(RemoveWithValues isolator) {
        try {
            isolator.setInputFormat(orders);
            orders = Filter.useFilter(orders, isolator);
        } catch (Exception e) {
            throw new AssertionError(MESSAGE_CANNOT_ISOLATE_PRODUCT);
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

    /**
     * Ensures that noise is suppressed by not recommending products with too few {@code orders} 
     * to provide a reliable Recommender prediction.
     */
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
            canBuild = false;
            throw new AssertionError(MESSAGE_CANNOT_BUILD_CLASSIFIER);
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
            throw new AssertionError(MESSAGE_CANNOT_EVALUATE_CLASSIFIER);
        }
    }
}