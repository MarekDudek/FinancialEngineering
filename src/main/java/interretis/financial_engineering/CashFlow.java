package interretis.financial_engineering;

import gnu.trove.map.hash.TIntObjectHashMap;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

import static com.google.common.base.Preconditions.checkNotNull;
import static interretis.financial_engineering.utilities.NumericUtilities.divide;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;

@EqualsAndHashCode
@ToString
public final class CashFlow {

    private final TIntObjectHashMap<BigDecimal> cs;

    public CashFlow(final List<BigDecimal> amounts) {
        checkNotNull(amounts);
        cs = new TIntObjectHashMap<>();
        final ListIterator<BigDecimal> iterator = amounts.listIterator();
        while (iterator.hasNext())
            cs.put(iterator.nextIndex(), iterator.next());
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

    int maxTime() {
        return cs.size() - 1;
    }
}
