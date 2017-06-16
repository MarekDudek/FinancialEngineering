package interretis.financial_engineering;

import org.testng.annotations.Test;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public final class PricingASimpleBond {

    @Test
    public void test() {
        // given
        final BigDecimal A = new BigDecimal("70.00"); // pay in 1 year
        final BigDecimal r = new BigDecimal("0.05");
        final ZeroCouponBond bond = new ZeroCouponBond(A);
        // when
        final BigDecimal price = bond.price(r);
        // then
        assertThat(price, is(equalTo(new BigDecimal("66.67"))));
    }
}
