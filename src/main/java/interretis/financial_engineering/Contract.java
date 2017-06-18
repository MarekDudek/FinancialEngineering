package interretis.financial_engineering;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.IntStream;

import static interretis.financial_engineering.CashFlow.currentValue;
import static interretis.financial_engineering.utilities.FunctionalUtilities.sum;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.rangeClosed;

public final class Contract {

    public final CashFlow cashFlow;

    public Contract(final CashFlow cashFlow) {
        this.cashFlow = cashFlow;
    }

    public BigDecimal valueAtTime(final int time, final BigDecimal ratePerPeriod) {
        return currentValue(cashFlow.atTime(time), ratePerPeriod, time);
    }

    public BigDecimal presentValue(final BigDecimal ratePerPeriod) {
        final IntStream time = rangeClosed(0, cashFlow.N());
        final List<BigDecimal> valuesAtTime = time.mapToObj(
                t -> valueAtTime(t, ratePerPeriod)
        ).collect(toList());
        return sum(valuesAtTime);
    }
}
