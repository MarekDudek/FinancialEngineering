package interretis.financial_engineering;

import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.Iterator;

import static interretis.financial_engineering.CashFlows.*;
import static interretis.financial_engineering.Interest.discount;
import static interretis.financial_engineering.utilities.NumericUtilities.*;
import static java.math.BigDecimal.ZERO;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;
import static org.testng.Assert.assertFalse;

public final class PricingASimpleBondTest {

    @Test
    public void cashflow_of_bought_bond()
    {
        // given
        final BigDecimal pv = discount(amount(70), percent(5), 1);
        // when
        final Iterator<BigDecimal> bond = lendAtRatePerPeriod(pv, percent(5), 1);
        // then
        assertThat(bond.next(), is(closeTo(amount(-66.67), CENT)));
        assertThat(bond.next(), is(closeTo(amount(70), CENT)));
        assertFalse(bond.hasNext());
    }

    @Test
    public void cashflow_of_borrowed_cash()
    {
        // given
        final BigDecimal pv = discount(amount(70), percent(5), 1);
        // when
        // when
        final Iterator<BigDecimal> cash = borrowAtRatePerPeriod(pv, percent(5), 1);
        // then
        assertThat(cash.next(), is(closeTo(amount(66.67), CENT)));
        assertThat(cash.next(), is(closeTo(amount(-70), CENT)));
        assertFalse(cash.hasNext());
    }

    @Test
    public void cashflow_of_bond_bought_for_borrowed_cash()
    {
        // given
        final BigDecimal pv = discount(amount(70), percent(5), 1);
        // when
        final Iterator<BigDecimal> bond = lendAtRatePerPeriod(pv, percent(5), 1);
        final Iterator<BigDecimal> cash = borrowAtRatePerPeriod(pv, percent(5), 1);
        // when
        final Iterator<BigDecimal> c = combine(bond, cash);
        // then
        assertThat(c.next(), is(closeTo(ZERO, CENT)));
        assertThat(c.next(), is(closeTo(ZERO, CENT)));
        assertFalse(c.hasNext());
    }

    @Test
    public void cashflow_of_sold_bond()
    {
        // given
        final BigDecimal pv = discount(amount(70), percent(5), 1);
        // when
        final Iterator<BigDecimal> sold = negate(lendAtRatePerPeriod(pv, percent(5), 1));
        // then
        assertThat(sold.next(), is(closeTo(amount(66.67), CENT)));
        assertThat(sold.next(), is(closeTo(amount(-70), CENT)));
        assertFalse(sold.hasNext());
    }

    @Test
    public void cashflow_of_lent_cash()
    {
        // given
        final BigDecimal pv = discount(amount(70), percent(5), 1);
        // when
        // when
        final Iterator<BigDecimal> loan = negate(borrowAtRatePerPeriod(pv, percent(5), 1));
        // then
        assertThat(loan.next(), is(closeTo(amount(-66.67), CENT)));
        assertThat(loan.next(), is(closeTo(amount(70), CENT)));
        assertFalse(loan.hasNext());
    }

    @Test
    public void cashflow_of_bond_sold_and_loan()
    {
        // given
        final BigDecimal pv = discount(amount(70), percent(5), 1);
        // when
        final Iterator<BigDecimal> sold = negate(lendAtRatePerPeriod(pv, percent(5), 1));
        final Iterator<BigDecimal> loan = negate(borrowAtRatePerPeriod(pv, percent(5), 1));
        // when
        final Iterator<BigDecimal> c = combine(sold, loan);
        // then
        assertThat(c.next(), is(closeTo(ZERO, CENT)));
        assertThat(c.next(), is(closeTo(ZERO, CENT)));
        assertFalse(c.hasNext());
    }
}
