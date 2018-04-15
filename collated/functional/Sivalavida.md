# Sivalavida
###### /java/seedu/address/ui/BrowserPanel.java
``` java
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
        String completeUrl = SELECTED_PERSON_LOCATION_PAGE_URL;
        boolean firstPerson = true;
        for (Person person:selectedPersons){
            if (!firstPerson){
                completeUrl += "&";
            }else{
                firstPerson = false;
            }
            completeUrl += String.format("lat=%s,%s",person.getLatitude().value,person.getLongitude().value);
        }
        loadPage(completeUrl);
    }
```
###### /java/seedu/address/ui/BrowserPanel.java
``` java
    @Subscribe
    private void handleRenderMapEvent(RenderMapEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadSelectedPersonsLocationPage(event.getSelectedPersons());
    }
}
```
###### /java/seedu/address/commons/events/ui/RenderMapEvent.java
``` java
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
```
###### /java/seedu/address/logic/parser/AddressBookParser.java
``` java
        case MapCommand.COMMAND_WORD:
            return new MapCommandParser().parse(arguments);
```
###### /java/seedu/address/logic/parser/ParserUtil.java
``` java
    /**
     * Parses a {@code String gender} into a {@code Gender}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code gender} is invalid.
     */
    public static Gender parseGender(String gender) throws IllegalValueException {
        requireNonNull(gender);
        String trimmedGender = gender.trim();
        if (!Gender.isValidGender(trimmedGender)) {
            throw new IllegalValueException(Gender.MESSAGE_GENDER_CONSTRAINTS);
        }
        return new Gender(trimmedGender);
    }

    /**
     * Parses a {@code Optional<String> gender} into an {@code Optional<Gender>} if {@code gender} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     *
     */
    public static Optional<Gender> parseGender(Optional<String> gender) throws IllegalValueException {
        requireNonNull(gender);
        return gender.isPresent() ? Optional.of(parseGender(gender.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String age} into a {@code Age}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code age} is invalid.
     *
     */
    public static Age parseAge(String age) throws IllegalValueException {
        requireNonNull(age);
        String trimmedAge = age.trim();
        if (!Age.isValidAge(trimmedAge)) {
            throw new IllegalValueException(Age.MESSAGE_AGE_CONSTRAINTS);
        }
        return new Age(trimmedAge);
    }

    /**
     * Parses a {@code Optional<String> age} into an {@code Optional<Age>} if {@code age} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     *
     */
    public static Optional<Age> parseAge(Optional<String> age) throws IllegalValueException {
        requireNonNull(age);
        return age.isPresent() ? Optional.of(parseAge(age.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String latitude} into a {@code Latitude}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code latitude} is invalid.
     *
     */
    public static Latitude parseLatitude(String latitude) throws IllegalValueException {
        requireNonNull(latitude);
        String trimmedLatitude = latitude.trim();
        if (!Latitude.isValidLatitude(trimmedLatitude)) {
            throw new IllegalValueException(Latitude.MESSAGE_LATITUDE_CONSTRAINTS);
        }
        return new Latitude(trimmedLatitude);
    }

    /**
     * Parses a {@code Optional<String> latitude} into an {@code Optional<Latitude>} if {@code latitude} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     *
     */
    public static Optional<Latitude> parseLatitude(Optional<String> latitude) throws IllegalValueException {
        requireNonNull(latitude);
        return latitude.isPresent() ? Optional.of(parseLatitude(latitude.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String longitude} into a {@code Longitude}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code longitude} is invalid.
     *
     */
    public static Longitude parseLongitude(String longitude) throws IllegalValueException {
        requireNonNull(longitude);
        String trimmedLongitude = longitude.trim();
        if (!Longitude.isValidLongitude(trimmedLongitude)) {
            throw new IllegalValueException(Longitude.MESSAGE_LONGITUDE_CONSTRAINTS);
        }
        return new Longitude(trimmedLongitude);
    }

    /**
     * Parses a {@code Optional<String> longitude} into an {@code Optional<Longitude>} if {@code longitude} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     *
     */
    public static Optional<Longitude> parseLongitude(Optional<String> longitude) throws IllegalValueException {
        requireNonNull(longitude);
        return longitude.isPresent() ? Optional.of(parseLongitude(longitude.get())) : Optional.empty();
    }
```
###### /java/seedu/address/logic/parser/MapCommandParser.java
``` java
/**
 * Parses input arguments and creates a new MapCommand object
 */
public class MapCommandParser implements Parser<MapCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the MapCommand
     * and returns an MapCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MapCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MapCommand.MESSAGE_USAGE));
        }
        String[] queryKeywords = trimmedArgs.split("\\s+");
        String queryWord = queryKeywords[0];
        String[] queryParameters = Arrays.copyOfRange(queryKeywords, 1, queryKeywords.length);

        if(!isValidQueryType(queryWord) || !isValidQueryParameters(queryWord, queryParameters)){
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MapCommand.MESSAGE_USAGE));
        }

        Predicate<Person> predicate = getPredicate(queryWord, queryParameters);
        return new MapCommand(predicate);
    }

    /**
     *Checks if the given queryParameters are conform to the format of the queryWord
     */
    private boolean isValidQueryParameters(String queryWord, String[] queryParameters) {
        int queryParametersLength = queryParameters.length;
        switch (queryWord){
            case ALL_PERSONS_QUERY_WORD:
                return queryParametersLength == ALL_PERSONS_QUERY_NUM_PARAMETERS;
            case NAME_CONTAINS_KEYWORDS_QUERY_WORD:
                return queryParametersLength >= NAME_CONTAINS_KEYWORDS_QUERY_MIN_NUM_PARAMETERS ;
            case WITHIN_AGE_RANGE_QUERY_WORD:
                return (queryParametersLength == WITHIN_AGE_RANGE_QUERY_NUM_PARAMETERS&&
                        isValidAge(queryParameters[0])&&
                        isValidAge(queryParameters[1]));
            case GENDER_QUERY_WORD:
                return queryParametersLength == IS_GENDER_QUERY_NUM_PARAMETERS &&
                        isValidGender(queryParameters[0]);
            case CONTAINS_TAG_QUERY_WORD:
                return queryParametersLength >= CONTAINS_TAG_QUERY_NUM_PARAMETERS;
            default:
                return false;//unknown query
        }
    }

    /**
     * Assumes that the queryWord and queryParameters are valid and returns the corresponding
     * predicate
     */
    private Predicate getPredicate(String queryWord, String[] queryParameters) {
        switch (queryWord){
            case ALL_PERSONS_QUERY_WORD:
                return PREDICATE_SHOW_ALL_PERSONS;
            case NAME_CONTAINS_KEYWORDS_QUERY_WORD:
                return new NameContainsKeywordsPredicate(Arrays.asList(queryParameters)) ;
            case WITHIN_AGE_RANGE_QUERY_WORD:
                return new AgeWithinRangePredicate(new Age(queryParameters[0]), new Age(queryParameters[1]));
            case GENDER_QUERY_WORD:
                return new GenderPredicate(new Gender(queryParameters[0]));
            case CONTAINS_TAG_QUERY_WORD:
                return new ContainsTagPredicate(Arrays.asList(queryParameters));
            default:
                return null;//unknown query
        }
    }

    private boolean isValidQueryType(String queryWord) {
        return queryWord.equals(ALL_PERSONS_QUERY_WORD) ||
                queryWord.equals(NAME_CONTAINS_KEYWORDS_QUERY_WORD) ||
                queryWord.equals(WITHIN_AGE_RANGE_QUERY_WORD) ||
                queryWord.equals(GENDER_QUERY_WORD) ||
                queryWord.equals(CONTAINS_TAG_QUERY_WORD);
    }


}
```
###### /java/seedu/address/logic/commands/MapCommand.java
``` java
/**
 * Displays the geographic distribution of queried customers in Retail Analytics.
 */
public class MapCommand extends Command {

    public static final String COMMAND_WORD = "map";

    //query words
    public static final String ALL_PERSONS_QUERY_WORD = "all";
    public static final String NAME_CONTAINS_KEYWORDS_QUERY_WORD = "name";
    public static final String WITHIN_AGE_RANGE_QUERY_WORD = "age";
    public static final String GENDER_QUERY_WORD = "gen";
    public static final String CONTAINS_TAG_QUERY_WORD = "tag";

    //number of parameters (including query word) for respective query words
    public static final int ALL_PERSONS_QUERY_NUM_PARAMETERS = 0;
    public static final int NAME_CONTAINS_KEYWORDS_QUERY_MIN_NUM_PARAMETERS = 1; //min
    public static final int WITHIN_AGE_RANGE_QUERY_NUM_PARAMETERS = 2;
    public static final int IS_GENDER_QUERY_NUM_PARAMETERS = 1;
    public static final int CONTAINS_TAG_QUERY_NUM_PARAMETERS = 1; //min

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays the geographic distribution of queried customers in Retail Analytics with clustering.\n"
            + "Query 1) " + ALL_PERSONS_QUERY_WORD + ": Select all customers - Parameters: none \n"
            + "Examples: " + COMMAND_WORD + " " + ALL_PERSONS_QUERY_WORD + "\n"
            + "Query 2) " + NAME_CONTAINS_KEYWORDS_QUERY_WORD + ": Select customer if keyword in name  - Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Examples: " + COMMAND_WORD + " " + NAME_CONTAINS_KEYWORDS_QUERY_WORD + " Alex Bernice\n"
            + "Query 3) " + WITHIN_AGE_RANGE_QUERY_WORD + ": Select customer if age within age range - Parameters: MIN_AGE MAX_AGE\n"
            + "Examples: " + COMMAND_WORD + " " + WITHIN_AGE_RANGE_QUERY_WORD + " 15 56\n"
            + "Query 4) " + GENDER_QUERY_WORD + ": Select customers of specified gender - Parameters: GENDER\n"
            + "Examples: " + COMMAND_WORD + " " + GENDER_QUERY_WORD + " M\n"
            + "Query 5) " + CONTAINS_TAG_QUERY_WORD + ": Select customer with any of the specified tags - Parameters: TAG\n"
            + "Examples: " + COMMAND_WORD + " " + CONTAINS_TAG_QUERY_WORD + " friends";

    public static final String MESSAGE_MAP_PERSON_SUCCESS = "Number of customers displayed on map: %1$s";

    private final Predicate<Person> predicate;

    public MapCommand(Predicate<Person> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        List<Person> updatedPersonList = model.getFilteredPersonList();//see find command when using query
        EventsCenter.getInstance().post(new RenderMapEvent(updatedPersonList));
        return new CommandResult(String.format(MESSAGE_MAP_PERSON_SUCCESS, updatedPersonList.size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MapCommand // instanceof handles nulls
                && this.predicate.equals(((MapCommand) other).predicate)); // state check
    }
}
```
###### /java/seedu/address/model/order/ContainsItemPredicate.java
``` java
/**
 * Tests that a {@code Order} contains {@code } matches any of the keywords given.
 */
public class ContainsItemPredicate implements Predicate<Order> {
    private final List<Integer> productIds;

    public ContainsItemPredicate(List<Integer> productIds) {
        this.productIds = productIds;
    }

    @Override
    public boolean test(Order order) {
        for(SubOrder suborder:order.getSubOrders()){
            for(int productId: productIds){
                if (suborder.getProductID() == productId){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ContainsItemPredicate // instanceof handles nulls
                && this.productIds.equals(((ContainsItemPredicate) other).productIds)); // state check
    }

}

```
###### /java/seedu/address/model/person/AgeWithinRangePredicate.java
``` java
/**
 * Tests that a {@code Person}'s {@code Age} is within the range given.
 */
public class AgeWithinRangePredicate implements Predicate<Person> {
    private final Age minimumAge;
    private final Age maximumAge;

    public AgeWithinRangePredicate(Age mininumAge, Age maximumAge) {
        this.minimumAge = mininumAge;
        this.maximumAge = maximumAge;
    }

    @Override
    public boolean test(Person person) {
        return (minimumAge.compareTo(person.getAge())>=0) &&
                (maximumAge.compareTo(person.getAge())<=0);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AgeWithinRangePredicate // instanceof handles nulls
                && this.maximumAge == ((AgeWithinRangePredicate) other).maximumAge
                && this.maximumAge == ((AgeWithinRangePredicate) other).maximumAge); // state check
    }

}
```
###### /java/seedu/address/model/person/Age.java
``` java
/**
 * Represents a Person's age in Retail Analytics.
 * Guarantees: immutable; is valid as declared in {@link #isValidAge(String)}
 */
public class Age implements Comparable<Age>{

    /**
     * The minimum allowed age
     */
    public static int MIN_AGE = 1;

    /**
     * The maximum allowed age
     */
    public static int MAX_AGE = 120;

    public static final String MESSAGE_AGE_CONSTRAINTS =
            String.format("Age must be an integer between %d and %d", MIN_AGE, MAX_AGE);
    public static final String AGE_VALIDATION_REGEX = "\\d{1,3}";
    public final String value;

    /**
     * Constructs a {@code Age}.
     *
     * @param age A valid age of a person.
     */
    public Age(String age) {
        requireNonNull(age);
        checkArgument(isValidAge(age), MESSAGE_AGE_CONSTRAINTS);
        this.value = age;
    }

    /**
     * Returns true if a given string is a valid age of a person
     */
    public static boolean isValidAge(String test) {
        if (test.matches(AGE_VALIDATION_REGEX)){
            int age = Integer.parseInt(test);
            if(age>=MIN_AGE && age<=MAX_AGE){
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the int value of the persons Age
     */
    private int getNumericalAge() {
        return Integer.parseInt(value);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Age // instanceof handles nulls
                && this.value.equals(((Age) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public int compareTo(Age other) {
        return (other.getNumericalAge() - this.getNumericalAge());
    }
}
```
###### /java/seedu/address/model/person/Longitude.java
``` java
/**
 * Represents a Person's Latitude in the Retail Analytics.
 * Guarantees: immutable; is valid as declared in {@link #isValidLongitude(String)}
 */
public class Longitude {

    /**
     * The minimum allowed longitude
     */
    public static float MIN_LONGITUDE = Float.valueOf("-180.0000");

    /**
     * The maximum allowed longitude
     */
    public static float MAX_LONGITUDE = Float.valueOf("180.0000");

    public static final String MESSAGE_LONGITUDE_CONSTRAINTS =
            String.format("Latitude numbers must be a decimal value between %f and %f", MIN_LONGITUDE, MAX_LONGITUDE);
    public static final String LONGITUDE_VALIDATION_REGEX = "-?\\d+\\.?\\d*";
    private static final DecimalFormat format = new DecimalFormat("000.000000");

    public final String value;

    /**
     * Constructs a {@code Longitude}.
     *
     * @param longitude A valid longitude number.
     */
    public Longitude(String longitude) {
        requireNonNull(longitude);
        checkArgument(isValidLongitude(longitude), MESSAGE_LONGITUDE_CONSTRAINTS);
        this.value = format.format(Double.parseDouble(longitude));
    }

    /**
     * return true if {@code test} is a decimal value and within the MIN and MAX longitude
     */
    public static boolean isValidLongitude(String test) {
        if (test.matches(LONGITUDE_VALIDATION_REGEX)) {
            Float longitude = Float.parseFloat(test);
            if (longitude >= MIN_LONGITUDE && longitude <= MAX_LONGITUDE) {
                return true;
            }
        }
        return false;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Longitude // instanceof handles nulls
                && this.value.equals(((Longitude) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### /java/seedu/address/model/person/Person.java
``` java
    public Gender getGender() {
        return gender;
    }

    public Latitude getLatitude() {
        return latitude;
    }

    public Longitude getLongitude() {
        return longitude;
    }

    public Age getAge() {
        return age;
    }
  
    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags.toSet());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return otherPerson.getName().equals(this.getName())
                && otherPerson.getPhone().equals(this.getPhone())
                && otherPerson.getEmail().equals(this.getEmail())
                && otherPerson.getAddress().equals(this.getAddress())
                && otherPerson.getGender().equals(this.getGender())
                && otherPerson.getAge().equals(this.getAge())
                && otherPerson.getLatitude().equals(this.getLatitude())
                && otherPerson.getLongitude().equals(this.getLongitude())
                ;
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, gender, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Address: ")
                .append(getAddress())
                .append(" Gender: ")
                .append(getGender())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }


}
```
###### /java/seedu/address/model/person/GenderPredicate.java
``` java
/**
 * Tests that a {@code Person}'s {@code Age} is within the range given.
 */
