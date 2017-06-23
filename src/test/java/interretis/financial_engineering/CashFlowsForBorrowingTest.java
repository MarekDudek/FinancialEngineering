package interretis.financial_engineering;

import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.Iterator;

import static interretis.financial_engineering.CashFlows.borrowAtRatePerPeriod;
import static interretis.financial_engineering.Interest.discount;
import static interretis.financial_engineering.utilities.NumericUtilities.*;
import static java.math.BigDecimal.ZERO;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertFalse;

public final class CashFlowsForBorrowingTest {

    private static final BigDecimal RATE = percent(5);
    private static final BigDecimal AMOUNT = amount(100);

    @Test
    @Deprecated
    public void borrowing_up_to_time_1() {
        // given
        final int maturity = 1;
        final BigDecimal borrowed = discount(AMOUNT, RATE, maturity);
        // when
        final CashFlow cashFlow = new CashBorrowContract(borrowed, maturity, RATE).getCashFlow();
        // then
        assertThat(cashFlow.atTime(0), is(equalTo(borrowed)));
        assertThat(cashFlow.atTime(maturity), is(closeTo(AMOUNT.negate(), ONE_PERCENT)));
    }

    @Test
    public void borrowing_for_one_period()
    {
        // when
        final Iterator<BigDecimal> c = borrowAtRatePerPeriod(amount(100), percent(5), 1);
        // then
        assertThat(c.next(), is(equalTo(amount(100))));
        assertThat(c.next(), is(closeTo(amount(-105), CENT)));
        assertFalse(c.hasNext());
    }

    @Test
    public void borrowing_for_one_period__when_payback_is_given()
    {
        // given
        final BigDecimal loan = discount(amount(100), percent(5), 1);
        // then
        assertThat(loan, is(closeTo(amount(95.24), CENT)));
        // when
        final Iterator<BigDecimal> c = borrowAtRatePerPeriod(loan, percent(5), 1);
        // then
        assertThat(c.next(), is(closeTo(amount(95.24), CENT)));
        assertThat(c.next(), is(closeTo(amount(-100), CENT)));
        assertFalse(c.hasNext());
    }

    @Test
    @Deprecated
    public void borrowing_up_to_time_2() {
        // given
        final int maturity = 2;
        final BigDecimal borrowed = discount(AMOUNT, RATE, maturity);
        // when
        final CashFlow cashFlow = new CashBorrowContract(borrowed, maturity, RATE).getCashFlow();
        // then
        assertThat(cashFlow.atTime(0), is(equalTo(borrowed)));
        assertThat(cashFlow.atTime(1), is(equalTo(ZERO)));
        assertThat(cashFlow.atTime(maturity), is(closeTo(AMOUNT.negate(), ONE_PERCENT)));
    }

    @Test
    public void borrowing_for_two_periods()
    {
        // when
        final Iterator<BigDecimal> c = borrowAtRatePerPeriod(amount(100), percent(5), 2);
        // then
        assertThat(c.next(), is(equalTo(amount(100))));
        assertThat(c.next(), is(equalTo(ZERO)));
        assertThat(c.next(), is(closeTo(amount(-110.25), CENT)));
        assertFalse(c.hasNext());
    }

    @Test
    public void borrowing_for_two_periods__when_payback_is_given()
    {
        // given
        final BigDecimal loan = discount(amount(100), percent(5), 2);
        // then
        assertThat(loan, is(closeTo(number(90.70), CENT)));
        // when
        final Iterator<BigDecimal> c = borrowAtRatePerPeriod(loan, percent(5), 2);
        // then
        assertThat(c.next(), is(closeTo(amount(90.70), CENT)));
        assertThat(c.next(), is(equalTo(ZERO)));
        assertThat(c.next(), is(closeTo(amount(-100), CENT)));
        assertFalse(c.hasNext());
    }

    @Test
    @Deprecated
    public void borrowing_up_to_time_3() {
        // given
        final int maturity = 3;
        final BigDecimal borrowed = discount(AMOUNT, RATE, maturity);
        // when
        final CashFlow cashFlow = new CashBorrowContract(borrowed, maturity, RATE).getCashFlow();
        // then
        assertThat(cashFlow.atTime(0), is(equalTo(borrowed)));
        assertThat(cashFlow.atTime(1), is(equalTo(ZERO)));
        assertThat(cashFlow.atTime(2), is(equalTo(ZERO)));
        assertThat(cashFlow.atTime(maturity), is(closeTo(AMOUNT.negate(), ONE_PERCENT)));
    }

    @Test
    public void borrowing_for_three_periods() {
        // when
        final Iterator<BigDecimal> c = borrowAtRatePerPeriod(amount(100), percent(5), 3);
        // then
        assertThat(c.next(), is(equalTo(amount(100))));
        assertThat(c.next(), is(equalTo(ZERO)));
        assertThat(c.next(), is(equalTo(ZERO)));
        assertThat(c.next(), is(closeTo(amount(-115.76), CENT)));
        assertFalse(c.hasNext());
    }

    @Test
    public void borrowing_for_three_periods__when_payback_is_given()
    {
        // given
        final BigDecimal payback = amount(100);
        final BigDecimal amount = discount(payback, percent(5), 3);
        // then
        assertThat(amount, is(closeTo(number(86.38), CENT)));
        // when
        final Iterator<BigDecimal> c = borrowAtRatePerPeriod(amount, percent(5), 3);
        // then
        assertThat(c.next(), is(equalTo(amount)));
        assertThat(c.next(), is(equalTo(ZERO)));
        assertThat(c.next(), is(equalTo(ZERO)));
        assertThat(c.next(), is(closeTo(payback.negate(), CENT)));
        assertFalse(c.hasNext());
    }
}
