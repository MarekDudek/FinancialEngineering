package interretis.financial_engineering;

import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.Iterator;

import static interretis.financial_engineering.CashFlows.*;
import static interretis.financial_engineering.Interest.discount;
import static interretis.financial_engineering.utilities.NumericUtilities.*;
import static java.math.BigDecimal.ZERO;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertFalse;

public final class PricingASimpleBondTest {

    private static final BigDecimal AMOUNT = amount(70);
    private static final BigDecimal RATE = percent(5);

    private static final Contract CONTRACT = new Contract(new CashFlow(asList(ZERO, amount(70))));
    private static final BigDecimal PV = CONTRACT.presentValue(RATE);
    private static final BigDecimal AMOUNT_FOR_1 = discount(AMOUNT, RATE, 1);

    @Test
    @Deprecated
    public void buy_simple_bond_for_borrowed() {
        // given
        final Contract c = new Contract(new CashFlow(asList(PV.negate(), AMOUNT)));
        // then
        assertThat(c.getCashFlow(), is(equalTo(new CashFlow(asList(new BigDecimal("-66.666667"), new BigDecimal("70.000000"))))));
        // when
        final CashBorrowContract b = new CashBorrowContract(AMOUNT_FOR_1, 1, RATE);
        // then
        assertThat(b.getCashFlow(), is(equalTo(new CashFlow(asList(new BigDecimal("66.666667"), new BigDecimal("-70.000000350000"))))));
        // when
        final Portfolio portfolio = new Portfolio(asList(c, b));
        // then
        for (final BigDecimal amount : portfolio.getCashFlow().amounts())
            assertThat(amount, is(closeTo(ZERO, EPSILON)));
    }

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
    @Deprecated
    public void sell_contract_and_lend_cash() {
        // given
        final Contract sold = new Contract(new CashFlow(asList(PV, AMOUNT.negate())));
        // then
        assertThat(sold.getCashFlow(), is(equalTo(new CashFlow(asList(new BigDecimal("66.666667"), new BigDecimal("-70.000000"))))));
        final CashLendContract lend = new CashLendContract(AMOUNT_FOR_1, 1, RATE);
        // when
        final Portfolio portfolio = new Portfolio(asList(sold, lend));
        // then
        for (final BigDecimal amount : portfolio.getCashFlow().amounts())
            assertThat(amount, is(closeTo(ZERO, EPSILON)));
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
