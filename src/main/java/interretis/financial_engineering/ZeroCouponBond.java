package interretis.financial_engineering;

import java.math.BigDecimal;

import static interretis.financial_engineering.NumericUtilities.divide;
import static java.math.BigDecimal.ONE;

public final class ZeroCouponBond {

    private final BigDecimal faceValueInAYear;

    public ZeroCouponBond(final BigDecimal faceValueInAYear) {
        this.faceValueInAYear = faceValueInAYear;
    }

    public BigDecimal price(final BigDecimal interestRate) {
        return divide(faceValueInAYear, ONE.add(interestRate));
    }
}
