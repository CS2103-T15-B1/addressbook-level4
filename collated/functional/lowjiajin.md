# lowjiajin
###### \java\seedu\address\logic\commands\FindProductByCategoryCommand.java
``` java

/**
 * Lists all product from the specified category
 */
public class FindProductByCategoryCommand extends Command {

    public static final String COMMAND_WORD = "findproductbycategory";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all products whose categories contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " food";

    private static final String message = Messages.MESSAGE_PRODUCTS_LISTED_OVERVIEW;

    private final ProductCategoryContainsKeywordsPredicate predicate;

    public FindProductByCategoryCommand(ProductCategoryContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredProductList(predicate);
        return new CommandResult(getMessageForListShownSummary(model.getFilteredProductList().size(), message));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindProductByCategoryCommand // instanceof handles nulls
                && this.predicate.equals(((FindProductByCategoryCommand) other).predicate)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\FindProductByNameCommand.java
``` java
public class FindProductByNameCommand extends Command {

    public static final String COMMAND_WORD = "findproductbyname";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all products whose names contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " egg";

    private static final String message = Messages.MESSAGE_PRODUCTS_LISTED_OVERVIEW;

    private final ProductNameContainsKeywordsPredicate predicate;

    public FindProductByNameCommand(ProductNameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredProductList(predicate);
        return new CommandResult(getMessageForListShownSummary(model.getFilteredProductList().size(), message));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindProductByNameCommand // instanceof handles nulls
                && this.predicate.equals(((FindProductByNameCommand) other).predicate)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\FindProductByPriceCommand.java
``` java

/**
 * Lists all the products which prices lie within the specified interval.
 */
public class FindProductByPriceCommand extends Command {

    public static final String COMMAND_WORD = "findproductbyprice";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all products whose price lies within "
            + "the specified min/max (inclusive) interval and displays them as a list with index numbers.\n"
            + "Parameters: min/NUMBER max/NUMBER\n"
            + String.format("Example: %1$s %2$s10 %3$s15",
                    COMMAND_WORD, PREFIX_MIN_PRICE.getPrefix(), PREFIX_MAX_PRICE.getPrefix());

    private static final String message = Messages.MESSAGE_PRODUCTS_LISTED_OVERVIEW;

    private final ProductCostsBetweenPredicate predicate;

    public FindProductByPriceCommand(ProductCostsBetweenPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredProductList(predicate);
        return new CommandResult(getMessageForListShownSummary(model.getFilteredProductList().size(), message));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindProductByPriceCommand // instanceof handles nulls
                && this.predicate.equals(((FindProductByPriceCommand) other).predicate)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\ListProductCommand.java
``` java
/**
 * Lists all products in the address book to the user.
 */
public class ListProductCommand extends Command {

    public static final String COMMAND_WORD = "listproduct";

    public static final String MESSAGE_SUCCESS = "Listed all products";


    @Override
    public CommandResult execute() {
        model.updateFilteredProductList(PREDICATE_SHOW_ALL_PRODUCTS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### \java\seedu\address\logic\commands\RecommendCommand.java
``` java

/**
 * Finds recommendations for which products a given person is likely to buy
 */
public class RecommendCommand extends Command {

    public static final String COMMAND_WORD = "recommend";

    public static final String MESSAGE_SUCCESS = "Recommendations for: %1$s\n" +
            "Output format: [<product name, probability of buying>...]\n" +
            "%2$s";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds the products most likely to be bought by the " +
            "person identified by the index number used in the last person listing.\n" +
            "Parameters: INDEX (must be a positive integer)\n" +
            "Example:" + COMMAND_WORD + " 1";

    private static final String ARFF_NAME = "data/Orders.arff";

    private final Index targetIndex;
    private final ReadOnlyAddressBook addressBook;

    private Person personToRecommendFor;

    public RecommendCommand(Index targetIndex, ReadOnlyAddressBook addressBook) {
        this.targetIndex = targetIndex;
        this.addressBook = addressBook;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        personToRecommendFor = lastShownList.get(targetIndex.getZeroBased());
        RecommenderManager recommenderManager = new RecommenderManager(ARFF_NAME, addressBook);
        String recommendations = recommenderManager.getRecommendations(personToRecommendFor);

        return new CommandResult(String.format(MESSAGE_SUCCESS, personToRecommendFor.getName(), recommendations));
    }
}
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case FindProductByCategoryCommand.COMMAND_WORD:
            return new FindProductByCategoryCommandParser().parse(arguments);

        case FindProductByNameCommand.COMMAND_WORD:
            return new FindProductByNameCommandParser().parse(arguments);

        case FindProductByPriceCommand.COMMAND_WORD:
            return new FindProductByPriceCommandParser().parse(arguments);

        case ListProductCommand.COMMAND_WORD:
            return new ListProductCommand();

```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case RecommendCommand.COMMAND_WORD:
            return new RecommendCommandParser(addressBook).parse(arguments);

```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
            case FindProductByCategoryCommand.COMMAND_WORD:
                return new FindProductByCategoryCommandParser().parse(arguments);

            case FindProductByNameCommand.COMMAND_WORD:
                return new FindProductByNameCommandParser().parse(arguments);

            case FindProductByPriceCommand.COMMAND_WORD:
                return new FindProductByPriceCommandParser().parse(arguments);

            case ListProductCommand.COMMAND_WORD:
                return new ListProductCommand();

```
###### \java\seedu\address\logic\parser\FindProductByCategoryCommandParser.java
``` java

/**
 * Parses input arguments and creates a new FindProductByCategoryCommand object
 */
public class FindProductByCategoryCommandParser implements Parser<FindProductByCategoryCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindProductByCategoryCommand
     * and returns a FindProductByCategoryCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public FindProductByCategoryCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindProductByCategoryCommand.MESSAGE_USAGE));
        }

        String[] categoryKeywords = trimmedArgs.split("\\s+");

        return new FindProductByCategoryCommand(
                new ProductCategoryContainsKeywordsPredicate(Arrays.asList(categoryKeywords)));
    }
}
```
###### \java\seedu\address\logic\parser\FindProductByNameCommandParser.java
``` java
public class FindProductByNameCommandParser implements Parser<FindProductByNameCommand> {

    public FindProductByNameCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindProductByNameCommand.MESSAGE_USAGE));
        }

        String[] categoryKeywords = trimmedArgs.split("\\s+");

        return new FindProductByNameCommand(
                new ProductNameContainsKeywordsPredicate(Arrays.asList(categoryKeywords)));
    }
}
```
###### \java\seedu\address\logic\parser\FindProductByPriceCommandParser.java
``` java

/**
 * Parses input arguments and creates a new FindProductByPriceCommand object
 */
public class FindProductByPriceCommandParser implements Parser<FindProductByPriceCommand> {

    /**
     * Parses the given {@code String} of arguments for the min and max price delimiters of the
     * FindProductByPriceCommand and returns a FindProductByPriceCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format.
     */
    public FindProductByPriceCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MIN_PRICE, PREFIX_MAX_PRICE);

        // Ensures that the input conforms to the min and max price argument delimiters.
        if (!arePrefixesPresent(argMultimap, PREFIX_MIN_PRICE, PREFIX_MAX_PRICE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindProductByPriceCommand.MESSAGE_USAGE));
        }

        try {
            Money minPrice = ParserUtil.parsePrice(argMultimap.getValue(PREFIX_MIN_PRICE)).get();
            Money maxPrice = ParserUtil.parsePrice(argMultimap.getValue(PREFIX_MAX_PRICE)).get();

            return new FindProductByPriceCommand(new ProductCostsBetweenPredicate(minPrice, maxPrice));

        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
```
###### \java\seedu\address\logic\parser\RecommendCommandParser.java
``` java

/**
 * Parses input arguments and creates a new RecommendCommand object
 */
public class RecommendCommandParser implements Parser<RecommendCommand> {

    private ReadOnlyAddressBook addressBook;

    /**
     * Parses the given {@code String} of arguments in the context of the RecommendCommand
     * and returns a RecommendCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public RecommendCommandParser(ReadOnlyAddressBook addressBook) {
        this.addressBook = addressBook;
    }

    public RecommendCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new RecommendCommand(index, addressBook);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RecommendCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\logic\recommender\ArffFormatter.java
``` java

/**
 * Controls how the training data is formatted into header and data entries of the .arff format.
 */
public class ArffFormatter {
    private static final String PREFIX_NOT = "!";
    private static final String WEKA_DELIMITER = ",";
    private final HashMap<Integer, String> productIdToNameMap;
    
    public ArffFormatter(HashMap<Integer, String> productIdToNameMap) {
        this.productIdToNameMap = productIdToNameMap;
    }

    /**
     * For the header, a product is converted into two distinct classes for prediction:
     * to buy the product, or not to buy the product.
     */
    public String convertProductToBinaryLabels(Product product) {
        return String.format("%1$s%3$s %2$s%1$s", productIdToNameMap.get(product.getId()), PREFIX_NOT, WEKA_DELIMITER);
    }

    /**
     * Concatenates the {@code person}'s features and his class (i.e. has bought a product or not) into an .arff entry
     */
    public String formatDataEntry(Person person, Product product, HashSet<Integer> productsBoughtByPerson) {
        return String.format("%1$s%2$s",
                formatPersonFeatures(person), getProductClassLabel(product.getId(), productsBoughtByPerson));
    }

    /**
     * @return the delimited training features
     */
    private String formatPersonFeatures(Person person) {
        return String.format("\n%1$s%3$s%2$s%3$s", person.getAge().value, person.getGender(), WEKA_DELIMITER);
    }

    /**
     * Checks if product has been bought by a person and formats it as either a positive or negative class entry.
     */
    private String getProductClassLabel(Integer productId, HashSet<Integer> productsBoughtByPerson) {
        boolean hasBoughtProduct = productsBoughtByPerson.contains(productId);

        if (hasBoughtProduct) {
            return productIdToNameMap.get(productId);
        } else {
            return PREFIX_NOT.concat(productIdToNameMap.get(productId));
        }
    }
}
```
###### \java\seedu\address\logic\recommender\ArffWriter.java
``` java

/**
 * Handles the writing of {@code addressBook}'s data into an .arff file for recommender training.
 */
public class ArffWriter {
    
    private static final String ARFF_HEADER = "@RELATION ConvertedOrders\n\n"
            + "@ATTRIBUTE age NUMERIC\n"
            + "@ATTRIBUTE gender {M, F}\n"
            + "@ATTRIBUTE class {%s}\n\n"
            + "@DATA";

    private static final String MESSAGE_ARFF_HEADER_WRITE_FAIL = "Failed to write .arff header information"
            + " while building recommender training set.";
    private static final String MESSAGE_ARFF_DATA_WRITE_FAIL = "Failed to write .arff data entries"
            + " while building recommender training set.";
    private static final String MESSAGE_ARFF_CREATION_FAIL = "Failed to create .arff"
            + " while building recommender training set.";
    private static final String MESSAGE_ARFF_MAKE_CANNOT_CLOSE = "Failed to close .arff"
            + " while building recommender training set.";
    private static final String MESSAGE_CANNOT_WRITE = "Cannot write to .arff file.";

    private final ObservableList<Person> persons;
    private final ObservableList<Product> products;
    private final ObservableList<Order> orders;
    private final File arff;
    private final ArffFormatter formatter;

    public ArffWriter(File arff, ReadOnlyAddressBook addressBook) {
        this.arff = arff;
        persons = addressBook.getPersonList();
        products = addressBook.getProductList();
        orders = addressBook.getOrderList();
        formatter = new ArffFormatter(getProductIdToNameMap());
    }

    /**
     * Writes orders to file located at {@code arff}.
     */
    public void makeArffFromOrders() {        
        try {
            BufferedWriter writer = makeWriter();
            writeArffHeader(writer);
            writeArffData(writer);
            closeArffFile(writer);
        } catch (IOException ioe) {
            throw new AssertionError(String.format("%1$s.\n%2$s", MESSAGE_CANNOT_WRITE, ioe.getMessage()));
        }
    }

    /**
     * @return HashMap which allows retrieval of a {@code product}'s name with its numerical id as key.
     */
    private HashMap<Integer, String> getProductIdToNameMap() {
        HashMap<Integer, String> productIdToNameMap = new HashMap<>();
        
        for (Product product : products) {
            productIdToNameMap.put(product.getId(), product.getName().fullProductName);
        }
        
        return productIdToNameMap;
    }

    /**
     * Makes a directory for the .arff file if it doesn't already exist, then,
     * @return the new writer in erstwhile directory.
     */
    private BufferedWriter makeWriter() throws IOException {
        arff.getParentFile().mkdirs();
        try {
            return new BufferedWriter(new FileWriter(arff));
        } catch (IOException ioe) {
            throw new IOException(MESSAGE_ARFF_CREATION_FAIL);
        }
    }

    /**
     * Writes both the positive and negative purchase decisions as classes to be predicted, for every product.
     */
    private void writeArffHeader(BufferedWriter writer) throws IOException {
        String classLabels = products.stream()
                .map(formatter::convertProductToBinaryLabels).collect(Collectors.joining(", "));
        try {
            writer.write(String.format(ARFF_HEADER, classLabels));
        } catch (IOException ioe) {
            throw new IOException(MESSAGE_ARFF_HEADER_WRITE_FAIL);
        }
    }

    private void writeArffData(BufferedWriter writer) throws IOException {
        Map<String, HashSet<Integer>> productsBoughtMap = makeProductsBoughtMap();
        for (Person person : persons) {
            writeOrdersOfPersonToArff(person, productsBoughtMap, writer);
        }
    }

    /**
     * @return Map which allows retrieval of a set of all products a {@code person} has bought, 
     * keyed by {@code person}'s id.
     */
    private Map<String, HashSet<Integer>> makeProductsBoughtMap() {
        Map<String, HashSet<Integer>> productsBoughtMap = new HashMap<>();
        for (Order order : orders) {
            recordWhichPersonBoughtWhichProduct(productsBoughtMap, order);
        }
        return productsBoughtMap;
    }

    /**
     * For a given order, records the {@code person} as having bought the {@code product},
     * via {@code productsBoughtMap}.
     */
    private void recordWhichPersonBoughtWhichProduct(Map<String, HashSet<Integer>> productsBoughtMap, Order order) {
        String personId = order.getPersonId();

        for (SubOrder suborder : order.getSubOrders()) {
            if (productsBoughtMap.get(personId) == null) {
                productsBoughtMap.put(personId, new HashSet<>());
            }
            productsBoughtMap.get(personId).add(suborder.getProductID());
        }
    }

    /**
     * Write whether a {@code person} has purchased a {@code product} as a data entry in the .arff file,
     * for every {@code product} in the {@code addressBook}.
     */
    private void writeOrdersOfPersonToArff (
            Person person, Map<String, HashSet<Integer>> productsBoughtMap, BufferedWriter writer) throws IOException {

        try {
            for (Product product : products) {
                writer.write(formatter.formatDataEntry(person, product, 
                        getProductsBoughtByPerson(person, productsBoughtMap)));
            }
        } catch (IOException ioe) {
            throw new IOException(MESSAGE_ARFF_DATA_WRITE_FAIL);
        }
    }

    public HashSet<Integer> getProductsBoughtByPerson(Person person, Map<String, HashSet<Integer>> productsBoughtMap) {
        // Defaults to an empty set in cases where a {@code person} has never bought anything.
        return productsBoughtMap.getOrDefault(person.getEmail().value, new HashSet<>());
    }

    private void closeArffFile(BufferedWriter writer) throws IOException {
        try {
            writer.close();
        } catch (IOException ioe) {
            throw new IOException(MESSAGE_ARFF_MAKE_CANNOT_CLOSE);
        }
    }
}
```
###### \java\seedu\address\logic\recommender\BuyDecision.java
``` java

/**
 * Represents the confidence in the decision of whether to buy a given product, referenced by its {@code productName}.
 * Package private to Recommender.
 */
class BuyDecision implements Comparable<BuyDecision> {
    private static final String MESSAGE_COMPARING_NULL = "Cannot compare a BuyDecision with a null.";
    private String productName;
    private double buyProb;

    BuyDecision(String productName, double buyProb) {
        this.productName = productName;
        this.buyProb = buyProb;
    }

    private String getProductName() {
        return productName;
    }

    private double getBuyProb() {
        return buyProb;
    }

    /**
     * Used in sorting the recommendations by probability of purchase,
     * so the most confident recommendations are presented first.
     */
    @Override
    public int compareTo(BuyDecision other) {
        if (!(other instanceof BuyDecision)) {
            throw new AssertionError(MESSAGE_COMPARING_NULL);
        }
        return compare(other.getBuyProb(), buyProb);
    }

    /**
     * Controls how the each product's recommendation is displayed in the CLI output.
     * {@code productName} and {@code buyProb} delimited with a colon and space.
     */
    @Override
    public String toString() {
        return String.format("%1$s: %2$f", productName, buyProb);
    }
}
```
###### \java\seedu\address\logic\recommender\ProductTrainer.java
``` java

/**
 * Trains a classifier to predict whether or not one specific product will be bought.
 */
public class ProductTrainer {
    private static final String MESSAGE_CANNOT_ISOLATE_PRODUCT = "{@code isolator} has invalid settings for orders. "
            + "Error when isolating orders of a given product.";
    private static final String MESSAGE_CANNOT_BUILD_CLASSIFIER = "{@code orders} format is invalid. "
            + "Error building classifier.";
    private static final String MESSAGE_CANNOT_EVALUATE_CLASSIFIER = "Invalid parameters for "
            + "{@code crossValidateModel()} method, or orders modified after classifier built. "
            + "Error evaluating classifier.";

    private static final int WEKA_NUM_FEATURES_USED = 2;
    private static final int WEKA_MIN_ORDERS = 5;
    private static final boolean WEKA_EVALUATE_CLASSIFIER = false; // Flag to print evaluation data for debugging

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
        assert attrSelClassifier != null; // Should not ever get the classifier before training it.
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
```
###### \java\seedu\address\logic\recommender\Recommender.java
``` java

/**
 * The logic that determines which products a person is most likely to buy for {@code RecommenderManager}.
 */
public class Recommender {

    private static final int POSITIVE_CLASS_INDEX = 0;

    private static final String MESSAGE_CANNOT_CLASSIFY_INSTANCE = "The attribute format has to match the classifier "
            + "for the product to be classified.";

    private static final String AGE_ATTRIBUTE_NAME = "age";
    private static final String GENDER_ATTRIBUTE_NAME = "gender";
    private static final String CLASS_ATTRIBUTE_NAME = "class";
    private static final String INSTANCE_TYPE = "person";
    private static final ArrayList<String> GENDER_NOMINALS = new ArrayList<String>(Arrays.asList("m", "f"));

    /**
     * Determines the likelihood of a person wanting to buy any product, assuming the product has a classifier,
     * and returns the decision as a string.
     */
    public String getRecommendations(
            ArrayList<String> productsWithClassifiers, Person person, HashMap<String, Classifier> classifierDict) {

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
     *
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
            throw new AssertionError(MESSAGE_CANNOT_CLASSIFY_INSTANCE);
        }
        return new BuyDecision(currentProductPredicted, buyProb);
    }

    /**
     * Sorts the recommendations so the most confident recommendations come first.
     *
     * @return the recommendations as a String.
     */
    private String getFormattedRecs(ArrayList<BuyDecision> productRecOfAPerson) {
        Collections.sort(productRecOfAPerson);
        return Arrays.toString(productRecOfAPerson.toArray());
    }
}
```
###### \java\seedu\address\logic\recommender\RecommenderManager.java
``` java

/**
 * Manages the training of the recommendations classifier, and its subsequent use on a new {@code person}.
 */
public class RecommenderManager {
    private static final String MESSAGE_INVALID_ARFF_PATH = "%1$s does not refer to a valid ARFF file.";
    private static final String MESSAGE_ERROR_READING_ARFF = "File name or format invalid, error reading ARFF.";
    private static final String MESSAGE_CANNOT_CLOSE_READER = "Cannot close ARFF reader, reader still in use.";
    private static final String MESSAGE_BAD_REMOVER_SETTINGS = "{@code WEKA_REMOVER_SETTINGS} has invalid value.";
    private static final String MESSAGE_ORDERS_IS_NULL = "Cannot classify with a lack of orders in .arff."
            + " Check data entries in file.";

    private static final String WEKA_REMOVER_SETTINGS = "-S 0.0 -C last -L %1$d-%2$d -V -H";

    private File arff;
    private BufferedReader reader;
    private Instances orders;
    private RemoveWithValues isolator;
    private HashMap<String, Classifier> classifierDict;
    private ArrayList<String> productsWithClassifiers;

    /**
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
```
###### \java\seedu\address\model\product\ProductCategoryContainsKeywordsPredicate.java
``` java
public class ProductCategoryContainsKeywordsPredicate implements Predicate<Product> {
    private final List<String> keywords;

    public ProductCategoryContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Product product) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(product.getCategory().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ProductCategoryContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((ProductCategoryContainsKeywordsPredicate) other).keywords)); // state check
    }
}
```
###### \java\seedu\address\model\product\ProductCostsBetweenPredicate.java
``` java
public class ProductCostsBetweenPredicate implements Predicate<Product> {

    private final Money minPrice;
    private final Money maxPrice;

    public ProductCostsBetweenPredicate(Money minPrice, Money maxPrice) {
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    @Override
    public boolean test(Product product) {
        return product.getPrice().compareTo(minPrice) >= 0 && product.getPrice().compareTo(maxPrice) <= 0;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ProductCostsBetweenPredicate // instanceof handles nulls
                && this.minPrice.equals(((ProductCostsBetweenPredicate) other).minPrice)
                && this.maxPrice.equals(((ProductCostsBetweenPredicate) other).maxPrice)); // state check
    }
}
```
###### \java\seedu\address\model\product\ProductNameContainsKeywordsPredicate.java
``` java
public class ProductNameContainsKeywordsPredicate implements Predicate<Product> {
    private final List<String> keywords;

    public ProductNameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Product product) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(product.getName().fullProductName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ProductNameContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((ProductNameContainsKeywordsPredicate) other).keywords)); // state check
    }
}
```
###### \java\seedu\address\model\util\SampleDataUtil.java
``` java
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alex@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"), new Gender("M"), new Age("15"),
                new Latitude("1.339160"), new Longitude("103.745133"),getTagSet("friends")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("bernice@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new Gender("F"), new Age("15"),
                new Latitude("1.389889"), new Longitude("103.726903"), getTagSet("colleagues", "friends")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new Gender("F"),new Age("56"),
                new Latitude("1.379932"), new Longitude("103.852374"), getTagSet("neighbours")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("david@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new Gender("M"), new Age("23"),
                new Latitude("1.363222"), new Longitude("103.883062"), getTagSet("family")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"), new Gender("M"), new Age("77"),
                new Latitude("1.357340"), new Longitude("103.890084"), getTagSet("classmates")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("roy@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"), new Gender("M"), new Age("48"),
                new Latitude("1.327898"), new Longitude("103.907420"), getTagSet("colleagues")),
            new Person(new Name("Tan Roo Yang"), new Phone("97776590"), new Email("rooyang@example.com"),
                new Address("Blk 55 Tiong Bahru Lorong 4, #03-10"), new Gender("M"), new Age("23"),
                new Latitude("1.250828"), new Longitude("103.832659"), getTagSet("colleagues")),
            new Person(new Name("Linda Gao"), new Phone("81226734"), new Email("linda@example.com"),
                new Address("Rio Casa, Punggol Avenue 8, #05-33"), new Gender("F"), new Age("22"),
                new Latitude("1.339416"), new Longitude("103.745100"), getTagSet("colleagues")),
            new Person(new Name("Zelene Quek"), new Phone("81226734"), new Email("zelene@example.com"),
                new Address("12D Philips Avenue"), new Gender("F"), new Age("61"),
                new Latitude("1.357639"), new Longitude("104.014221"), getTagSet("family"))
        };
    }

    public static Product[] getSampleProducts() {
        return new Product[] {
            new Product(new ProductName("TrendyShirt"), new Money(new BigDecimal(12)),
                new Category("Clothing")),
            new Product(new ProductName("Dentures"), new Money(new BigDecimal(200)),
                new Category("Healthcare")),
            new Product(new ProductName("Lipstick"), new Money(new BigDecimal(30)),
                new Category("Cosmetics")),
            new Product(new ProductName("Toothbrush"), new Money(new BigDecimal(5)),
                new Category("Healthcare"))
        };
    }

    public static Order[] getSampleOrders() {
        return new Order[] {
            new Order("alex@example.com", Stream.of(
                    new SubOrder(1, 1, new Money(new BigDecimal(12))),
                    new SubOrder(4, 1, new Money(new BigDecimal(6)))).collect(Collectors.toList())),
            new Order("bernice@example.com", Stream.of(
                    new SubOrder(1, 1, new Money(new BigDecimal(12))),
                    new SubOrder(3, 1, new Money(new BigDecimal(30)))).collect(Collectors.toList())),
            new Order("charlotte@example.com", Stream.of(
                    new SubOrder(2, 1, new Money(new BigDecimal(200))),
                    new SubOrder(3, 1, new Money(new BigDecimal(29)))).collect(Collectors.toList())),
            new Order("david@example.com", Stream.of(
                    new SubOrder(1, 1, new Money(new BigDecimal(12)))).collect(Collectors.toList())),
            new Order("irfan@example.com", Stream.of(
                    new SubOrder(2, 1, new Money(new BigDecimal(200))),
                    new SubOrder(4, 1, new Money(new BigDecimal(5)))).collect(Collectors.toList())),
            new Order("roy@example.com", Stream.of(
                    new SubOrder(4, 1, new Money(new BigDecimal(4)))).collect(Collectors.toList())),
            new Order("rooyang@example.com", Stream.of(
                    new SubOrder(1, 1, new Money(new BigDecimal(13)))).collect(Collectors.toList())),
            new Order("linda@example.com", Stream.of(
                    new SubOrder(1, 1, new Money(new BigDecimal(12))),
                    new SubOrder(3, 1, new Money(new BigDecimal(30))),
                    new SubOrder(4, 1, new Money(new BigDecimal(5)))).collect(Collectors.toList())),
            new Order("zelene@example.com", Stream.of(
                    new SubOrder(2, 1, new Money(new BigDecimal(230)))).collect(Collectors.toList()))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        try {
            AddressBook sampleAb = new AddressBook();
            for (Person samplePerson : getSamplePersons()) {
                sampleAb.addPerson(samplePerson);
            }
            for (Product sampleProduct : getSampleProducts()) {
                sampleAb.addProduct(sampleProduct);
            }
            for (Order sampleOrder : getSampleOrders()) {
                sampleAb.addOrder(sampleOrder);
            }
            return sampleAb;
        } catch (DuplicatePersonException e) {
            throw new AssertionError("sample data cannot contain duplicate persons", e);
        } catch (DuplicateProductException e) {
            throw new AssertionError("sample data cannot contain duplicate products", e);
        } catch (DuplicateOrderException e) {
            throw new AssertionError("sample data cannot contain duplicate orders", e);
        }
    }

```
