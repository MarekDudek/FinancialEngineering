package interretis.financial_engineering;

import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static interretis.financial_engineering.CashFlows.*;
import static interretis.financial_engineering.Interest.discount;
import static interretis.financial_engineering.utilities.NumericUtilities.*;
import static java.math.BigDecimal.ZERO;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public final class PortfolioTest {

    @Test
    public void price_of_contract()
    {
        // given
        final BigDecimal a = amount(10);
        final BigDecimal r = percent(5);
        final List<BigDecimal> as = asList(a, a, a, a, a);
        // when
        final Iterator<BigDecimal> contract = as.iterator();
        final BigDecimal pv = presentValue(contract, r);
        // then
        assertThat(pv, is(closeTo(number(45.46), CENT)));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void cashflow_of_bought_contract()
    {
        // given
        final BigDecimal a = amount(10);
        final BigDecimal r = percent(5);
        final List<BigDecimal> as = asList(a, a, a, a, a);
        // when
        final BigDecimal pv = presentValue(as.iterator(), r);
        final Iterator<BigDecimal> payment = singletonList(pv.negate()).iterator();
        final Iterator<BigDecimal> contract = as.iterator();
        final Iterator<BigDecimal> c = combine(contract, payment);
        // then
        assertThat(newArrayList(c), contains(
                closeTo(amount(-35.46), CENT),
                equalTo(a),
                equalTo(a),
                equalTo(a),
                equalTo(a)
        ));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void cashflow_of_loan_for_one_period()
    {
        // given
        final BigDecimal a = amount(10);
        final BigDecimal r = percent(5);
        // when
        final Iterator<BigDecimal> l = borrowAtRatePerPeriod(discount(a, r, 1), r, 1);
        // then
        assertThat(newArrayList(l), contains(
                closeTo(amount(9.52), CENT),
                closeTo(amount(-10), CENT)
        ));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void cashflow_of_loan_for_two_periods()
    {
        // given
        final BigDecimal a = amount(10);
        final BigDecimal r = percent(5);
        // when
        final Iterator<BigDecimal> l = borrowAtRatePerPeriod(discount(a, r, 2), r, 2);
        // then
        assertThat(newArrayList(l), contains(
                closeTo(amount(9.07), CENT),
                equalTo(ZERO),
                closeTo(amount(-10), CENT)
        ));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void cashflow_of_loan_for_three_periods()
    {
        // given
        final BigDecimal a = amount(10);
        final BigDecimal r = percent(5);
        // when
        final Iterator<BigDecimal> l = borrowAtRatePerPeriod(discount(a, r, 3), r, 3);
        // then
        assertThat(newArrayList(l), contains(
                closeTo(amount(8.64), CENT),
                equalTo(ZERO),
                equalTo(ZERO),
                closeTo(amount(-10), CENT)
        ));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void cashflow_of_loan_for_four_periods()
    {
        // given
        final BigDecimal a = amount(10);
        final BigDecimal r = percent(5);
        // when
        final Iterator<BigDecimal> l = borrowAtRatePerPeriod(discount(a, r, 4), r, 4);
        // then
        assertThat(newArrayList(l), contains(
                closeTo(amount(8.23), CENT),
                equalTo(ZERO),
                equalTo(ZERO),
                equalTo(ZERO),
                closeTo(amount(-10), CENT)
        ));
    }

    @Test
    public void cashflow_of_contract_bought_for_series_of_loans()
    {
        // given
        final BigDecimal a = amount(10);
        final BigDecimal r = percent(5);
        final List<BigDecimal> as = asList(a, a, a, a, a);
        // when
        final Iterator<BigDecimal> payment = singletonList(presentValue(as.iterator(), r).negate()).iterator();
        final Iterator<BigDecimal> contract = as.iterator();
        final Iterator<BigDecimal> loan1 = borrowAtRatePerPeriod(discount(a, r, 1), r, 1);
        final Iterator<BigDecimal> loan2 = borrowAtRatePerPeriod(discount(a, r, 2), r, 2);
        final Iterator<BigDecimal> loan3 = borrowAtRatePerPeriod(discount(a, r, 3), r, 3);
        final Iterator<BigDecimal> loan4 = borrowAtRatePerPeriod(discount(a, r, 4), r, 4);
        // then
        final Iterator<BigDecimal> portfolio = combine(contract, payment, loan1, loan2, loan3, loan4);
        newArrayList(portfolio).forEach(
                c -> assertThat(c, is(closeTo(amount(0), CENT)))
        );
    }


    @Test
    public void cashflow_of_contract_sold_and_series_of_loans()
    {
        // given
        final BigDecimal a = amount(10);
        final BigDecimal r = percent(5);
        final List<BigDecimal> as = asList(a, a, a, a, a);
        // when
        final Iterator<BigDecimal> payment = singletonList(presentValue(as.iterator(), r)).iterator();
        final Iterator<BigDecimal> contract = negate(as.iterator());
        final Iterator<BigDecimal> loan1 = negate(borrowAtRatePerPeriod(discount(a, r, 1), r, 1));
        final Iterator<BigDecimal> loan2 = negate(borrowAtRatePerPeriod(discount(a, r, 2), r, 2));
        final Iterator<BigDecimal> loan3 = negate(borrowAtRatePerPeriod(discount(a, r, 3), r, 3));
        final Iterator<BigDecimal> loan4 = negate(borrowAtRatePerPeriod(discount(a, r, 4), r, 4));
        // then
        final Iterator<BigDecimal> portfolio = combine(contract, payment, loan1, loan2, loan3, loan4);
        newArrayList(portfolio).forEach(
                c -> assertThat(c, is(closeTo(amount(0), CENT)))
        );
    }

    @Test
    public void buy_contract_and_borrow_at_different_rate() {
        // given
        final BigDecimal a = amount(10);
        final List<BigDecimal> as = asList(a, a, a, a, a);
        // when
        final BigDecimal bpv = presentValue(as.iterator(), percent("5.2"));
        // then
        assertThat(bpv, is(closeTo(number(45.30), CENT)));
        // when
        final BigDecimal spv = presentValue(as.iterator(), percent("4.8"));
        // then
        assertThat(spv, is(closeTo(number(45.62), CENT)));
    }
}
