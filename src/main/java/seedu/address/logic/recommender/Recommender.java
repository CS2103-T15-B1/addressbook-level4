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

//@@author lowjiajin

/**
 * The logic that determines which products a person is most likely to buy for {@code RecommenderManager}.
 */
public class Recommender {
    
    private static final int POSITIVE_CLASS_INDEX = 0;
    
    private static final String MESSAGE_CANNOT_CLASSIFY_INSTANCE = "Cannot classify instance.";
    
    private static final String AGE_ATTRIBUTE_NAME = "age";
    private static final String GENDER_ATTRIBUTE_NAME = "gender";
    private static final String CLASS_ATTRIBUTE_NAME = "class";
    private static final String INSTANCE_TYPE = "person";
    private static final ArrayList<String> GENDER_NOMINALS = new ArrayList<String>(Arrays.asList("m", "f"));

    /**
     * Determines the likelihood of a person wanting to buy any product, assuming the product has a classifier.
     * @return A string in the following format: [<product id, probability of buying>, <...>, ...].
     */
    public String getRecommendations(ArrayList<String> productsWithClassifiers, Person person, HashMap<String, Classifier> classifierDict) {

        Instance personInstance = parsePerson(person);
        ArrayList<BuyDecision> productRecOfAPerson = new ArrayList<>();

        // Goes through every product with enough orders to allow a recommendation and records the recommendation
        for (int i = 0; i < productsWithClassifiers.size(); i++) {
            String currentProductPredicted = productsWithClassifiers.get(i);
            Classifier classifier = classifierDict.get(currentProductPredicted);
            BuyDecision decision = getBuyDecision(currentProductPredicted, classifier, personInstance);
            productRecOfAPerson.add(decision);
        }

        return getFormattedRecs(productRecOfAPerson);
    }

    /**
     * Extracts the feature data from a {@code person} and turns them into a {@code DenseInstance} for classification.
     */
    private Instance parsePerson(Person person) {
        // Set up the person as a Weka instance
        ArrayList<Attribute> attributes = getAttributes();
        Instances persons = new Instances(INSTANCE_TYPE, attributes, 1);
        Instance personInstance = new DenseInstance(attributes.size());
        
        // Assign values to the aforementioned instance
        personInstance.setDataset(persons);
        personInstance.setValue(0, Double.parseDouble(person.getAge().value));
        personInstance.setValue(1, person.getGender().value.toLowerCase());

        return personInstance;
    }

    /**
     * Sets up the age and gender as classification features.
     * @return the ArrayList of features, with the class (i.e. whether person will buy) to be predicted.
     */
    public ArrayList<Attribute> getAttributes() {
        Attribute ageAttribute = new Attribute(AGE_ATTRIBUTE_NAME);
        Attribute genderAttribute = new Attribute(GENDER_ATTRIBUTE_NAME, GENDER_NOMINALS);
        Attribute classAttribute = new Attribute(CLASS_ATTRIBUTE_NAME, new ArrayList<>());
        return new ArrayList<Attribute>(Arrays.asList(ageAttribute, genderAttribute, classAttribute));
    }

    /**
     * Uses Weka's {@code distributionForInstance} to obtain the probability of confidence in the buy decision.
     */
    private BuyDecision getBuyDecision(String currentProductPredicted, Classifier classifier, Instance personInstance) {
        double buyProb = 0;

        try {
            buyProb = classifier.distributionForInstance(personInstance)[POSITIVE_CLASS_INDEX];
        } catch (Exception e) {
            System.out.println(MESSAGE_CANNOT_CLASSIFY_INSTANCE);
        }
        return new BuyDecision(currentProductPredicted, buyProb);
    }

    /**
     * Sorts the recommendations so the most confident recommendations come first.
     * @return the recommendations as a String.
     */
    private String getFormattedRecs(ArrayList<BuyDecision> productRecOfAPerson) {
        Collections.sort(productRecOfAPerson);
        return Arrays.toString(productRecOfAPerson.toArray());
    }
}
