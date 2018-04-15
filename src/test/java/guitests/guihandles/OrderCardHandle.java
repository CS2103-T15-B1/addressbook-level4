package guitests.guihandles;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.stream.Collectors;

//@@author He Yingxu

/**
 * Provides a handle to a Order card in the Order list panel.
 */
public class OrderCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String PERSONID_FIELD_ID = "#personId";
    private static final String TIME_FIELD_ID = "#time";
    private static final String SUBORDERS_FIELD_ID = "#subOrders";

    private final Label idLabel;
    private final Label personIdLabel;
    private final Label timeLabel;
    private final List<VBox> subOrdersLabels;

    public OrderCardHandle(Node cardNode) {
        super(cardNode);

        this.idLabel = getChildNode(ID_FIELD_ID);
        this.personIdLabel = getChildNode(PERSONID_FIELD_ID);
        this.timeLabel= getChildNode(TIME_FIELD_ID);

        Region subOrdersContainer = getChildNode(SUBORDERS_FIELD_ID);
        this.subOrdersLabels = subOrdersContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(VBox.class::cast)
                .collect(Collectors.toList());
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getPersonId() {
        return personIdLabel.getText();
    }

    public String getTime() {
        return timeLabel.getText();
    }

    /**
     * returns all the children node inside each box
     * @return
     */
    public List<ObservableList<Node>> getSubOrders() {
        return subOrdersLabels
                .stream()
                .map(VBox::getChildren)
                .collect(Collectors.toList());
    }

}
