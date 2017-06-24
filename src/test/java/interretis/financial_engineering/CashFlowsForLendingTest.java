package interretis.financial_engineering;

import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.Iterator;

import static interretis.financial_engineering.CashFlows.lendAtRatePerPeriod;
import static interretis.financial_engineering.Interest.discount;
import static interretis.financial_engineering.utilities.NumericUtilities.*;
import static java.math.BigDecimal.ZERO;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertFalse;

public final class CashFlowsForLendingTest {

    @Test
    public void lending_for_one_period()
    {
        // when
        final Iterator<BigDecimal> c = lendAtRatePerPeriod(amount(100), percent(5), 1);
        // then
        assertThat(c.next(), is(equalTo(amount(-100))));
        assertThat(c.next(), is(closeTo(amount(105), CENT)));
        assertFalse(c.hasNext());
    }

    @Test
    public void lending_for_one_period__when_payback_is_given()
    {
        // given
        final BigDecimal loan = discount(amount(100), percent(5), 1);
        // then
        assertThat(loan, is(closeTo(amount(95.24), CENT)));
        // when
        final Iterator<BigDecimal> c = lendAtRatePerPeriod(loan, percent(5), 1);
        // then
        assertThat(c.next(), is(closeTo(amount(-95.24), CENT)));
        assertThat(c.next(), is(closeTo(amount(100), CENT)));
        assertFalse(c.hasNext());
    }

    @Test
    public void lending_for_two_periods()
    {
        // when
        final Iterator<BigDecimal> c = lendAtRatePerPeriod(amount(100), percent(5), 2);
        // then
        assertThat(c.next(), is(equalTo(amount(-100))));
        assertThat(c.next(), is(equalTo(ZERO)));
        assertThat(c.next(), is(closeTo(amount(110.25), CENT)));
        assertFalse(c.hasNext());
    }

    @Test
    public void lending_for_two_periods__when_payback_is_given()
    {
        // given
        final BigDecimal loan = discount(amount(100), percent(5), 2);
        // then
        assertThat(loan, is(closeTo(amount(90.70), CENT)));
        // when
        final Iterator<BigDecimal> c = lendAtRatePerPeriod(loan, percent(5), 2);
        // then
        assertThat(c.next(), is(closeTo(amount(-90.70), CENT)));
        assertThat(c.next(), is(equalTo(ZERO)));
        assertThat(c.next(), is(closeTo(amount(100), CENT)));
        assertFalse(c.hasNext());
    }


    @Test
    public void lending_for_three_periods()
    {
        // when
        final Iterator<BigDecimal> c = lendAtRatePerPeriod(amount(100), percent(5), 3);
        // then
        assertThat(c.next(), is(equalTo(amount(-100))));
        assertThat(c.next(), is(equalTo(ZERO)));
        assertThat(c.next(), is(equalTo(ZERO)));
        assertThat(c.next(), is(closeTo(amount(115.76), CENT)));
        assertFalse(c.hasNext());
    }

    @Test
    public void lending_for_three_periods__when_payback_is_given()
    {
        // given
        final BigDecimal loan = discount(amount(100), percent(5), 3);
        // then
        assertThat(loan, is(closeTo(amount(86.38), CENT)));
        // when
        final Iterator<BigDecimal> c = lendAtRatePerPeriod(loan, percent(5), 3);
        // then
        assertThat(c.next(), is(closeTo(amount(-86.38), CENT)));
        assertThat(c.next(), is(equalTo(ZERO)));
        assertThat(c.next(), is(equalTo(ZERO)));
        assertThat(c.next(), is(closeTo(amount(100), CENT)));
        assertFalse(c.hasNext());
    }
}
