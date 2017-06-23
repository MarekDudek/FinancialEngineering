package interretis.financial_engineering;

import org.testng.annotations.Test;

import static interretis.financial_engineering.utilities.NumericUtilities.amount;
import static interretis.financial_engineering.utilities.NumericUtilities.percent;
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
        assertThat(b.price(), is(closeTo(amount(66.67), amount(0.1))));
    }

    @Test
    public void zero_coupon_bond_example_from_investopedia()
    {
        // given
        final ZeroCouponBond bond = new ZeroCouponBond(amount(20_000), percent(5.5), 2, 20);
        // then
        assertThat(bond.price(), is(closeTo(amount(6757), amount(0.5))));
    }
}
