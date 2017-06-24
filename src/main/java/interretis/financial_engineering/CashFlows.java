package interretis.financial_engineering;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static interretis.financial_engineering.Interest.compound;
import static interretis.financial_engineering.Interest.discount;
import static java.math.BigDecimal.ZERO;
import static java.util.Collections.nCopies;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;
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
    public static Iterator<BigDecimal> combine(final Iterator<BigDecimal>... cashFlows)
    {
        final List<BigDecimal> c = newArrayList();

        while (of(cashFlows).anyMatch(Iterator::hasNext))
            c.add(
                    of(cashFlows).filter(
                            Iterator::hasNext
                    ).map(
                            Iterator::next
                    ).reduce(
                            BigDecimal::add
                    ).orElse(
                            ZERO
                    )
            );

        return c.iterator();
    }

    public static Iterator<BigDecimal> negate(final Iterator<BigDecimal> cashFlow)
    {
        return newArrayList(cashFlow).stream().map(
                BigDecimal::negate
        ).collect(toList()).iterator();
    }

    public static BigDecimal presentValue(final Iterator<BigDecimal> cashFlow, final BigDecimal rate) {

        final List<BigDecimal> cf = newArrayList(cashFlow);

        return range(0, cf.size()).mapToObj(
                t -> valueAtTime(cf.get(t), rate, t)
        ).reduce(
                BigDecimal::add
        ).orElse(
                ZERO
        );
    }

    public static BigDecimal valueAtTime(final Iterator<BigDecimal> cashFlow, final BigDecimal rate, final int time)
    {
        final List<BigDecimal> cf = newArrayList(cashFlow);
        return valueAtTime(cf.get(time), rate, time);
    }

    public static BigDecimal valueAtTime(final BigDecimal cash, final BigDecimal rate, final int time)
    {
        return discount(cash, rate, time);
    }
}
