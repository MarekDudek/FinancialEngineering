package interretis.financial_engineering;

import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.List;

import static interretis.financial_engineering.utilities.NumericUtilities.amount;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public final class CashFlowTest {

    @Test
    public void test() {
        // given
        final BigDecimal ten = amount("10");
        // when
        final List<BigDecimal> amounts = asList(ten, ten, ten, ten, ten);
        final CashFlow c = new CashFlow(amounts);
        // then
        final BigDecimal c0 = c.atTime(0);
        assertThat(c0, is(equalTo(ten)));
        // then
        final int n = c.maxTime();
        assertThat(n, is(equalTo(4)));
    }
}
