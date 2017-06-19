package interretis.financial_engineering;

import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.IntStream;

import static interretis.financial_engineering.CashFlow.currentValue;
import static interretis.financial_engineering.utilities.FunctionalUtilities.sum;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.rangeClosed;

@AllArgsConstructor
public final class Contract implements HasCashFlow {

    private final CashFlow cashFlow;

    BigDecimal valueAtTime(final int t, final BigDecimal r) {
        final BigDecimal c = cashFlow.atTime(t);
        return currentValue(c, r, t);
    }

    BigDecimal presentValue(final BigDecimal r) {
        final IntStream ts = rangeClosed(0, cashFlow.maxTime());
        final List<BigDecimal> valuesAtTime = ts.mapToObj(
                t -> valueAtTime(t, r)
        ).collect(toList());
        return sum(valuesAtTime);
    }

    @Override
    public CashFlow getCashFlow() {
        return cashFlow;
    }
}
