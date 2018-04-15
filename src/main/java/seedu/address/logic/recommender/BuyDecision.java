package seedu.address.logic.recommender;

import static java.lang.Double.compare;

//@@author lowjiajin

/**
 * Represents the confidence in the decision of whether to buy a given product, referenced by its {@code productName}.
 * Package private to Recommender.
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
     * Used in sorting the recommendations by probability of purchase,
     * so the most confident recommendations are presented first.
     */
    @Override
    public int compareTo(BuyDecision other) {
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
