package interretis.financial_engineering;

import java.math.BigDecimal;

import static interretis.financial_engineering.utilities.NumericUtilities.*;
import static java.lang.Math.E;
import static java.lang.Math.pow;
import static java.math.BigDecimal.ONE;

final class Interest {

    // Basic operations

    // @formatter:off
    /**
     *  a * (1 + n*r)
     */
    // @formatter:on
    private static BigDecimal simple(final BigDecimal a, final BigDecimal r, final int n) {
        return a.multiply(ONE.add(multiply(r, n)));
    }

    // @formatter:off
    /**
     *  a * (1 + r)^n
     */
    // @formatter:on
    static BigDecimal compound(final BigDecimal a, final BigDecimal r, final int n) {
        return a.multiply((ONE.add(r)).pow(n));
    }

    // @formatter:off
    /**
     * a * (1 + r/n)^(y*n)
     */
    // @formatter:on
    private static BigDecimal compound(final BigDecimal a, final BigDecimal r, final int n, final int y) {
        return a.multiply((ONE.add(divide(r, n))).pow(y * n));
    }

    // @formatter:off
    /**
     *     a
     *  -------
     *  (1+r)^n
     */
    // @formatter:on
    static BigDecimal discount(final BigDecimal a, final BigDecimal r, final int n) {
        return divide(a, (ONE.add(r)).pow(n));
    }

    private static final int ONE_PERIOD = 1;

    // One-time interest

    static BigDecimal worthAtBasicInterest(final BigDecimal principal, final BigDecimal rate) {
        return compound(principal, rate, ONE_PERIOD);
    }

    static BigDecimal priceAtBasicInterest(final BigDecimal worth, final BigDecimal rate) {
        return discount(worth, rate, ONE_PERIOD);
    }

    // Simple interest

    static BigDecimal worthAtSimpleInterest(final BigDecimal principal, final int periods, final BigDecimal ratePerPeriod) {
        return simple(principal, ratePerPeriod, periods);
    }

    static BigDecimal simpleInterest(final BigDecimal principal, final int periods, final BigDecimal ratePerPeriod) {
        return simple(principal, ratePerPeriod, periods).subtract(principal);
    }

    // Compound interest

    //   annual rate + multiple compounding periods in each year

    static BigDecimal worthAtCompoundInterest(final BigDecimal principal, final BigDecimal annualRate, final int periodsPerYear, final int years) {
        return compound(principal, annualRate, periodsPerYear, years);
    }

    static BigDecimal compoundInterest(final BigDecimal principal, final BigDecimal annualRate, final int periodsPerYear, final int years) {
        return compound(principal, annualRate, periodsPerYear, years).subtract(principal);
    }

    //   rate matches period

    static BigDecimal worthAtCompoundInterest(final BigDecimal principal, final BigDecimal ratePerPeriod, final int periods) {
        return compound(principal, ratePerPeriod, ONE_PERIOD, periods);
    }

    static BigDecimal compoundInterest(final BigDecimal principal, final BigDecimal ratePerPeriod, final int periods) {
        return compound(principal, ratePerPeriod, ONE_PERIOD, periods).subtract(principal);
    }

    // Continuous compounding

    static BigDecimal worthWithContinuousCompounding(final BigDecimal principal, final int years, final BigDecimal annualRate) {
        final double r = annualRate.doubleValue();
        final double y = (double) years;
        final double rate = pow(E, r * y);
        return principal.multiply(number(rate));
    }

    static BigDecimal continuousInterest(final BigDecimal principal, final int years, final BigDecimal annualRate) {
        final BigDecimal worth = worthWithContinuousCompounding(principal, years, annualRate);
        return worth.subtract(principal);
    }
}
