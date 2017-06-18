package interretis.financial_engineering;

import org.testng.annotations.Test;

import java.math.BigDecimal;

import static interretis.financial_engineering.CashFlow.currentValue;
import static interretis.financial_engineering.utilities.NumericUtilities.*;
import static java.math.BigDecimal.ZERO;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public final class PortfolioTest {

    private static final BigDecimal RATE = percent(5);
    private static final BigDecimal TEN = amount(10);
    private static final BigDecimal MINUS_TEN = amount(-10);

    private static final Contract CONTRACT = new Contract(new CashFlow(asList(TEN, TEN, TEN, TEN, TEN)));
    private static final BigDecimal PRESENT_VALUE = CONTRACT.presentValue(RATE);

    private static final BigDecimal TEN_FOR_1 = currentValue(TEN, RATE, 1);
    private static final BigDecimal TEN_FOR_2 = currentValue(TEN, RATE, 2);
    private static final BigDecimal TEN_FOR_3 = currentValue(TEN, RATE, 3);
    private static final BigDecimal TEN_FOR_4 = currentValue(TEN, RATE, 4);

    @Test
    public void buy_contract_for_borrowed_cash() {
        // given
        final BigDecimal c0 = TEN.subtract(PRESENT_VALUE);
        final Contract bought = new Contract(new CashFlow(asList(c0, TEN, TEN, TEN, TEN)));
        // then
        assertThat(bought.getCashFlow(), is(equalTo(new CashFlow(asList(new BigDecimal("-35.459506"), TEN, TEN, TEN, TEN)))));
        // when
        final CashBorrowContract borrow1 = new CashBorrowContract(TEN_FOR_1, 1, RATE);
        final CashBorrowContract borrow2 = new CashBorrowContract(TEN_FOR_2, 2, RATE);
        final CashBorrowContract borrow3 = new CashBorrowContract(TEN_FOR_3, 3, RATE);
        final CashBorrowContract borrow4 = new CashBorrowContract(TEN_FOR_4, 4, RATE);
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
    public void sell_contract_and_lend_cash() {
        // given
        final BigDecimal c0 = PRESENT_VALUE.subtract(TEN);
        final Contract sold = new Contract(new CashFlow(asList(c0, MINUS_TEN, MINUS_TEN, MINUS_TEN, MINUS_TEN)));
        // then
        assertThat(sold.getCashFlow(), is(equalTo(new CashFlow(asList(new BigDecimal("35.459506"), MINUS_TEN, MINUS_TEN, MINUS_TEN, MINUS_TEN)))));
        // when
        final CashLendContract lent1 = new CashLendContract(TEN_FOR_1, 1, RATE);
        final CashLendContract lent2 = new CashLendContract(TEN_FOR_2, 2, RATE);
        final CashLendContract lent3 = new CashLendContract(TEN_FOR_3, 3, RATE);
        final CashLendContract lent4 = new CashLendContract(TEN_FOR_4, 4, RATE);
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
}
