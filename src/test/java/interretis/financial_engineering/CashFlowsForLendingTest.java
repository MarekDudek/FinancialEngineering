package interretis.financial_engineering;

import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.Iterator;

import static com.google.common.collect.Lists.newArrayList;
import static interretis.financial_engineering.CashFlows.lendAtRatePerPeriod;
import static interretis.financial_engineering.Interest.discount;
import static interretis.financial_engineering.utilities.NumericUtilities.*;
import static java.math.BigDecimal.ZERO;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public final class CashFlowsForLendingTest {

    @Test
    @SuppressWarnings("unchecked")
    public void lending_for_one_period()
    {
        // when
        final Iterator<BigDecimal> cf = lendAtRatePerPeriod(amount(100), percent(5), 1);
        // then
        assertThat(newArrayList(cf), contains(
                equalTo(amount(-100)),
                closeTo(amount(105), CENT))
        );
    }

    @Test
    @SuppressWarnings("unchecked")
    public void lending_for_one_period__when_payback_is_given()
    {
        // given
        final BigDecimal loan = discount(amount(100), percent(5), 1);
        // then
        assertThat(loan, is(closeTo(amount(95.24), CENT)));
        // when
        final Iterator<BigDecimal> cf = lendAtRatePerPeriod(loan, percent(5), 1);
        // then
        assertThat(newArrayList(cf), contains(
                closeTo(amount(-95.24), CENT),
                closeTo(amount(100), CENT))
        );
    }

    @Test
    @SuppressWarnings("unchecked")
    public void lending_for_two_periods()
    {
        // when
        final Iterator<BigDecimal> cf = lendAtRatePerPeriod(amount(100), percent(5), 2);
        // then
        assertThat(newArrayList(cf), contains(
                closeTo(amount(-100), CENT),
                equalTo(ZERO),
                closeTo(amount(110.25), CENT))
        );
    }

    @Test
    @SuppressWarnings("unchecked")
    public void lending_for_two_periods__when_payback_is_given()
    {
        // given
        final BigDecimal loan = discount(amount(100), percent(5), 2);
        // then
        assertThat(loan, is(closeTo(amount(90.70), CENT)));
        // when
        final Iterator<BigDecimal> cf = lendAtRatePerPeriod(loan, percent(5), 2);
        // then
        assertThat(newArrayList(cf), contains(
                closeTo(amount(-90.70), CENT),
                equalTo(ZERO),
                closeTo(amount(100), CENT))
        );
    }


    @Test
    @SuppressWarnings("unchecked")
    public void lending_for_three_periods()
    {
        // when
        final Iterator<BigDecimal> cf = lendAtRatePerPeriod(amount(100), percent(5), 3);
        // then
        assertThat(newArrayList(cf), contains(
                closeTo(amount(-100), CENT),
                equalTo(ZERO),
                equalTo(ZERO),
                closeTo(amount(115.76), CENT))
        );
    }

    @Test
    @SuppressWarnings("unchecked")
    public void lending_for_three_periods__when_payback_is_given()
    {
        // given
        final BigDecimal loan = discount(amount(100), percent(5), 3);
        // then
        assertThat(loan, is(closeTo(amount(86.38), CENT)));
        // when
        final Iterator<BigDecimal> cf = lendAtRatePerPeriod(loan, percent(5), 3);
        // then
        assertThat(newArrayList(cf), contains(
                closeTo(amount(-86.38), CENT),
                equalTo(ZERO),
                equalTo(ZERO),
                closeTo(amount(100), CENT))
        );
    }
}
