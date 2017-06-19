package interretis.financial_engineering;

import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

import static java.lang.Math.max;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.rangeClosed;

@AllArgsConstructor
final class Portfolio {

    private final List<HasCashFlow> contracts;

    CashFlow getCashFlow() {
        final List<CashFlow> cashFlows = contracts.stream().map(
                HasCashFlow::getCashFlow
        ).collect(toList());
        return cashFlows.stream().reduce(
                new CashFlow(emptyList()),
                (c1, c2) -> {
                    final int maxTime = max(c1.maxTime(), c2.maxTime());
                    final List<BigDecimal> amounts = rangeClosed(0, maxTime).mapToObj(
                            t -> c1.atTime(t).add(c2.atTime(t))
                    ).collect(toList());
                    return new CashFlow(amounts);
                });
    }
}
