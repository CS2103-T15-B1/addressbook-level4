package seedu.address.model.money;

import java.util.*;
import java.io.Serializable;
import java.math.BigDecimal;
import static java.math.BigDecimal.ZERO;
import java.math.RoundingMode;

import seedu.address.model.money.exceptions.MismatchedCurrencyException;
import seedu.address.model.money.exceptions.ObjectNotMoneyException;
/**
 * Represent an amount of money in any currency.
 *
 * This class assumes decimal currency, without funky divisions
 * like 1/5 and so on. Money objects are immutable.
 * Most operations involving more than one Money object will throw a
 * MismatchedCurrencyException if the currencies don't match.
 *
 */
public class Money implements Comparable<Money>, Serializable {

    /**
     * The money amount.
     * Never null.
     */
    private BigDecimal fAmount;

    /**
     * The currency of the money, such as US Dollars or Euros.
     * Never null.
     */
    private final Currency fCurrency;

    /**
     * The rounding style to be used.
     */
    private final RoundingMode fRounding;

    /**
     *
     */
    private static BigDecimal DEFAULT_AMOUNT = new BigDecimal(0.00);

    /**
     * The default currency to be used if no currency is passed to the constructor.
     * To be initialized by the static init().
     */
    private static Currency DEFAULT_CURRENCY = Currency.getInstance("SGD");

    /**
     * The default rounding style to be used if no currency is passed to the constructor.
     */
    private static RoundingMode DEFAULT_ROUNDING = RoundingMode.HALF_EVEN;

    private int fHashCode;
    private static final int HASH_SEED = 23;
    private static final int HASH_FACTOR = 37;

    /**
     * Full constructor.
     *
     * @param aAmount is required, can be positive or negative. The number of
     * decimals in the amount cannot exceed the maximum number of
     * decimals for the given Currency.
     * @param aCurrency
     * @param aRoundingStyle is required, must match a rounding style used by
     * BigDecimal.
     */
    public Money(BigDecimal aAmount, Currency aCurrency, RoundingMode aRoundingStyle){
        fAmount = aAmount;
        fCurrency = aCurrency;
        fRounding = aRoundingStyle;
        validateState();
    }

    /**
     * Constructor taking only the money amount.
     * @param aAmount is required, can be positive or negative.
     */
    public Money(BigDecimal aAmount){
        this(aAmount, DEFAULT_CURRENCY, DEFAULT_ROUNDING);
    }

    /**
     * Constructor taking the money amount and currency.
     *
     * The rounding style takes a default value.
     * @param aAmount is required, can be positive or negative.
     * @param aCurrency is required.
     */
    public Money(BigDecimal aAmount, Currency aCurrency){
        this(aAmount, aCurrency, DEFAULT_ROUNDING);
    }

    /**
     * Constructor taking the money amount and the rounding mode.
     * @param aAmount is required, can be positive or negative.
     */
    public Money(BigDecimal aAmount, RoundingMode aRoundingStyle){
        this(aAmount, DEFAULT_CURRENCY, aRoundingStyle);
    }

    /**
     * empty constructor
     */
    public Money() {
        this(DEFAULT_AMOUNT, DEFAULT_CURRENCY, DEFAULT_ROUNDING);
    }
    /** Return the amount passed to the constructor. */
    public BigDecimal getAmount() { return fAmount; }

    /** Return the currency passed to the constructor, or the default currency. */
    public Currency getCurrency() { return fCurrency; }

    /** Return the rounding style passed to the constructor, or the default rounding style. */
    public RoundingMode getRoundingStyle() { return fRounding; }

    /**
     * Return true only if aThat Money has the same currency
     * as this Money. For the public use.
     * Assume the aThat is also a money object
     */
    public boolean isSameCurrencyAs(Money aThat){
        boolean result = false;
        if ( aThat != null ) {
            result = this.fCurrency.equals(aThat.fCurrency);
        }
        return result;
    }

    /** Return true only if the amount is positive. */
    public boolean isPlus(){
        return fAmount.compareTo(ZERO) > 0;
    }

    public boolean isMinus(){
        return fAmount.compareTo(ZERO) <  0;
    }

    public boolean isZero(){
        return fAmount.compareTo(ZERO) ==  0;
    }

    /**
     * Add aThat Money to this Money.
     * Currencies must match.
     */
    public Money plus(Object aThat){
        checkObjectIsMoney(aThat);
        Money that = (Money)aThat;
        checkCurrenciesMatch(that);
        return new Money(fAmount.add(that.fAmount), fCurrency, fRounding);
    }

    /**
     * Subtract aThat Money from this Money.
     * Currencies must match.
     */
    public Money minus(Object aThat){
        checkObjectIsMoney(aThat);
        Money that = (Money)aThat;
        checkCurrenciesMatch(that);
        return new Money(fAmount.subtract(that.fAmount), fCurrency, fRounding);
    }

    /**
     * Sum a collection of Money objects.
     * Currencies must match.
     *
     * @param aMoneys collection of Money objects, all of the same currency.
     * If the collection is empty, then a zero value is returned.
     */
    public static Money sum(Collection<Money> aMoneys){
        Money sum = new Money(ZERO);
        for(Money money : aMoneys){
            sum = sum.plus(money);
        }
        return sum;
    }

    /**
     * Equals (insensitive to scale).
     *
     * Return true only if the amounts are equal.
     * Currencies must match. This method is not synonymous with the equals method.
     */
    public boolean eq(Object aThat){
        checkObjectIsMoney(aThat);
        Money that = (Money)aThat;
        checkCurrenciesMatch(that);
        return compareAmount(that) == 0;
    }

    public boolean gt(Object aThat){
        checkObjectIsMoney(aThat);
        Money that = (Money)aThat;
        checkCurrenciesMatch(that);
        return compareAmount(that) > 0;
    }

