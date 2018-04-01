package seedu.address.ui;

import java.net.URL;
import java.util.List;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.RenderMapEvent;
import seedu.address.model.person.Person;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    public static final String DEFAULT_PAGE = "default.html";
    public static final String PERSON_LOCATION_PAGE_URL = "https://sivalavida.github.io/PersonLocationPage.html?name=%s&lat=%s&lon=%s";
    public static final String SELECTED_PERSON_LOCATION_PAGE_URL = "https://sivalavida.github.io/SelectedPersonsLocationPage.html";

    private static final String FXML = "BrowserPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private WebView browser;

    public BrowserPanel() {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        loadDefaultPage();
        registerAsAnEventHandler(this);
    }

    /**
     * Loads location of person in Google Maps
     */
    private void loadPersonLocationPage(Person person) {
        loadPage(String.format(PERSON_LOCATION_PAGE_URL, person.getName().fullName, person.getLatitude().value,person.getLongitude().value));
    }
    /**
     * Loads location of selected persons in Google Maps
     */
    private void loadSelectedPersonsLocationPage(List<Person> selectedPersons) {
        loadPage(SELECTED_PERSON_LOCATION_PAGE_URL);
//        URL defaultPage = MainApp.class.getResource(FXML_FILE_FOLDER + SELECTED_PERSON_LOCATION_PAGE_URL);
//        loadPage(defaultPage.toExternalForm());
    }

    public void loadPage(String url) {
        Platform.runLater(() -> browser.getEngine().load(url));
    }

    /**
     * Loads a default HTML file with a background that matches the general theme.
     */
    private void loadDefaultPage() {
        URL defaultPage = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        loadPage(defaultPage.toExternalForm());
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        browser = null;
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonLocationPage(event.getNewSelection().person);
    }

    @Subscribe
    private void handleRenderMapEvent(RenderMapEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadSelectedPersonsLocationPage(event.getSelectedPersons());
    }
}
