package interretis.financial_engineering;

import java.math.BigDecimal;

import static com.google.common.base.Preconditions.checkNotNull;
import static interretis.financial_engineering.utilities.NumericUtilities.divide;
import static java.math.BigDecimal.ONE;

public final class ZeroCouponBond {

    private final BigDecimal principal;

    public ZeroCouponBond(final BigDecimal principal) {
        this.principal = checkNotNull(principal);
    }

    public BigDecimal price(final BigDecimal interestRate) {
        return divide(principal, ONE.add(interestRate));
    }
}
