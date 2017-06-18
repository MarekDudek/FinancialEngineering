package interretis.financial_engineering;

import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.List;

import static interretis.financial_engineering.utilities.NumericUtilities.*;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;

public final class PresentValueTest {

    @Test
    public void test() {
        // given
        final BigDecimal ten = amount(10);
        final List<BigDecimal> amounts = asList(ten, ten, ten, ten, ten, ten);
        final CashFlow cashFLow = new CashFlow(amounts);
        final BigDecimal price = amount(60);
        final BigDecimal interestRatePerPeriod = percent(5);
        // when
        final Contract contract = new Contract(price, cashFLow);
        // then
        final BigDecimal v0 = contract.valueAtTime(0, interestRatePerPeriod);
        assertThat(v0, is(closeTo(ten, ONE_PERCENT)));
        // then
        final BigDecimal v1 = contract.valueAtTime(1, interestRatePerPeriod);
        assertThat(v1, is(closeTo(amount("9.52"), ONE_PERCENT)));
        // then
        final BigDecimal v2 = contract.valueAtTime(2, interestRatePerPeriod);
        assertThat(v2, is(closeTo(amount("9.07"), ONE_PERCENT)));
        // then
        final BigDecimal v3 = contract.valueAtTime(3, interestRatePerPeriod);
        assertThat(v3, is(closeTo(amount("8.63"), ONE_PERCENT)));
        // then
        final BigDecimal v4 = contract.valueAtTime(4, interestRatePerPeriod);
        assertThat(v4, is(closeTo(amount("8.22"), ONE_PERCENT)));
        // then
        final BigDecimal v5 = contract.valueAtTime(5, interestRatePerPeriod);
        assertThat(v5, is(closeTo(amount("7.83"), ONE_PERCENT)));
        // then
        final BigDecimal pv = contract.presentValue(interestRatePerPeriod);
        assertThat(pv, is(closeTo(amount("53.29"), ONE_PERCENT)));
    }
}
