package interretis.financial_engineering.utilities;

import org.testng.annotations.Test;

import java.util.Iterator;

import static com.google.common.collect.Lists.newArrayList;
import static interretis.financial_engineering.utilities.IteratorUtilities.constant;
import static interretis.financial_engineering.utilities.IteratorUtilities.take;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

public final class IteratorUtilitiesTest {

    @Test
    public void test()
    {
        // given
        final Iterator<Integer> iterator = constant(0);
        // when
        final Iterator<Integer> limited = take(iterator, 10);
        // then
        assertThat(newArrayList(limited), hasSize(10));
    }
}
