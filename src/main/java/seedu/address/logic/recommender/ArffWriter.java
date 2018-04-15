package seedu.address.logic.recommender;

import javafx.collections.ObservableList;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.order.Order;
import seedu.address.model.order.SubOrder;
import seedu.address.model.person.Person;
import seedu.address.model.product.Product;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

//@@author lowjiajin

/**
 * Handles the writing of {@code addressBook}'s data into an .arff file for recommender training.
 */
public class ArffWriter {
    
    private static final String ARFF_HEADER = "@RELATION ConvertedOrders\n\n" +
            "@ATTRIBUTE age NUMERIC\n" +
            "@ATTRIBUTE gender {M, F}\n" +
            "@ATTRIBUTE class {%s}\n\n" +
            "@DATA";

    private static final String MESSAGE_ARFF_HEADER_WRITE_FAIL = "Failed to write .arff header information" +
            " while building recommender training set.";
    private static final String MESSAGE_ARFF_DATA_WRITE_FAIL = "Failed to write .arff data entries" +
            " while building recommender training set.";
    private static final String MESSAGE_ARFF_CREATION_FAIL = "Failed to create .arff" +
            " while building recommender training set.";
    private static final String MESSAGE_ARFF_MAKE_CANNOT_CLOSE = "Failed to close .arff" +
            " while building recommender training set.";

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

    public void makeArffFromOrders() {
        BufferedWriter writer = makeWriter();
        writeArffHeader(writer);
        writeArffData(writer);
        closeArffFile(writer);
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
    private BufferedWriter makeWriter() {
        arff.getParentFile().mkdirs();
        try {
            return new BufferedWriter(new FileWriter(arff));
        } catch (IOException ioe) {
            System.out.println(MESSAGE_ARFF_CREATION_FAIL);
        }
        return null;
    }

    /**
     * Writes both the positive and negative purchase decisions as classes to be predicted, for every product.
     */
    private void writeArffHeader(BufferedWriter writer) {
        String classLabels = products.stream()
                .map(formatter::convertProductToBinaryLabels).collect(Collectors.joining(", "));
        try {
            writer.write(String.format(ARFF_HEADER, classLabels));
        } catch (IOException ioe) {
            System.out.println(MESSAGE_ARFF_HEADER_WRITE_FAIL);
        }
    }

    private void writeArffData(BufferedWriter writer) {
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
    private void writeOrdersOfPersonToArff(
            Person person, Map<String, HashSet<Integer>> productsBoughtMap, BufferedWriter writer) {

        try {
            for (Product product : products) {
                writer.write(formatter.formatDataEntry(person, product, getProductsBoughtByPerson(person, productsBoughtMap)));
            }
        } catch (IOException ioe) {
            System.out.println(MESSAGE_ARFF_DATA_WRITE_FAIL);
        }
    }

    public HashSet<Integer> getProductsBoughtByPerson(Person person, Map<String, HashSet<Integer>> productsBoughtMap) {
        // Defaults to an empty set in cases where a {@code person} has never bought anything.
        return productsBoughtMap.getOrDefault(person.getEmail().value, new HashSet<>());
    }

    private void closeArffFile(BufferedWriter writer) {
        try {
            writer.close();
        } catch (IOException ioe) {
            System.out.println(MESSAGE_ARFF_MAKE_CANNOT_CLOSE);
        }
    }
}