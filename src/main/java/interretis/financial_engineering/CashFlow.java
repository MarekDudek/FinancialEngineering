package interretis.financial_engineering;

import gnu.trove.map.hash.TIntObjectHashMap;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static interretis.financial_engineering.utilities.NumericUtilities.divide;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;


@ToString
public final class CashFlow {

    private final TIntObjectHashMap<BigDecimal> cs;

    public CashFlow(final List<BigDecimal> amounts) {

        checkNotNull(amounts);
        checkArgument(amounts.size() > 0);

        cs = new TIntObjectHashMap<>();

        final ListIterator<BigDecimal> iterator = amounts.listIterator();
        while (iterator.hasNext())
            cs.put(iterator.nextIndex(), iterator.next());
    }


    static BigDecimal currentValue(final BigDecimal cashFlow, final BigDecimal rate, final int time) {
        return divide(
                cashFlow,
                ONE.add(rate).pow(time)
        );
    }

    BigDecimal atTime(final int time) {
        if (cs.containsKey(time))
            return cs.get(time);
        else
            return ZERO;
    }

    Collection<BigDecimal> amounts() {
        return cs.valueCollection();
    }

    int length() {
        return cs.size() - 1;
    }
}
