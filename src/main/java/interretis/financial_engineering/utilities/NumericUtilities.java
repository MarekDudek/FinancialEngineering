package interretis.financial_engineering.utilities;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.math.RoundingMode.HALF_EVEN;

public final class NumericUtilities {

    private static final RoundingMode DEFAULT_MODE = HALF_EVEN;
    private static final int DEFAULT_SCALE = 6;

    private static BigDecimal number(final String number) {
        return new BigDecimal(number).setScale(DEFAULT_SCALE, DEFAULT_MODE);
    }

    public static BigDecimal amount(final String number) {
        return number(number);
    }

    public static BigDecimal amount(final int number) {
        return new BigDecimal(number).setScale(DEFAULT_SCALE, DEFAULT_MODE);
    }

    public static BigDecimal percent(final String number) {
        return divide(number(number), HUNDRED);
    }

    private static final BigDecimal HUNDRED = number("100");

    public static BigDecimal divide(final BigDecimal nominator, final BigDecimal denominator) {
        return nominator.divide(denominator, DEFAULT_MODE);
    }

    public static final BigDecimal EPSILON = number("0.000001");
    public static final BigDecimal DEFAULT_PRECISION = percent("1");
}
