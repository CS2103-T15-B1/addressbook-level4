package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.Person;

import java.util.List;
//@@author Sivalavida
/**
 * Indicates a request to render the locations of querried people on map
 */
public class RenderMapEvent extends BaseEvent {

    private final List<Person> selectedPersons;

    public RenderMapEvent(List<Person> selectedPersons) {
        this.selectedPersons = selectedPersons;
    }

    public List<Person> getSelectedPersons() {
        return selectedPersons;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
