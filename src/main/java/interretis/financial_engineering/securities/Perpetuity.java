package interretis.financial_engineering.securities;

import lombok.AllArgsConstructor;

import java.math.BigDecimal;

import static interretis.financial_engineering.utilities.NumericUtilities.divide;

@AllArgsConstructor
public final class Perpetuity {

    private final BigDecimal payment;
    private final BigDecimal rate;

    public BigDecimal price()
    {
        return divide(payment, rate);
    }
}
