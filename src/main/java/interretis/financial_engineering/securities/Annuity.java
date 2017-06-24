package interretis.financial_engineering.securities;

import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static interretis.financial_engineering.utilities.NumericUtilities.divide;
import static interretis.financial_engineering.utilities.NumericUtilities.pow;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static java.util.Collections.nCopies;

@AllArgsConstructor
public final class Annuity {

    private final BigDecimal payment;
    private final BigDecimal rate;
    private final int maturity;

    BigDecimal price()
    {
        return payment.multiply(
                divide(
                        ONE.subtract(
                                pow(ONE.add(rate), -maturity)
                        ),
                        rate
                ));
    }

    Iterator<BigDecimal> cashFlow()
    {
        final List<BigDecimal> cf = newArrayList(nCopies(maturity + 1, payment));
        cf.set(0, ZERO);
        return cf.iterator();
    }
}
