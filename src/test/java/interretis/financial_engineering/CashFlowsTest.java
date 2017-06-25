package interretis.financial_engineering;

import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static interretis.financial_engineering.CashFlows.*;
import static interretis.financial_engineering.utilities.NumericUtilities.*;
import static java.math.BigDecimal.ZERO;
import static java.util.Arrays.asList;
import static java.util.Collections.nCopies;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public final class CashFlowsTest {

    @Test
    public void cashflows_of_different_name_can_be_combined()
    {
        // given
        final Iterator<BigDecimal> c1 = nCopies(5, ZERO).iterator();
        final Iterator<BigDecimal> c2 = nCopies(0, ZERO).iterator();
        // when
        final Iterator<BigDecimal> c = combine(c1, c2);
        // then
        assertThat(newArrayList(c), hasSize(5));
    }

    @Test
    public void numbers_match()
    {
        // given
        final Iterator<BigDecimal> c0 = nCopies(0, number(0)).iterator();
        final Iterator<BigDecimal> c1 = nCopies(1, number(1)).iterator();
        final Iterator<BigDecimal> c2 = nCopies(2, number(2)).iterator();
        final Iterator<BigDecimal> c3 = nCopies(3, number(3)).iterator();
        final Iterator<BigDecimal> c4 = nCopies(4, number(4)).iterator();
        final Iterator<BigDecimal> c5 = nCopies(5, number(5)).iterator();
        // when
        final Iterator<BigDecimal> c = combine(c0, c1, c2, c3, c4, c5);
        // then
        assertThat(newArrayList(c), is(equalTo(newArrayList(number(15), number(14), number(12), number(9), number(5)))));
    }

    @Test
    public void present_value_is_calculated()
    {
        // given
        final BigDecimal a = amount(10);
        final BigDecimal r = percent(5);
        final List<BigDecimal> as = asList(a, a, a, a, a, a);
        // when
        assertThat(valueAtTime(as.iterator(), r, 0), is(closeTo(a, CENT)));
        assertThat(valueAtTime(as.iterator(), r, 1), is(closeTo(amount(9.52), CENT)));
        assertThat(valueAtTime(as.iterator(), r, 2), is(closeTo(amount(9.07), CENT)));
        assertThat(valueAtTime(as.iterator(), r, 3), is(closeTo(amount(8.63), CENT)));
        assertThat(valueAtTime(as.iterator(), r, 4), is(closeTo(amount(8.22), CENT)));
        assertThat(valueAtTime(as.iterator(), r, 5), is(closeTo(amount(7.83), CENT)));
        assertThat(presentValue(as.iterator(), r), is(closeTo(amount(53.29), CENT)));
    }
}
