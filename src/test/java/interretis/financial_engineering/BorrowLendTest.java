package interretis.financial_engineering;

import org.testng.annotations.Test;

import java.math.BigDecimal;

import static interretis.financial_engineering.CashFlow.currentValue;
import static interretis.financial_engineering.utilities.NumericUtilities.*;
import static java.math.BigDecimal.ZERO;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public final class BorrowLendTest {

    private static final BigDecimal RATE = percent(5);

    @Test
    public void borrowing_up_to_time_1() {
        // given
        final int maturity = 1;
        final BigDecimal amount = currentValue(amount(10), RATE, maturity);
        // when
        final CashFlow cashFlow = new Borrow(amount, maturity).cashFlow(RATE);
        // then
        assertThat(cashFlow.atTime(0), is(equalTo(amount)));
        assertThat(cashFlow.atTime(maturity), is(closeTo(amount(10), ONE_PERCENT)));
    }

    @Test
    public void borrowing_up_to_time_2() {
        // given
        final int maturity = 2;
        final BigDecimal amount = currentValue(amount(10), RATE, maturity);
        // when
        final CashFlow cashFlow = new Borrow(amount, maturity).cashFlow(RATE);
        // then
        assertThat(cashFlow.atTime(0), is(equalTo(amount)));
        assertThat(cashFlow.atTime(1), is(equalTo(ZERO)));
        assertThat(cashFlow.atTime(maturity), is(closeTo(amount(10), ONE_PERCENT)));
    }

    @Test
    public void borrowing_up_to_time_3() {
        // given
        final int maturity = 3;
        final BigDecimal amount = currentValue(amount(10), RATE, maturity);
        // when
        final CashFlow cashFlow = new Borrow(amount, maturity).cashFlow(RATE);
        // then
        assertThat(cashFlow.atTime(0), is(equalTo(amount)));
        assertThat(cashFlow.atTime(1), is(equalTo(ZERO)));
        assertThat(cashFlow.atTime(2), is(equalTo(ZERO)));
        assertThat(cashFlow.atTime(maturity), is(closeTo(amount(10), ONE_PERCENT)));
    }
}
