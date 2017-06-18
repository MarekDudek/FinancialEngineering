package interretis.financial_engineering;

import org.testng.annotations.Test;

import java.math.BigDecimal;

import static interretis.financial_engineering.CashFlow.currentValue;
import static interretis.financial_engineering.utilities.NumericUtilities.*;
import static java.math.BigDecimal.ZERO;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public final class CashLendContractTest {

    private static final BigDecimal RATE = percent(5);
    private static final BigDecimal AMOUNT = amount(10);

    @Test
    public void lending_up_to_time_1() {
        // given
        final int maturity = 1;
        final BigDecimal lent = currentValue(AMOUNT, RATE, maturity);
        // when
        final CashFlow cashFlow = new CashLendContract(lent, maturity, RATE).getCashFlow();
        // then
        assertThat(cashFlow.atTime(0), is(equalTo(lent.negate())));
        assertThat(cashFlow.atTime(maturity), is(closeTo(AMOUNT, ONE_PERCENT)));
    }

    @Test
    public void lending_up_to_time_2() {
        // given
        final int maturity = 2;
        final BigDecimal lent = currentValue(AMOUNT, RATE, maturity);
        // when
        final CashFlow cashFlow = new CashLendContract(lent, maturity, RATE).getCashFlow();
        // then
        assertThat(cashFlow.atTime(0), is(equalTo(lent.negate())));
        assertThat(cashFlow.atTime(1), is(equalTo(ZERO)));
        assertThat(cashFlow.atTime(maturity), is(closeTo(AMOUNT, ONE_PERCENT)));
    }

    @Test
    public void lending_up_to_time_3() {
        // given
        final int maturity = 3;
        final BigDecimal lent = currentValue(AMOUNT, RATE, maturity);
        // when
        final CashFlow cashFlow = new CashLendContract(lent, maturity, RATE).getCashFlow();
        // then
        assertThat(cashFlow.atTime(0), is(equalTo(lent.negate())));
        assertThat(cashFlow.atTime(1), is(equalTo(ZERO)));
        assertThat(cashFlow.atTime(2), is(equalTo(ZERO)));
        assertThat(cashFlow.atTime(maturity), is(closeTo(AMOUNT, ONE_PERCENT)));
    }
}
