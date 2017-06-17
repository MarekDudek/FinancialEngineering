package interretis.financial_engineering;

import org.testng.annotations.Test;

import java.math.BigDecimal;

import static interretis.financial_engineering.Interest.*;
import static interretis.financial_engineering.utilities.NumericUtilities.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;

public final class PricingASimpleBond {

    @Test
    public void simple_interest() {
        // given
        final BigDecimal investment = amount(100);
        final int periods = 4;
        final BigDecimal ratePerPeriod = percent(10);
        // when
        final BigDecimal interest = simpleInterest(investment, periods, ratePerPeriod);
        // then
        assertThat(interest, is(closeTo(amount("40"), EPSILON)));
        // when
        final BigDecimal worth = worthAtMaturityAtSimpleInterest(investment, periods, ratePerPeriod);
        // then
        assertThat(worth, is(closeTo(amount("140"), EPSILON)));
    }

    @Test
    public void compound_interest() {
        // given
        final BigDecimal investment = amount(100);
        final int periods = 4;
        final BigDecimal ratePerPeriod = percent(10);
        // when
        final BigDecimal worth = worthAtMaturityAtCompoundInterest(investment, periods, ratePerPeriod);
        // then
        assertThat(worth, is(closeTo(amount("146.41"), ONE_PERCENT)));
        // when
        final BigDecimal interest = compoundInterest(investment, periods, ratePerPeriod);
        // then
        assertThat(interest, is(closeTo(amount("46.41"), ONE_PERCENT)));
    }

    @Test
    public void compound_interest__annual_basis_interest_year__many_compounding_periods_each_year__basic_case() {
        // given
        final BigDecimal investment = amount(100);
        final int years = 4;
        final int compoundingPeriodsPerYear = 1;
        final BigDecimal annualRate = percent(10);
        // when
        final BigDecimal worth = worthAtMaturityAtCompoundInterest(investment, years, annualRate, compoundingPeriodsPerYear);
        // then
        assertThat(worth, is(closeTo(amount("146.41"), ONE_PERCENT)));
        // when
        final BigDecimal interest = compoundInterest(investment, years, annualRate, compoundingPeriodsPerYear);
        // then
        assertThat(interest, is(closeTo(amount("46.41"), ONE_PERCENT)));
    }

    @Test
    public void compound_interest__annual_basis_interest_year__many_compounding_periods_each_year() {
        // given
        final BigDecimal investment = amount(100);
        final int years = 4;
        final int compoundingPeriodsPerYear = 2;
        final BigDecimal annualRate = percent(10);
        // when
        final BigDecimal worth = worthAtMaturityAtCompoundInterest(investment, years, annualRate, compoundingPeriodsPerYear);
        // then
        assertThat(worth, is(closeTo(amount("147.75"), ONE_PERCENT)));
        // when
        final BigDecimal interest = compoundInterest(investment, years, annualRate, compoundingPeriodsPerYear);
        // then
        assertThat(interest, is(closeTo(amount("47.75"), ONE_PERCENT)));
    }

    @Test
    public void continuous_compounding() {
        // given
        final BigDecimal investment = amount(1000);
        final int years = 5;
        final BigDecimal annualRate = percent(5);
        // when
        final BigDecimal worth = worthAtMaturityWithContinuousCompounding(investment, years, annualRate);
        // then
        assertThat(worth, is(closeTo(number("1284.02"), ONE_PERCENT)));
    }

    @Test
    public void pricing_a_simple_bond_for_one_year() {
        // given
        final BigDecimal A = amount(70); // pay in 1 year
        final BigDecimal r = percent(5);
        final ZeroCouponBond bond = new ZeroCouponBond(A);
        // when
        final BigDecimal price = bond.price(r);
        // then
        assertThat(price, is(closeTo(amount("66.67"), ONE_PERCENT)));
    }
}
