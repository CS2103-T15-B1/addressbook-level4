package seedu.address.logic.recommender;

import javafx.collections.ObservableList;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.order.Order;
import seedu.address.model.order.SubOrder;
import seedu.address.model.person.Person;
import seedu.address.model.product.Product;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

//@@author lowjiajin
public class ArffWriter {

    private static final String PREFIX_NOT = "!";
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

    private final ObservableList<Person> persons;
    private final ObservableList<Product> products;
    private final ObservableList<Order> orders;
    private final String arffPath;

    public ArffWriter(String arffPath, ReadOnlyAddressBook addressBook) {
        persons = addressBook.getPersonList();
        products = addressBook.getProductList();
        orders = addressBook.getOrderList();
        this.arffPath = arffPath;
    }

    public void makeArffFromOrders() {

        Map<String, HashSet<Integer>> productsBoughtMap = new HashMap<>();
        for (Order order : orders) {
            recordWhichPersonBoughtWhichProduct(productsBoughtMap, order);
        }

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(arffPath));
            writeArffHeader(writer);
            for (Person person : persons) {
                writeOrdersOfPersonToArff(productsBoughtMap, person, writer);
            }
            writer.close();
        } catch (IOException ioe) {
            System.out.println(MESSAGE_ARFF_CREATION_FAIL);
        }
    }

    private void recordWhichPersonBoughtWhichProduct(Map<String, HashSet<Integer>> productsBoughtMap, Order order) {
        String personId = order.getPersonId();

        for (SubOrder suborder : order.getSubOrders()) {
            if (productsBoughtMap.get(personId) == null) {
                productsBoughtMap.put(personId, new HashSet<>());
            }
            productsBoughtMap.get(personId).add(suborder.getProductID());
        }
    }

    private void writeOrdersOfPersonToArff(
            Map<String, HashSet<Integer>> productsBoughtMap, Person person, BufferedWriter writer) {

        HashSet<Integer> productsBoughtByPerson = productsBoughtMap.getOrDefault(
                person.getEmail(), new HashSet<>());
        for (Product product : products) {
            try {
                writer.write(formatPersonFeatures(person).concat(
                        checkProductClassLabel(product.getId(), productsBoughtByPerson)));
            } catch (IOException ioe) {
                System.out.println(MESSAGE_ARFF_DATA_WRITE_FAIL);
            }
        }
    }

    private void writeArffHeader(BufferedWriter writer) {
        String classLabels = products.stream()
                .map(this::convertProductToBinaryLabels).collect(Collectors.joining(", "));
        try {
            writer.write(String.format(ARFF_HEADER, classLabels));
        } catch (IOException ioe) {
            System.out.println(MESSAGE_ARFF_HEADER_WRITE_FAIL);
        }
    }

    private String formatPersonFeatures(Person person) {
        return String.format("\n%1$s,%2$s,", person.getAge().value, person.getGender());
    }

    private String checkProductClassLabel(Integer productId, HashSet<Integer> productsBoughtByPerson) {
        boolean hasBoughtProduct = productsBoughtByPerson.contains(productId);

        if (hasBoughtProduct) {
            return String.valueOf(productId);
        } else {
            return PREFIX_NOT.concat(String.valueOf(productId));
        }
    }

    private String convertProductToBinaryLabels(Product product) {
        return String.format("%1$s, !%1$s", String.valueOf(product.getId()));
    }
}