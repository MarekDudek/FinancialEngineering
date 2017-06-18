package interretis.financial_engineering;

import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static interretis.financial_engineering.utilities.NumericUtilities.divide;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;


@ToString
public final class CashFlow {

    public final List<BigDecimal> amounts;

    public CashFlow(final List<BigDecimal> amounts) {
        checkNotNull(amounts);
        checkArgument(amounts.size() > 0);
        this.amounts = amounts;
    }

    public static BigDecimal currentValue(final BigDecimal c, final BigDecimal r, final int t) {
        final BigDecimal denominator = ONE.add(r).pow(t);
        return divide(c, denominator);
    }

    public BigDecimal atTime(final int time) {
        if (time >= amounts.size())
            return ZERO;
        return amounts.get(time);
    }

    public int N() {
        return amounts.size() - 1;
    }
}
