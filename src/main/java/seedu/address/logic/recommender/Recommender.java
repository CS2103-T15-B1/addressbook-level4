package seedu.address.logic.recommender;

import seedu.address.model.person.Person;
import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import static java.lang.Double.compare;

public class Recommender {

    private static final String MESSAGE_CANNOT_CLASSIFY_INSTANCE = "Cannot classify instance.";

    /**
     * Determines the likelihood of a person wanting to buy any product, assuming the product has a classifier.
     * @return A string in the following format: [<product id, probability of buying>, <...>, ...].
     */
    public String getRecommendations(ArrayList<String> productsWithClassifiers, Person person, HashMap<String, Classifier> classifierDict) {

        Instance personInstance = parsePerson(person);
        ArrayList<Recommender.RecommenderProductDecision> ProductRecOfAPerson = new ArrayList<>();

        for (int i = 0; i < productsWithClassifiers.size(); i++) {
            String currentProductPredicted = productsWithClassifiers.get(i);
            Classifier classifier = classifierDict.get(currentProductPredicted);
            try {
                Recommender.RecommenderProductDecision decision = new Recommender.RecommenderProductDecision(
                        currentProductPredicted, classifier.distributionForInstance(personInstance)[0]);
                ProductRecOfAPerson.add(decision);
            } catch (Exception e) {
                System.out.println(MESSAGE_CANNOT_CLASSIFY_INSTANCE);
            }
        }

        Collections.sort(ProductRecOfAPerson);
        return Arrays.toString(ProductRecOfAPerson.toArray());
    }

    /**
     * Extracts the feature data from a {@code person} and turns them into a {@code DenseInstance} for classification.
     */
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

    /**
     * Represents the confidence in the decision of whether to buy a given product, referenced by its {@code productId}.
     */
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

        /**
         * Used in sorting the recommendations so only the most confident recommendations are presented.
         */
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
