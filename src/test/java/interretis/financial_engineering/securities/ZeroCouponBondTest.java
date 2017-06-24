package interretis.financial_engineering.securities;

import interretis.financial_engineering.securities.ZeroCouponBond;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.Iterator;

import static interretis.financial_engineering.utilities.NumericUtilities.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;

public final class ZeroCouponBondTest {

    @Test
    public void pricing_a_simple_bond_for_one_year()
    {
        // given
        final ZeroCouponBond b = new ZeroCouponBond(amount(70), percent(5), 1, 1);
        // then
        assertThat(b.price(), is(closeTo(amount(66.67), CENT)));
    }

    @Test
    public void cash_flow_of_simple_bond_for_one_year()
    {
        // given
        final ZeroCouponBond b = new ZeroCouponBond(amount(70), percent(5), 1, 1);
        // when
        final Iterator<BigDecimal> c = b.cashFlow();
        // then
        assertThat(c.next(), is(closeTo(amount(-66.67), CENT)));
        assertThat(c.next(), is(closeTo(amount(70), CENT)));
    }

    @Test
    public void zero_coupon_bond_example_from_investopedia()
    {
        // given
        final ZeroCouponBond b = new ZeroCouponBond(amount(20_000), percent(5.5), 2, 20);
        // then
        assertThat(b.price(), is(closeTo(amount(6_757.04), CENT)));
    }
}
