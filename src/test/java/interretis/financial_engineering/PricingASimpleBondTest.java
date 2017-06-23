package interretis.financial_engineering;

import org.testng.annotations.Test;

import java.math.BigDecimal;

import static interretis.financial_engineering.Interest.discount;
import static interretis.financial_engineering.utilities.NumericUtilities.*;
import static java.math.BigDecimal.ZERO;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public final class PricingASimpleBondTest {

    private static final BigDecimal AMOUNT = amount(70);
    private static final BigDecimal RATE = percent(5);

    private static final Contract CONTRACT = new Contract(new CashFlow(asList(ZERO, amount(70))));
    private static final BigDecimal PV = CONTRACT.presentValue(RATE);
    private static final BigDecimal AMOUNT_FOR_1 = discount(AMOUNT, RATE, 1);

    @Test
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
}
