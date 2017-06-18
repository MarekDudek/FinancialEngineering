package interretis.financial_engineering;

import java.math.BigDecimal;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static java.math.BigDecimal.ZERO;
import static java.util.Collections.nCopies;

public final class Portfolio {

    public final List<HasCashFlow> contracts;

    public Portfolio(final List<HasCashFlow> contracts) {
        this.contracts = contracts;
    }

    public CashFlow getCashFlow() {
        final List<BigDecimal> amounts = newArrayList(nCopies(5, ZERO));
        for (final HasCashFlow contract : contracts)
            for (int i = 0; i < 5; i++) {
                final BigDecimal amount = contract.getCashFlow().atTime(i);
                amounts.set(i, amounts.get(i).add(amount));
            }
        return new CashFlow(amounts);
    }
}
