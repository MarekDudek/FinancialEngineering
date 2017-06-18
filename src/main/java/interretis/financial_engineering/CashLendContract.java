package interretis.financial_engineering;

import java.math.BigDecimal;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static interretis.financial_engineering.Interest.worthAtMaturityAtCompoundInterest;
import static java.math.BigDecimal.ZERO;
import static java.util.Collections.nCopies;

public final class CashLendContract implements HasCashFlow {

    public final BigDecimal amount;
    public final int maturity;
    public final BigDecimal rate;

    public CashLendContract(final BigDecimal amount, final int maturity, BigDecimal rate) {
        this.amount = amount;
        this.maturity = maturity;
        this.rate = rate;
    }

    @Override
    public CashFlow getCashFlow() {
        final List<BigDecimal> amounts = newArrayList(nCopies(5, ZERO));
        amounts.set(0, amount.negate());
        final BigDecimal worth = worthAtMaturityAtCompoundInterest(amount, maturity, rate);
        amounts.set(maturity, worth);
        return new CashFlow(amounts);
    }
}
