package seedu.address.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import seedu.address.model.order.UniqueOrderList;

//@@author qinghao1
public class UniqueOrderListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueOrderList uniqueOrderList = new UniqueOrderList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueOrderList.asObservableList().remove(0);
    }
}
