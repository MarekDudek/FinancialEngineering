package interretis.financial_engineering;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.math.RoundingMode.HALF_EVEN;

public final class NumericUtilities {

    private static final RoundingMode MODE = HALF_EVEN;

    public static BigDecimal divide(final BigDecimal nominator, final BigDecimal denominator) {
        return nominator.divide(denominator, MODE);
    }
}
