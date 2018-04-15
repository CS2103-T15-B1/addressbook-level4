package seedu.address.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import seedu.address.model.product.UniqueProductList;

//@@author qinghao1
public class UniqueProductListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueProductList uniqueProductList = new UniqueProductList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueProductList.asObservableList().remove(0);
    }
}
