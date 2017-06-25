package interretis.financial_engineering.securities;

import org.testng.annotations.Test;

import java.math.BigDecimal;

import static interretis.financial_engineering.CashFlows.presentValue;
import static interretis.financial_engineering.utilities.IteratorUtilities.take;
import static interretis.financial_engineering.utilities.NumericUtilities.amount;
import static interretis.financial_engineering.utilities.NumericUtilities.percent;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;

public final class PerpetuityTest {

    @Test
    public void price_of_perpetuity()
    {
        // given
        final Perpetuity perpetuity = new Perpetuity(amount(1_000), percent(5));
        // when
        final BigDecimal p = perpetuity.price();
        // then
        assertThat(p, is(closeTo(amount(20_000), ZERO)));
    }

    @Test
    public void price_of_perpetuity_equals_present_value()
    {
        // given
        final BigDecimal r = percent(5);
        final Perpetuity perpetuity = new Perpetuity(amount(1_000), r);
        // when
        final BigDecimal p = perpetuity.price();
        final BigDecimal pv = presentValue(take(perpetuity.cashFlow(), 250), r);
        // then
        assertThat(pv, closeTo(p, ONE));
    }
}