    public boolean gteq(Object aThat){
        checkObjectIsMoney(aThat);
        Money that = (Money)aThat;
        checkCurrenciesMatch(that);
        return compareAmount(that) >= 0;
    }

    public boolean lt(Object aThat){
        checkObjectIsMoney(aThat);
        Money that = (Money)aThat;
        checkCurrenciesMatch(that);
        return compareAmount(that) < 0;
    }

    public boolean lteq(Object aThat){
        checkObjectIsMoney(aThat);
        Money that = (Money)aThat;
        checkCurrenciesMatch(that);
        return compareAmount(that) <= 0;
    }

    /**
     * Multiply this Money by an integral factor.
     *
     * The scale of the returned Money is equal to the scale of 'this'
     * Money.
     */
    public Money times(int aFactor){
        BigDecimal factor = new BigDecimal(aFactor);
        BigDecimal newAmount = fAmount.multiply(factor);
        return new Money(newAmount, fCurrency, fRounding);
    }

    /**
     * Multiply this Money by an non-integral factor (having a decimal point).
     */
    public Money times(double aFactor){
        BigDecimal newAmount = fAmount.multiply(asBigDecimal(aFactor));
        newAmount = newAmount.setScale(getNumDecimalsForCurrency(), fRounding);
        return  new Money(newAmount, fCurrency, fRounding);
    }

    /**
     * Divide this Money by an integral divisor.
     *
     * The scale of the returned Money is equal to the scale of
     * 'this' Money since this Money is scale is applied to the new Money.
     */
    public Money div(int aDivisor){
        BigDecimal divisor = new BigDecimal(aDivisor);
        BigDecimal newAmount = fAmount.divide(divisor, fRounding);
        return new Money(newAmount, fCurrency, fRounding);
    }

    /**
     * Divide this Money by an non-integral divisor.
     */
    public Money div(double aDivisor){
        BigDecimal newAmount = fAmount.divide(asBigDecimal(aDivisor), fRounding);
        return new Money(newAmount, fCurrency, fRounding);
    }

    /** Return the absolute value of the amount. */
    public Money abs(){
        return isPlus() ? this : times(-1);
    }

    /** Return the amount x (-1). */
    public Money negate(){
        return times(-1);
    }

    /**
     * Returns
     * getAmount().getPlainString() + space + getCurrency().getSymbol().
     *
     * The return value uses the default locale/currency, and will not
     * always be suitable for display to an end user.
     */
    public String toString(){
        return fAmount.toPlainString() + " " + fCurrency.getSymbol();
    }

    /**
     * This equal is sensitive to scale.
     *
     * For example, 10 is not equal to 10.00
     * The eq method, on the other hand, is not
     * sensitive to scale.
     */
    public boolean equals(Object aThat){
        if (this == aThat) return true;
        if (! (aThat instanceof Money) ) return false;
        Money that = (Money)aThat;
        //the object fields are never null :
        boolean result = (this.fAmount.equals(that.fAmount) );
        result = result && (this.fCurrency.equals(that.fCurrency) );
        result = result && (this.fRounding == that.fRounding);
        return result;
    }

    public int hashCode(){
        if ( fHashCode == 0 ) {
            fHashCode = HASH_SEED;
            fHashCode = HASH_FACTOR * fHashCode + fAmount.hashCode();
            fHashCode = HASH_FACTOR * fHashCode + fCurrency.hashCode();
            fHashCode = HASH_FACTOR * fHashCode + fRounding.hashCode();
        }
        return fHashCode;
    }

    /**
     * Compare by amount, then currency and rounding method.
     * @param aThat
     * @return
     */
    public int compareTo(Money aThat) {
        final int EQUAL = 0;

        if ( this == aThat ) return EQUAL;

        //the object fields are never null
        int comparison = this.fAmount.compareTo(aThat.fAmount);
        if ( comparison != EQUAL ) return comparison;

        comparison = this.fCurrency.getCurrencyCode().compareTo(
                aThat.fCurrency.getCurrencyCode()
        );
        if ( comparison != EQUAL ) return comparison;


        comparison = this.fRounding.compareTo(aThat.fRounding);
        if ( comparison != EQUAL ) return comparison;

        return EQUAL;
    }

    private void validateState(){
        if( fAmount == null ) {
            throw new IllegalArgumentException("Amount cannot be null");
        }
        if( fCurrency == null ) {
            throw new IllegalArgumentException("Currency cannot be null");
        }
        if ( fAmount.scale() > getNumDecimalsForCurrency() ) {
            throw new IllegalArgumentException(
                    "Number of decimals is " + fAmount.scale() + ", but currency only takes " +
                            getNumDecimalsForCurrency() + " decimals."
            );
        }
    }

    private int getNumDecimalsForCurrency(){
        return fCurrency.getDefaultFractionDigits();
    }

    /**
     * throw new exception if the other Monday is not the same currency.
     * @param aThat
     */
    private void checkCurrenciesMatch(Money aThat){
        if (! this.fCurrency.equals(aThat.getCurrency())) {
            throw new MismatchedCurrencyException(
                    aThat.getCurrency() + " doesn't match the expected currency : " + fCurrency
            );
        }
    }

    private void checkObjectIsMoney(Object aThat) {
        if (! (aThat instanceof Money) ) {
            throw new ObjectNotMoneyException(
                    aThat.getClass() + " doesn't match with Money class"
            );
        }
    }

    /** Ignores scale: 0 same as 0.00 */
    private int compareAmount(Money aThat){
        return this.fAmount.compareTo(aThat.fAmount);
    }

    private BigDecimal asBigDecimal(double aDouble){
        String asString = Double.toString(aDouble);
        return new BigDecimal(asString);
    }
} 