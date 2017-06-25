package interretis.financial_engineering;

import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.Iterator;

import static com.google.common.collect.Lists.newArrayList;
import static interretis.financial_engineering.CashFlows.*;
import static interretis.financial_engineering.Interest.discount;
import static interretis.financial_engineering.utilities.NumericUtilities.*;
import static java.math.BigDecimal.ZERO;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.contains;

public final class PricingASimpleBondTest {

    @Test
    @SuppressWarnings("unchecked")
    public void cashflow_of_bought_bond()
    {
        // given
        final BigDecimal pv = discount(amount(70), percent(5), 1);
        // when
        final Iterator<BigDecimal> bond = lendAtRatePerPeriod(pv, percent(5), 1);
        // then
        assertThat(newArrayList(bond), contains(
                closeTo(amount(-66.67), CENT),
                closeTo(amount(70), CENT)
        ));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void cashflow_of_borrowed_cash()
    {
        // given
        final BigDecimal pv = discount(amount(70), percent(5), 1);
        // when
        // when
        final Iterator<BigDecimal> cash = borrowAtRatePerPeriod(pv, percent(5), 1);
        // then
        assertThat(newArrayList(cash), contains(
                closeTo(amount(66.67), CENT),
                closeTo(amount(-70), CENT)
        ));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void cashflow_of_bond_bought_for_borrowed_cash()
    {
        // given
        final BigDecimal pv = discount(amount(70), percent(5), 1);
        // when
        final Iterator<BigDecimal> bond = lendAtRatePerPeriod(pv, percent(5), 1);
        final Iterator<BigDecimal> cash = borrowAtRatePerPeriod(pv, percent(5), 1);
        // when
        final Iterator<BigDecimal> cf = combine(bond, cash);
        // then
        assertThat(newArrayList(cf), contains(
                closeTo(ZERO, CENT),
                closeTo(ZERO, CENT)
        ));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void cashflow_of_sold_bond()
    {
        // given
        final BigDecimal pv = discount(amount(70), percent(5), 1);
        // when
        final Iterator<BigDecimal> sold = negate(lendAtRatePerPeriod(pv, percent(5), 1));
        // then
        assertThat(newArrayList(sold), contains(
                closeTo(amount(66.67), CENT),
                closeTo(amount(-70), CENT)
        ));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void cashflow_of_lent_cash()
    {
        // given
        final BigDecimal pv = discount(amount(70), percent(5), 1);
        // when
        // when
        final Iterator<BigDecimal> loan = negate(borrowAtRatePerPeriod(pv, percent(5), 1));
        // then
        assertThat(newArrayList(loan), contains(
                closeTo(amount(-66.67), CENT),
                closeTo(amount(70), CENT)
        ));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void cashflow_of_bond_sold_and_loan()
    {
        // given
        final BigDecimal pv = discount(amount(70), percent(5), 1);
        // when
        final Iterator<BigDecimal> sold = negate(lendAtRatePerPeriod(pv, percent(5), 1));
        final Iterator<BigDecimal> loan = negate(borrowAtRatePerPeriod(pv, percent(5), 1));
        // when
        final Iterator<BigDecimal> cf = combine(sold, loan);
        // then
        assertThat(newArrayList(cf), contains(
                closeTo(ZERO, CENT),
                closeTo(ZERO, CENT)
        ));
    }
}
