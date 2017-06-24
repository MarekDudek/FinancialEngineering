package interretis.financial_engineering.utilities;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.math.RoundingMode.HALF_EVEN;

public final class NumericUtilities {

    // factories

    public static BigDecimal number(final String number) {
        return normalize(new BigDecimal(number));
    }

    public static BigDecimal number(final int number) {
        return normalize(new BigDecimal(number));
    }

    public static BigDecimal number(final double number) {
        return normalize(new BigDecimal(number));
    }

    // normalization

    private static BigDecimal normalize(final BigDecimal number) {
        return number.setScale(DEFAULT_SCALE, DEFAULT_MODE);
    }

    private static final RoundingMode DEFAULT_MODE = HALF_EVEN;
    private static final int DEFAULT_SCALE = 6;

    // roles

    public static BigDecimal amount(final String number) {
        return number(number);
    }

    public static BigDecimal amount(final int number) {
        return number(number);
    }

    public static BigDecimal amount(final double number) {
        return number(number);
    }

    public static BigDecimal percent(final String number) {
        return divide(number(number), HUNDRED);
    }

    public static BigDecimal percent(final int number) {
        return divide(number(number), HUNDRED);
    }

    public static BigDecimal percent(final double number) {
        return divide(number(number), HUNDRED);
    }

    private static final BigDecimal HUNDRED = number("100");

    // operations

    public static BigDecimal divide(final BigDecimal nominator, final BigDecimal denominator) {
        return nominator.divide(denominator, DEFAULT_MODE);
    }

    public static BigDecimal divide(final BigDecimal nominator, final int denominator) {
        return divide(nominator, number(denominator));
    }

    public static BigDecimal multiply(final BigDecimal factor1, final int factor2) {
        return factor1.multiply(number(factor2));
    }

    public static BigDecimal pow(final BigDecimal n, final int exp) {
        return number(Math.pow(n.doubleValue(), exp));
    }

    public static BigDecimal exp(final BigDecimal e) {
        return number(Math.exp(e.doubleValue()));
    }

    // precisions

    public static final BigDecimal EPSILON = number("0.000001");
    public static final BigDecimal ONE_PERCENT = percent(1);
    public static final BigDecimal CENT = amount("0.01");
}
