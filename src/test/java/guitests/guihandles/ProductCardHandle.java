package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;

//@@author He Yingxu

/**
 * Provides a handle to a product card in the product list panel.
 */
public class ProductCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String PRICE_FIELD_ID = "#price";
    private static final String CATEGORY_FIELD_ID = "#catgory";

    private final Label idLabel;
    private final Label nameLabel;
    private final Label priceLabel;
    private final Label categoryLabel;

    public ProductCardHandle(Node cardNode) {
        super(cardNode);

        this.idLabel = getChildNode(ID_FIELD_ID);
        this.nameLabel = getChildNode(NAME_FIELD_ID);
        this.priceLabel= getChildNode(PRICE_FIELD_ID);
        this.categoryLabel = getChildNode(CATEGORY_FIELD_ID);
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getPrice() {
        return priceLabel.getText();
    }

    public String getCategory() {
        return categoryLabel.getText();
    }

}