public class GenderPredicate implements Predicate<Person> {
    private final Gender gender;

    public GenderPredicate(Gender gender) {
        this.gender = gender;
    }

    @Override
    public boolean test(Person person) {
        return (gender.equals(person.getGender()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GenderPredicate // instanceof handles nulls
                && this.gender == ((GenderPredicate) other).gender); // state check
    }

}
```
###### /java/seedu/address/model/person/Gender.java
``` java
/**
 * Represents a Person's gender in the retail analytics.
 * Guarantees: immutable; is valid as declared in {@link #isValidGender(String)}
 */
public class Gender {

    public static final String MESSAGE_GENDER_CONSTRAINTS =
            "Gender should only be 'M' or 'F', and it should not be blank";

    public static final String MALE_SHORTFORM = "M";
    public static final String FEMALE_SHORTFORM = "F";

    public String value;//either MALE_SHORTFORM or FEMALE_SHORTFORM

    /**
     * Constructs a {@code Gender}.
     * @param gender A valid gender (ie "M" or "F") (case insensitive).
     */
    public Gender(String gender) {
        requireNonNull(gender);
        checkArgument(isValidGender(gender), MESSAGE_GENDER_CONSTRAINTS);
        setGender(gender);
    }

    /**
     * Sets {@code value} to the uppercase value of {@code gender}
     * @param gender a valid gender
     */
    private void setGender(String gender) {
        assert isValidGender(gender);

        String genderUpperCase = gender.toUpperCase();
        if(genderUpperCase.equals(MALE_SHORTFORM)){
            value = MALE_SHORTFORM;
        } else if(genderUpperCase.equals(FEMALE_SHORTFORM)){
            value = FEMALE_SHORTFORM;
        }
    }

