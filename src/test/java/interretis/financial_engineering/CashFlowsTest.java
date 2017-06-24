package interretis.financial_engineering;

import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.Iterator;

import static com.google.common.collect.Lists.newArrayList;
import static interretis.financial_engineering.CashFlows.portfolio;
import static java.math.BigDecimal.ZERO;
import static java.util.Collections.nCopies;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

public final class CashFlowsTest {

    @Test
    public void cashflows_of_different_name_can_be_combined()
    {
        // given
        final Iterator<BigDecimal> c1 = nCopies(5, ZERO).iterator();
        final Iterator<BigDecimal> c2 = nCopies(0, ZERO).iterator();
        // when
        final Iterator<BigDecimal> c = portfolio(c1, c2);
        // then
        assertThat(newArrayList(c), hasSize(5));
    }
}
