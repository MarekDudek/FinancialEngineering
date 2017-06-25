package interretis.financial_engineering.securities;

import lombok.AllArgsConstructor;
import org.apache.commons.collections.iterators.IteratorChain;

import java.math.BigDecimal;
import java.util.Iterator;

import static interretis.financial_engineering.utilities.IteratorUtilities.constant;
import static interretis.financial_engineering.utilities.NumericUtilities.divide;
import static java.math.BigDecimal.ZERO;
import static java.util.Collections.singletonList;

@AllArgsConstructor
final class Perpetuity {

    private final BigDecimal payment;
    private final BigDecimal rate;

    BigDecimal price()
    {
        return divide(payment, rate);
    }

    @SuppressWarnings("unchecked")
    Iterator<BigDecimal> cashFlow()
    {
        final Iterator<BigDecimal> zero = singletonList(ZERO).iterator();
        final Iterator<BigDecimal> constant = constant(payment);
        return new IteratorChain(zero, constant);
    }
}
