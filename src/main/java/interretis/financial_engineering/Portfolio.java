package interretis.financial_engineering;

import java.math.BigDecimal;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static java.lang.Math.max;
import static java.math.BigDecimal.ZERO;
import static java.util.Collections.emptyList;
import static java.util.Collections.nCopies;
import static java.util.stream.Collectors.toList;

final class Portfolio {

    private final List<HasCashFlow> contracts;

    Portfolio(final List<HasCashFlow> contracts) {
        this.contracts = contracts;
    }

    CashFlow getCashFlow() {
        final List<CashFlow> cashFlows = contracts.stream().map(
                HasCashFlow::getCashFlow
        ).collect(toList());
        return cashFlows.stream().reduce(
                new CashFlow(emptyList()),
                (c1, c2) -> {
                    final int maxTime = max(c1.maxTime(), c2.maxTime());
                    final List<BigDecimal> amounts = newArrayList(nCopies(maxTime, ZERO));
                    for (int i = 0; i < maxTime; i++) {
                        final BigDecimal sum = c1.atTime(i).add(c2.atTime(i));
                        amounts.set(i, sum);
                    }
                    return new CashFlow(amounts);
                });
    }
}
