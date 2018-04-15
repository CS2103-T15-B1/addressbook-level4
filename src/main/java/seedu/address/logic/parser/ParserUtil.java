package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.*;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.money.Money;
import seedu.address.model.order.SubOrder;
import seedu.address.model.person.*;
import seedu.address.model.product.Category;
import seedu.address.model.product.ProductName;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 * {@code ParserUtil} contains methods that take in {@code Optional} as parameters. However, it goes against Java's
 * convention (see https://stackoverflow.com/a/39005452) as {@code Optional} should only be used a return type.
 * Justification: The methods in concern receive {@code Optional} return values from other methods as parameters and
 * return {@code Optional} values based on whether the parameters were present. Therefore, it is redundant to unwrap the
 * initial {@code Optional} before passing to {@code ParserUtil} as a parameter and then re-wrap it into an
 * {@code Optional} return value inside {@code ParserUtil} methods.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INSUFFICIENT_PARTS = "Number of parts must be more than 1.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws IllegalValueException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws IllegalValueException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new IllegalValueException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws IllegalValueException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code Optional<String> name} into an {@code Optional<Name>} if {@code name} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Name> parseName(Optional<String> name) throws IllegalValueException {
        requireNonNull(name);
        return name.isPresent() ? Optional.of(parseName(name.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String gender} into a {@code Gender}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code gender} is invalid.
     * @author Sivalavida
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
     * @author Sivalavida
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
     * @author Sivalavida
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
     * @author Sivalavida
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
     * @author Sivalavida
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
     * @author Sivalavida
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
     * @author Sivalavida
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
     * @author Sivalavida
     */
    public static Optional<Longitude> parseLongitude(Optional<String> longitude) throws IllegalValueException {
        requireNonNull(longitude);
        return longitude.isPresent() ? Optional.of(parseLongitude(longitude.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws IllegalValueException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new IllegalValueException(Phone.MESSAGE_PHONE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code Optional<String> phone} into an {@code Optional<Phone>} if {@code phone} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Phone> parsePhone(Optional<String> phone) throws IllegalValueException {
        requireNonNull(phone);
        return phone.isPresent() ? Optional.of(parsePhone(phone.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws IllegalValueException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new IllegalValueException(Address.MESSAGE_ADDRESS_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code Optional<String> address} into an {@code Optional<Address>} if {@code address} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Address> parseAddress(Optional<String> address) throws IllegalValueException {
        requireNonNull(address);
        return address.isPresent() ? Optional.of(parseAddress(address.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws IllegalValueException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new IllegalValueException(Email.MESSAGE_EMAIL_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code Optional<String> email} into an {@code Optional<Email>} if {@code email} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Email> parseEmail(Optional<String> email) throws IllegalValueException {
        requireNonNull(email);
        return email.isPresent() ? Optional.of(parseEmail(email.get())) : Optional.empty();
    }

    /// product-level
    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code name} is invalid.
     */
    public static ProductName parseProductName(String name) throws IllegalValueException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!ProductName.isValidProductName(trimmedName)) {
            throw new IllegalValueException(ProductName.MESSAGE_PRODUCT_NAME_CONSTRAINTS);
        }
        return new ProductName(trimmedName);
    }

    /**
     * Parses a {@code Optional<String> name} into an {@code Optional<Name>} if {@code name} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<ProductName> parseProductName(Optional<String> name) throws IllegalValueException {
        requireNonNull(name);
        return name.isPresent() ? Optional.of(parseProductName(name.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String price} into a {@code price}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code price} is invalid.
     */
    public static Money parsePrice(String price) throws IllegalValueException {
        requireNonNull(price);
        String trimmedPrice = price.trim();
        Currency currency = Money.DEFAULT_CURRENCY;
        if (!Money.isValidMoney(trimmedPrice)) {
            throw new IllegalValueException(Money.MESSAGE_MONEY_CONSTRAINTS);
        } else if (Money.isValidMoneyWithCurrency(trimmedPrice)) {
            String currencySymbol = trimmedPrice.substring(0,1);
            currency = Money.parseCurrency(currencySymbol);
            trimmedPrice = trimmedPrice.substring(1).trim();
        }

        return new Money(new BigDecimal(trimmedPrice), currency);

    }

    /**
     * Parses a {@code Optional<String> price} into an {@code Optional<price>} if {@code price} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Money> parsePrice(Optional<String> price) throws IllegalValueException {
        requireNonNull(price);
        return price.isPresent() ? Optional.of(parsePrice(price.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String category} into a {@code category}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code category} is invalid.
     */
    public static Category parseCategory(String category) throws IllegalValueException {
        requireNonNull(category);
        String trimmedCategory = category.trim();
        if (!Category.isValidCategory(trimmedCategory)) {
            throw new IllegalValueException(Category.MESSAGE_CATEGORY_CONSTRAINTS);
        }
        return new Category(trimmedCategory);
    }

    /**
     * Parses a {@code Optional<String> category} into an {@code Optional<category>} if {@code category} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Category> parseCategory(Optional<String> category) throws IllegalValueException {
        requireNonNull(category);
        return category.isPresent() ? Optional.of(parseCategory(category.get())) : Optional.empty();
    }

    /// tag-level

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws IllegalValueException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new IllegalValueException(Tag.MESSAGE_TAG_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws IllegalValueException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses a {@code String subOrder} into an actual {@code SubOrder}.
     *
     * @throws IllegalValueException if the given {@code subOrder} is invalid.
     */
    public static SubOrder parseSubOrder(String subOrder) throws IllegalValueException {
        requireNonNull(subOrder);
        String trimmed = subOrder.trim();
        String[] components = trimmed.split("\\s");
        int productId;
        int numProduct;
        Money productPrice;
        if(components.length != 3)
            throw new IllegalValueException(SubOrder.MESSAGE_SUBORDER_CONSTRAINTS);
        try {
            productId = Integer.parseInt(components[0]);
            numProduct = Integer.parseInt(components[1]);
            productPrice = Money.parsePrice(components[2]);
        } catch (NumberFormatException e) {
            throw new IllegalValueException(SubOrder.MESSAGE_SUBORDER_CONSTRAINTS);
        }
        return new SubOrder(productId, numProduct, productPrice);
    }

    /**
     * Parses {@code Collection<String> subOrders} into a {@code List<SubOrder>}
     */
    public static List<SubOrder> parseSubOrders(Collection<String> subOrders) throws IllegalValueException {
        requireNonNull(subOrders);
        final List<SubOrder> subOrderList = new ArrayList<>();
        for (String subOrder : subOrders) {
            subOrderList.add(parseSubOrder(subOrder));
        }
        return subOrderList;
    }

    /**
     * Parses {@code String[] ItemIds} into a {@code List<Integer> ItemIds}
     * @author Sivalavida
     */
    public static List<Integer> parseItemIds(String[] ItemIds){
        requireNonNull(ItemIds);
        final List<Integer>  newItemIds = new ArrayList<>();
        for (String itemId : ItemIds) {
            newItemIds.add(Integer.parseInt(itemId));
        }
        return newItemIds;
    }

}
