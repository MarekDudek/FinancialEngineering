package interretis.financial_engineering.securities;

import org.testng.annotations.Test;

import java.math.BigDecimal;

import static interretis.financial_engineering.utilities.NumericUtilities.amount;
import static interretis.financial_engineering.utilities.NumericUtilities.percent;
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
}
