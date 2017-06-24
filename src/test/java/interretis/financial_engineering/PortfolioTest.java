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
import static org.testng.Assert.assertFalse;

public final class PortfolioTest {

    private static final BigDecimal COMMON_RATE = percent(5);
    private static final BigDecimal TEN = amount(10);
    private static final BigDecimal MINUS_TEN = amount(-10);

    private static final Contract CONTRACT = new Contract(new CashFlow(asList(TEN, TEN, TEN, TEN, TEN)));

    private static final BigDecimal TEN_FOR_1 = discount(TEN, COMMON_RATE, 1);
    private static final BigDecimal TEN_FOR_2 = discount(TEN, COMMON_RATE, 2);
    private static final BigDecimal TEN_FOR_3 = discount(TEN, COMMON_RATE, 3);
    private static final BigDecimal TEN_FOR_4 = discount(TEN, COMMON_RATE, 4);

    @Test
    @Deprecated
    public void buy_contract_for_borrowed_cash() {
        // when
        final BigDecimal pv = CONTRACT.presentValue(COMMON_RATE);
        // then
        assertThat(pv, is(closeTo(number("45.459506"), EPSILON)));
        // given
        final BigDecimal c0 = TEN.subtract(pv);
        final Contract bought = new Contract(new CashFlow(asList(c0, TEN, TEN, TEN, TEN)));
        // then
        assertThat(bought.getCashFlow(), is(equalTo(new CashFlow(asList(new BigDecimal("-35.459506"), TEN, TEN, TEN, TEN)))));
        // when
        final CashBorrowContract borrow1 = new CashBorrowContract(TEN_FOR_1, 1, COMMON_RATE);
        final CashBorrowContract borrow2 = new CashBorrowContract(TEN_FOR_2, 2, COMMON_RATE);
        final CashBorrowContract borrow3 = new CashBorrowContract(TEN_FOR_3, 3, COMMON_RATE);
        final CashBorrowContract borrow4 = new CashBorrowContract(TEN_FOR_4, 4, COMMON_RATE);
        // then
        assertThat(borrow1.getCashFlow(), is(equalTo(new CashFlow(asList(new BigDecimal("9.523810"), new BigDecimal("-10.000000500000"))))));
        assertThat(borrow2.getCashFlow(), is(equalTo(new CashFlow(asList(new BigDecimal("9.070295"), ZERO, new BigDecimal("-10.000000237500000000"))))));
        assertThat(borrow3.getCashFlow(), is(equalTo(new CashFlow(asList(new BigDecimal("8.638376"), ZERO, ZERO, new BigDecimal("-10.000000017000000000000000"))))));
        assertThat(borrow4.getCashFlow(), is(equalTo(new CashFlow(asList(new BigDecimal("8.227025"), ZERO, ZERO, ZERO, new BigDecimal("-10.000000306406250000000000000000"))))));
        // when
        final Portfolio portfolio = new Portfolio(asList(bought, borrow1, borrow2, borrow3, borrow4));
        final CashFlow cashFlow = portfolio.getCashFlow();
        // then
        for (final BigDecimal amount : cashFlow.amounts())
            assertThat(amount, is(closeTo(ZERO, EPSILON)));
    }

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
        assertThat(c.next(), is(closeTo(amount(-35.46), CENT)));
        assertThat(c.next(), is(closeTo(a, CENT)));
        assertThat(c.next(), is(closeTo(a, CENT)));
        assertThat(c.next(), is(closeTo(a, CENT)));
        assertThat(c.next(), is(closeTo(a, CENT)));
        assertFalse(c.hasNext());
    }

    @Test
    public void cashflow_of_loan_for_one_period()
    {
        // given
        final BigDecimal a = amount(10);
        final BigDecimal r = percent(5);
        // when
        final Iterator<BigDecimal> l = borrowAtRatePerPeriod(discount(a, r, 1), r, 1);
        // then
        assertThat(l.next(), is(closeTo(amount(9.52), CENT)));
        assertThat(l.next(), is(closeTo(amount(-10), CENT)));
        assertFalse(l.hasNext());
    }

    @Test
    public void cashflow_of_loan_for_two_periods()
    {
        // given
        final BigDecimal a = amount(10);
        final BigDecimal r = percent(5);
        // when
        final Iterator<BigDecimal> l = borrowAtRatePerPeriod(discount(a, r, 2), r, 2);
        // then
        assertThat(l.next(), is(closeTo(amount(9.07), CENT)));
        assertThat(l.next(), is(closeTo(ZERO, CENT)));
        assertThat(l.next(), is(closeTo(amount(-10), CENT)));
        assertFalse(l.hasNext());
    }

    @Test
    public void cashflow_of_loan_for_three_periods()
    {
        // given
        final BigDecimal a = amount(10);
        final BigDecimal r = percent(5);
        // when
        final Iterator<BigDecimal> l = borrowAtRatePerPeriod(discount(a, r, 3), r, 3);
        // then
        assertThat(l.next(), is(closeTo(amount(8.64), CENT)));
        assertThat(l.next(), is(closeTo(ZERO, CENT)));
        assertThat(l.next(), is(closeTo(ZERO, CENT)));
        assertThat(l.next(), is(closeTo(amount(-10), CENT)));
        assertFalse(l.hasNext());
    }

    @Test
    public void cashflow_of_loan_for_four_periods()
    {
        // given
        final BigDecimal a = amount(10);
        final BigDecimal r = percent(5);
        // when
        final Iterator<BigDecimal> l = borrowAtRatePerPeriod(discount(a, r, 4), r, 4);
        // then
        assertThat(l.next(), is(closeTo(amount(8.23), CENT)));
        assertThat(l.next(), is(closeTo(ZERO, CENT)));
        assertThat(l.next(), is(closeTo(ZERO, CENT)));
        assertThat(l.next(), is(closeTo(ZERO, CENT)));
        assertThat(l.next(), is(closeTo(amount(-10), CENT)));
        assertFalse(l.hasNext());
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
    public void sell_contract_and_lend_cash() {
        // when
        final BigDecimal pv = CONTRACT.presentValue(COMMON_RATE);
        // then
        assertThat(pv, is(closeTo(number("45.459506"), EPSILON)));
        // given
        final BigDecimal c0 = pv.subtract(TEN);
        final Contract sold = new Contract(new CashFlow(asList(c0, MINUS_TEN, MINUS_TEN, MINUS_TEN, MINUS_TEN)));
        // then
        assertThat(sold.getCashFlow(), is(equalTo(new CashFlow(asList(new BigDecimal("35.459506"), MINUS_TEN, MINUS_TEN, MINUS_TEN, MINUS_TEN)))));
        // when
        final CashLendContract lent1 = new CashLendContract(TEN_FOR_1, 1, COMMON_RATE);
        final CashLendContract lent2 = new CashLendContract(TEN_FOR_2, 2, COMMON_RATE);
        final CashLendContract lent3 = new CashLendContract(TEN_FOR_3, 3, COMMON_RATE);
        final CashLendContract lent4 = new CashLendContract(TEN_FOR_4, 4, COMMON_RATE);
        // then
        assertThat(lent1.getCashFlow(), is(equalTo(new CashFlow(asList(new BigDecimal("-9.523810"), new BigDecimal("10.000000500000"))))));
        assertThat(lent2.getCashFlow(), is(equalTo(new CashFlow(asList(new BigDecimal("-9.070295"), ZERO, new BigDecimal("10.000000237500000000"))))));
        assertThat(lent3.getCashFlow(), is(equalTo(new CashFlow(asList(new BigDecimal("-8.638376"), ZERO, ZERO, new BigDecimal("10.000000017000000000000000"))))));
        assertThat(lent4.getCashFlow(), is(equalTo(new CashFlow(asList(new BigDecimal("-8.227025"), ZERO, ZERO, ZERO, new BigDecimal("10.000000306406250000000000000000"))))));
        // when
        final Portfolio portfolio = new Portfolio(asList(sold, lent1, lent2, lent3, lent4));
        final CashFlow cashFlow = portfolio.getCashFlow();
        // then
        for (final BigDecimal amount : cashFlow.amounts())
            assertThat(amount, is(closeTo(ZERO, EPSILON)));
    }

    private static final BigDecimal LEND_RATE = percent("4.8");
    private static final BigDecimal BORROW_RATE = percent("5.2");

    @Test
    public void buy_contract_and_borrow_at_different_rate() {
        // when
        final BigDecimal bpv = CONTRACT.presentValue(BORROW_RATE);
        // then
        assertThat(bpv, is(closeTo(number("45.295384"), EPSILON)));
        // when
        final BigDecimal spv = CONTRACT.presentValue(LEND_RATE);
        // then
        assertThat(spv, is(closeTo(number("45.624865"), EPSILON)));

    }
}
