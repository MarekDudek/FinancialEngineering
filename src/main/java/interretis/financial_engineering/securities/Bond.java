package interretis.financial_engineering.securities;

import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static interretis.financial_engineering.Interest.discount;
import static interretis.financial_engineering.utilities.NumericUtilities.divide;
import static java.math.BigDecimal.ZERO;
import static java.util.Collections.nCopies;
import static java.util.stream.IntStream.rangeClosed;

@AllArgsConstructor
final class Bond {

    private final BigDecimal faceValue;
    private final BigDecimal couponRate;
    private final int maturity;
    private final int paymentsPerYear;
    private final BigDecimal price;

    BigDecimal payment()
    {
        return divide(
                couponRate.multiply(faceValue),
                paymentsPerYear
        );
    }

    Iterator<BigDecimal> cashFlow()
    {
        final List<BigDecimal> c = newArrayList(nCopies(maturity * paymentsPerYear + 1, payment()));
        c.set(0, ZERO);
        c.set(maturity * paymentsPerYear, payment().add(faceValue));
        return c.iterator();
    }

    BigDecimal priceForYield(final BigDecimal lambda)
    {
        final BigDecimal c = rangeClosed(1, paymentsPerYear * maturity).mapToObj(
                k -> discount(payment(), lambda, paymentsPerYear, maturity)
        ).reduce(
                BigDecimal::add
        ).orElse(
                ZERO
        );

        final BigDecimal f = discount(faceValue, lambda, paymentsPerYear, maturity);

        return c.add(f);
    }
}
