package interretis.financial_engineering;

import org.testng.annotations.Test;

import java.math.BigDecimal;

import static interretis.financial_engineering.Interest.*;
import static interretis.financial_engineering.utilities.NumericUtilities.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;

public final class InterestTest {

    @Test
    public void compounding_and_discounting() {
        // given
        final BigDecimal r = percent(5);
        final int t = 3;
        final BigDecimal a = amount(100);
        // when
        final BigDecimal c = compound(a, r, t);
        final BigDecimal d = discount(c, r, t);
        // then
        assertThat(d, is(closeTo(d, EPSILON)));
    }

    @Test
    public void basic_interest() {
        // given
        final BigDecimal worth = worthAtMaturityAtBasicInterest(number(100), percent(5));
        // then
        assertThat(worth, is(closeTo(number(105), EPSILON)));
        // given
        final BigDecimal price = priceForWorthAtMaturityAtBasicInterest(number(105), percent(5));
        // then
        assertThat(price, is(closeTo(number(100), EPSILON)));
    }

    @Test
    public void simple_interest() {
        // given
        final BigDecimal principal = amount(100);
        final int periods = 4;
        final BigDecimal ratePerPeriod = percent(10);
        // when
        final BigDecimal interest = simpleInterest(principal, periods, ratePerPeriod);
        // then
        assertThat(interest, is(closeTo(amount("40"), EPSILON)));
        // when
        final BigDecimal worth = worthAtMaturityAtSimpleInterest(principal, periods, ratePerPeriod);
        // then
        assertThat(worth, is(closeTo(amount("140"), EPSILON)));
    }

    @Test
    public void compound_interest() {
        // given
        final BigDecimal principal = amount(100);
        final int periods = 4;
        final BigDecimal ratePerPeriod = percent(10);
        // when
        final BigDecimal worth = worthAtMaturityAtCompoundInterest(principal, periods, ratePerPeriod);
        // then
        assertThat(worth, is(closeTo(amount("146.41"), ONE_PERCENT)));
        // when
        final BigDecimal interest = compoundInterest(principal, periods, ratePerPeriod);
        // then
        assertThat(interest, is(closeTo(amount("46.41"), ONE_PERCENT)));
    }

    @Test
    public void compound_interest__annual_basis_interest_year__many_compounding_periods_each_year__basic_case() {
        // given
        final BigDecimal principal = amount(100);
        final int years = 4;
        final int periodsPerYear = 1;
        final BigDecimal annualRate = percent(10);
        // when
        final BigDecimal worth = worthAtMaturityAtCompoundInterest(principal, annualRate, periodsPerYear, years);
        // then
        assertThat(worth, is(closeTo(amount("146.41"), ONE_PERCENT)));
        // when
        final BigDecimal interest = compoundInterest(principal, annualRate, periodsPerYear, years);
        // then
        assertThat(interest, is(closeTo(amount("46.41"), ONE_PERCENT)));
    }

    @Test
    public void compound_interest__annual_basis_interest_year__many_compounding_periods_each_year() {
        // given
        final BigDecimal principal = amount(100);
        final int years = 4;
        final int periodsPerYear = 2;
        final BigDecimal annualRate = percent(10);
        // when
        final BigDecimal worth = worthAtMaturityAtCompoundInterest(principal, annualRate, periodsPerYear, years);
        // then
        assertThat(worth, is(closeTo(amount("147.75"), ONE_PERCENT)));
        // when
        final BigDecimal interest = compoundInterest(principal, annualRate, periodsPerYear, years);
        // then
        assertThat(interest, is(closeTo(amount("47.75"), ONE_PERCENT)));
    }

    @Test
    public void continuous_compounding() {
        // given
        final BigDecimal principal = amount(1000);
        final int years = 5;
        final BigDecimal annualRate = percent(5);
        // when
        final BigDecimal worth = worthAtMaturityWithContinuousCompounding(principal, years, annualRate);
        // then
        assertThat(worth, is(closeTo(number("1284.02"), ONE_PERCENT)));
        // when
        final BigDecimal interest = continuousInterest(principal, years, annualRate);
        // then
        assertThat(interest, is(closeTo(number("284.02"), ONE_PERCENT)));
    }
}
