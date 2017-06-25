package interretis.financial_engineering.securities;

import lombok.AllArgsConstructor;
import org.apache.commons.collections.iterators.IteratorChain;

import java.math.BigDecimal;
import java.util.Iterator;

import static com.google.common.collect.Lists.newArrayList;
import static interretis.financial_engineering.utilities.NumericUtilities.divide;
import static interretis.financial_engineering.utilities.NumericUtilities.pow;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static java.util.Collections.nCopies;
import static java.util.Collections.singletonList;

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

    @SuppressWarnings("unchecked")
    Iterator<BigDecimal> cashFlow()
    {
        final Iterator<BigDecimal> zero = singletonList(ZERO).iterator();
        final Iterator<BigDecimal> payments = newArrayList(nCopies(maturity, payment)).iterator();
        return new IteratorChain(zero, payments);
    }
}
