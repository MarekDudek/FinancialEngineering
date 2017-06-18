package interretis.financial_engineering;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.IntStream;

import static interretis.financial_engineering.utilities.FunctionalUtilities.sum;
import static interretis.financial_engineering.utilities.NumericUtilities.divide;
import static java.math.BigDecimal.ONE;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.rangeClosed;

public final class Contract {

    public final BigDecimal price;
    public final CashFlow cashFlow;

    public Contract(final BigDecimal price, final CashFlow cashFlow) {
        this.price = price;
        this.cashFlow = cashFlow;
    }

    public BigDecimal valueAtTime(final int time, final BigDecimal ratePerPeriod) {
        final BigDecimal nominator = cashFlow.atTime(0);
        final BigDecimal rate = ONE.add(ratePerPeriod);
        final BigDecimal denominator = rate.pow(time);
        return divide(nominator, denominator);
    }

    public BigDecimal presentValue(final BigDecimal ratePerPeriod) {
        final IntStream time = rangeClosed(0, cashFlow.N());
        final List<BigDecimal> valuesAtTime = time.mapToObj(
                t -> valueAtTime(t, ratePerPeriod)
        ).collect(toList());
        return sum(valuesAtTime);
    }
}
