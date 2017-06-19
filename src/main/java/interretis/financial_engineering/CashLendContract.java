package interretis.financial_engineering;

import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static interretis.financial_engineering.Interest.worthAtCompoundInterest;
import static java.math.BigDecimal.ZERO;
import static java.util.Collections.nCopies;

@AllArgsConstructor
public final class CashLendContract implements HasCashFlow {

    private final BigDecimal amount;
    private final int maturity;
    private final BigDecimal rate;

    @Override
    public CashFlow getCashFlow() {
        final List<BigDecimal> amounts = newArrayList(nCopies(maturity + 1, ZERO));
        amounts.set(0, amount.negate());
        final BigDecimal worth = worthAtCompoundInterest(amount, rate, maturity);
        amounts.set(maturity, worth);
        return new CashFlow(amounts);
    }
}
