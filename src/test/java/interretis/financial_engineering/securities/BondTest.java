package interretis.financial_engineering.securities;

import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.Iterator;

import static com.google.common.collect.Lists.newArrayList;
import static interretis.financial_engineering.CashFlows.presentValue;
import static interretis.financial_engineering.utilities.NumericUtilities.*;
import static java.math.BigDecimal.ZERO;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public final class BondTest {

    @Test
    public void payment_of_bond()
    {
        // given
        final BigDecimal f = amount(1_000);
        final BigDecimal p = amount(1_000);
        final BigDecimal a = percent(5);
        final int m = 10;
        // when
        final Bond annual = new Bond(f, a, m, 1, p);
        // then
        assertThat(annual.payment(), is(closeTo(amount(50), CENT)));
        // when
        final Bond semiAnnual = new Bond(f, a, m, 2, p);
        // then
        assertThat(semiAnnual.payment(), is(closeTo(amount(25), CENT)));
        // when
        final Bond quarterly = new Bond(f, a, m, 4, p);
        // then
        assertThat(quarterly.payment(), is(closeTo(amount(12.5), CENT)));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void cashflow_of_bond()
    {
        // given
        final Bond b = new Bond(amount(1_000), percent(7), 5, 1, amount(0));
        // when
        final Iterator<BigDecimal> cf = b.cashFlow();
        // then
        assertThat(newArrayList(cf), contains(
                equalTo(ZERO),
                closeTo(amount(70), CENT),
                closeTo(amount(70), CENT),
                closeTo(amount(70), CENT),
                closeTo(amount(70), CENT),
                closeTo(amount(1_070), CENT)
        ));
        // when
        final BigDecimal pv = presentValue(b.cashFlow(), percent(5));
        // then
        assertThat(pv, is(closeTo(amount(1_086.59), CENT)));
    }

    @Test
    public void yield_to_maturity()
    {
        // given
        final Bond b = new Bond(amount(1_000), percent(5), 10, 2, amount(900));
        // when
        final BigDecimal price = b.priceForYield(percent(3.18));
        System.err.println(price);
        System.err.println(presentValue(b.cashFlow(), percent(3.18)));
    }
}