    /**
     * Returns true if a given string is a valid person gender.
     */
    public static boolean isValidGender(String test) {
        String testUpperCase = test.toUpperCase();
        return testUpperCase.equals(MALE_SHORTFORM) || testUpperCase.equals(FEMALE_SHORTFORM);
    }


    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Gender // instanceof handles nulls
                && this.value.equals(((Gender) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### /java/seedu/address/model/person/Latitude.java
``` java
/**
 * Represents a Person's Latitude in the Retail Analytics.
 * Guarantees: immutable; is valid as declared in {@link #isValidLatitude(String)}
 */
public class Latitude {

    /**
     * The minimum allowed latitude
     */
    public static float MIN_LATITUDE = Float.valueOf("-90.0000");

    /**
     * The maximum allowed latitude
     */
    public static float MAX_LATITUDE = Float.valueOf("90.0000");

    public static final String MESSAGE_LATITUDE_CONSTRAINTS =
            String.format("Latitude numbers must be a decimal value between %f and %f", MIN_LATITUDE, MAX_LATITUDE);
    public static final String LATITUDE_VALIDATION_REGEX = "-?\\d+\\.?\\d*";
    private static final DecimalFormat format = new DecimalFormat("00.000000");

    public final String value;

    /**
     * Constructs a {@code Latitude}.
     *
     * @param latitude A valid latitude number.
     */
    public Latitude(String latitude) {
        requireNonNull(latitude);
        checkArgument(isValidLatitude(latitude), MESSAGE_LATITUDE_CONSTRAINTS);
        this.value = format.format(Double.parseDouble(latitude));
    }

    /**
     * A method to check if latitude value is valid
     * @param test the latitude to check is valid
     * @return true if test is a decimal value and within the MIN and MAX latitude
     */
    public static boolean isValidLatitude(String test) {
        if (test.matches(LATITUDE_VALIDATION_REGEX)) {
            Float latitude = Float.parseFloat(test);
            if (latitude >= MIN_LATITUDE && latitude <= MAX_LATITUDE) {
                return true;
            }
        }
        return false;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Latitude // instanceof handles nulls
                && this.value.equals(((Latitude) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### /java/seedu/address/model/person/ContainsTagPredicate.java
``` java
/**
 * Tests if a {@code Person} contains a {@code Tag}'s which
 * matches any of the  given tags.
 */
public class ContainsTagPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public ContainsTagPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        for(Tag tag:person.getTags()){
            for(String keyword: keywords){
                if (tag.tagName.equals(keyword)){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ContainsTagPredicate // instanceof handles nulls
                && this.keywords.equals(((ContainsTagPredicate) other).keywords)); // state check
    }

}
```
