package interretis.financial_engineering;

import java.math.BigDecimal;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public final class CashFlow {

    public final List<BigDecimal> amounts;

    public CashFlow(final List<BigDecimal> amounts) {
        checkNotNull(amounts);
        checkArgument(amounts.size() > 0);
        this.amounts = amounts;
    }

    public BigDecimal atTime(final int time) {
        return amounts.get(time);
    }

    public int N() {
        return amounts.size() - 1;
    }
}
