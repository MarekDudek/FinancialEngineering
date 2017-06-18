package interretis.financial_engineering;

import java.math.BigDecimal;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static interretis.financial_engineering.Interest.worthAtMaturityAtCompoundInterest;
import static java.math.BigDecimal.ZERO;
import static java.util.Collections.nCopies;


public final class CashBorrowContract implements HasCashFlow {

    public final BigDecimal amount;
    public final BigDecimal rate;
    public final int maturity;

    public CashBorrowContract(final BigDecimal amount, final int maturity, final BigDecimal rate) {
        this.amount = amount;
        this.maturity = maturity;
        this.rate = rate;
    }

    @Override
    public CashFlow getCashFlow() {
        final List<BigDecimal> amounts = newArrayList(nCopies(5, ZERO));
        amounts.set(0, amount);
        final BigDecimal worth = worthAtMaturityAtCompoundInterest(amount, maturity, rate);
        amounts.set(maturity, worth.negate());
        return new CashFlow(amounts);
    }
}
