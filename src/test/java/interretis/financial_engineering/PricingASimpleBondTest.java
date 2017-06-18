package interretis.financial_engineering;

import org.testng.annotations.Test;

import java.math.BigDecimal;

import static interretis.financial_engineering.utilities.NumericUtilities.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;

public final class PricingASimpleBondTest {

    @Test
    public void pricing_a_simple_bond_for_one_year() {
        // given
        final BigDecimal A = amount(70); // pay in 1 year
        final BigDecimal r = percent(5);
        final ZeroCouponBond bond = new ZeroCouponBond(A);
        // when
        final BigDecimal price = bond.price(r);
        // then
        assertThat(price, is(closeTo(amount("66.67"), ONE_PERCENT)));
    }
}
