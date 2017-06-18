package interretis.financial_engineering;

import java.math.BigDecimal;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static java.math.BigDecimal.ZERO;
import static java.util.Collections.nCopies;

final class Portfolio {

    private final List<HasCashFlow> contracts;

    Portfolio(final List<HasCashFlow> contracts) {
        this.contracts = contracts;
    }

    CashFlow getCashFlow() {
        final List<BigDecimal> amounts = newArrayList(nCopies(5, ZERO));
        for (final HasCashFlow contract : contracts)
            for (int i = 0; i < 5; i++) {
                final BigDecimal amount = contract.getCashFlow().atTime(i);
                amounts.set(i, amounts.get(i).add(amount));
            }
        return new CashFlow(amounts);
    }
}
