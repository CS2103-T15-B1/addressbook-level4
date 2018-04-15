package seedu.address.logic.recommender;

import static java.lang.Double.compare;

//@@author lowjiajin
/**
 * Represents the confidence in the decision of whether to buy a given product, referenced by its {@code productName}.
 * Package private to Recommender
 */
class BuyDecision implements Comparable<BuyDecision> {
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
     * Used in sorting the recommendations so only the most confident recommendations are presented.
     */
    @Override
    public int compareTo(BuyDecision other) {
        return compare(other.getBuyProb(), buyProb);
    }

    @Override
    public String toString() {
        return String.format("%1$s: %2$f", productName, buyProb);
    }
}
