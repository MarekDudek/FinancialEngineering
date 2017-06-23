package interretis.financial_engineering;

import org.testng.annotations.Test;

import java.math.BigDecimal;

import static interretis.financial_engineering.Interest.discount;
import static interretis.financial_engineering.utilities.NumericUtilities.amount;
import static interretis.financial_engineering.utilities.NumericUtilities.percent;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;

public final class ZeroCouponBondTest {

    @Test
    public void test() {
        // given
        final ZeroCouponBond bond = new ZeroCouponBond(amount(20_000));
        // then
        final BigDecimal price = discount(amount(20_000), percent(5.5), 2, 20);
        assertThat(price, is(closeTo(amount(6757), amount(0.5))));
    }
}
