package interretis.financial_engineering;

import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public final class SomeTest {

    @Test
    public void test() {
        assertThat(2 + 2, is(equalTo(4)));
    }
}
