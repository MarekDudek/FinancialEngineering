package interretis.financial_engineering.securities;

import org.testng.annotations.Test;

import java.math.BigDecimal;

import static interretis.financial_engineering.utilities.NumericUtilities.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;

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
}
