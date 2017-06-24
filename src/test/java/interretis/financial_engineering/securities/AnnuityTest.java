package interretis.financial_engineering.securities;

import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static interretis.financial_engineering.CashFlows.presentValue;
import static interretis.financial_engineering.utilities.NumericUtilities.*;
import static java.math.BigDecimal.ZERO;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public final class AnnuityTest {

    @Test
    public void price_of_annuity()
    {
        // when
        final Annuity annuity = new Annuity(amount(1_000), percent(9), 10);
        final BigDecimal p = annuity.price();
        // then
        assertThat(p, is(closeTo(amount(6_417.66), CENT)));
    }

    @Test
    public void cashflows_of_annuity()
    {
        // given
        final BigDecimal a = amount(10);
        // when
        final Annuity annuity = new Annuity(a, percent(5), 5);
        final List<BigDecimal> cf = newArrayList(annuity.cashFlow());
        // then
        assertThat(cf, contains(ZERO, a, a, a, a, a));
        assertThat(cf, hasSize(6));
    }

    @Test
    public void price_equals_present_value_of_cashflows()
    {
        // given
        final BigDecimal rate = percent(9);
        final Annuity annuity = new Annuity(amount(1_000), rate, 10);
        // when
        final BigDecimal price = annuity.price();
        final BigDecimal pv = presentValue(annuity.cashFlow(), rate);
        // then
        assertThat(price, is(closeTo(pv, CENT)));
    }
}
