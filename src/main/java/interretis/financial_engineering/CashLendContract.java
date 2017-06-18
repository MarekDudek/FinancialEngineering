package interretis.financial_engineering;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static interretis.financial_engineering.Interest.worthAtMaturityAtCompoundInterest;
import static java.math.BigDecimal.ZERO;
import static java.util.Collections.nCopies;

public final class CashLendContract {

    public final BigDecimal amount;
    public final int maturity;

    public CashLendContract(final BigDecimal amount, final int maturity) {
        this.amount = amount;
        this.maturity = maturity;
    }

    public CashFlow cashFlow(final BigDecimal rate) {
        final List<BigDecimal> amounts = new ArrayList<>(nCopies(maturity + 1, ZERO));
        amounts.set(0, amount.negate());
        final BigDecimal worth = worthAtMaturityAtCompoundInterest(amount, maturity, rate);
        amounts.set(maturity, worth);
        return new CashFlow(amounts);
    }
}
