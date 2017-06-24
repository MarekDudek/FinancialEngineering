package interretis.financial_engineering;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static interretis.financial_engineering.Interest.compound;
import static java.math.BigDecimal.ZERO;
import static java.util.Collections.nCopies;
import static java.util.stream.Stream.of;

public enum CashFlows {

    ;

    public static Iterator<BigDecimal> borrowAtRatePerPeriod(final BigDecimal amount, final BigDecimal rate, final int maturity)
    {
        final List<BigDecimal> flows = newArrayList(nCopies(maturity + 1, ZERO));

        flows.set(0, amount);

        final BigDecimal compound = compound(amount, rate, 1, maturity);
        final BigDecimal payback = compound.negate();
        flows.set(maturity, payback);

        return flows.iterator();
    }

    public static Iterator<BigDecimal> lendAtRatePerPeriod(final BigDecimal amount, final BigDecimal rate, final int maturity)
    {

        final List<BigDecimal> flows = newArrayList(nCopies(maturity + 1, ZERO));

        final BigDecimal loan = amount.negate();
        flows.set(0, loan);

        final BigDecimal compound = compound(amount, rate, 1, maturity);
        flows.set(maturity, compound);

        return flows.iterator();
    }

    @SafeVarargs
    public static Iterator<BigDecimal> portfolio(final Iterator<BigDecimal>... cashFlows)
    {
        final List<BigDecimal> cashFlow = newArrayList();

        while (of(cashFlows).anyMatch(Iterator::hasNext)) {
            final BigDecimal next = of(cashFlows).filter(
                    Iterator::hasNext
            ).map(Iterator::next).reduce(
                    BigDecimal::add
            ).orElse(
                    ZERO
            );
            cashFlow.add(next);
        }

        return cashFlow.iterator();
    }
}
