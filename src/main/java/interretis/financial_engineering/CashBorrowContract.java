package interretis.financial_engineering;

import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static interretis.financial_engineering.Interest.worthAtCompoundInterest;
import static java.math.BigDecimal.ZERO;
import static java.util.Collections.nCopies;

@Deprecated
@AllArgsConstructor
public final class CashBorrowContract implements HasCashFlow {

    private final BigDecimal amount;
    private final int maturity;
    private final BigDecimal rate;

    @Override
    @Deprecated
    public CashFlow getCashFlow() {
        final List<BigDecimal> amounts = newArrayList(nCopies(maturity + 1, ZERO));
        amounts.set(0, amount);
        final BigDecimal worth = worthAtCompoundInterest(amount, rate, maturity);
        amounts.set(maturity, worth.negate());
        return new CashFlow(amounts);
    }

}
