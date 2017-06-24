package interretis.financial_engineering.securities;

import lombok.AllArgsConstructor;

import java.math.BigDecimal;

import static interretis.financial_engineering.utilities.NumericUtilities.divide;
import static interretis.financial_engineering.utilities.NumericUtilities.pow;
import static java.math.BigDecimal.ONE;

@AllArgsConstructor
public final class Annuity {

    private final BigDecimal payment;
    private final BigDecimal rate;
    private final int maturity;

    public BigDecimal price()
    {
        return payment.multiply(
                divide(
                        ONE.subtract(
                                pow(ONE.add(rate), -maturity)
                        ),
                        rate
                ));
    }
}
