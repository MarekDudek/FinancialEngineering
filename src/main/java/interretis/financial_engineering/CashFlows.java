package interretis.financial_engineering;

import com.google.common.collect.Lists;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Streams.zip;
import static interretis.financial_engineering.Interest.compound;
import static java.math.BigDecimal.ZERO;
import static java.util.Collections.emptyList;
import static java.util.Collections.nCopies;
import static java.util.stream.Collectors.toList;
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
        final Stream<List<BigDecimal>> lists = of(cashFlows).map(
                Lists::newArrayList
        );

        final List<BigDecimal> cashFlow = lists.reduce(
                (c1, c2) -> zip(c1.stream(), c2.stream(), BigDecimal::add).collect(toList())
        ).orElse(
                emptyList()
        );

        return cashFlow.iterator();
    }
}
