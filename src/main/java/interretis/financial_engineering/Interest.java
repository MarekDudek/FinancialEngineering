package interretis.financial_engineering;

import java.math.BigDecimal;

import static interretis.financial_engineering.utilities.NumericUtilities.divide;
import static interretis.financial_engineering.utilities.NumericUtilities.number;
import static java.lang.Math.E;
import static java.lang.Math.pow;
import static java.math.BigDecimal.ONE;

final class Interest {

    // Basic operations

    // @formatter:off
    /**
     *  a * (1+r)^n
     */
    // @formatter:on
    static BigDecimal compound(final BigDecimal a, final BigDecimal r, final int n) {
        return a.multiply((ONE.add(r)).pow(n));
    }

    // @formatter:off
    /**
     * a * (1+ r/n)^(y*n)
     */
    // @formatter:on
    static BigDecimal compound(final BigDecimal a, final BigDecimal r, final int n, final int y) {
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


    // One-time interest

    static BigDecimal worthAtMaturityAtBasicInterest(final BigDecimal principal, final BigDecimal rate) {
        return compound(principal, rate, 1);
    }

    static BigDecimal priceForWorthAtMaturityAtBasicInterest(final BigDecimal worth, final BigDecimal rate) {
        return discount(worth, rate, 1);
    }

    // Simple interest

    static BigDecimal worthAtMaturityAtSimpleInterest(final BigDecimal principal, final int periods, final BigDecimal ratePerPeriod) {
        final BigDecimal interest = simpleInterest(principal, periods, ratePerPeriod);
        return principal.add(interest);
    }

    static BigDecimal simpleInterest(final BigDecimal principal, final int periods, final BigDecimal ratePerPeriod) {
        final BigDecimal rate = ratePerPeriod.multiply(number(periods));
        return principal.multiply(rate);
    }

    // Compound interest

    //   annual rate + multiple compounding periods in each year

    static BigDecimal worthAtMaturityAtCompoundInterest(final BigDecimal principal, final BigDecimal annualRate, final int periodsPerYear, final int years) {
        return compound(principal, annualRate, periodsPerYear, years);
    }

    static BigDecimal compoundInterest(final BigDecimal principal, final BigDecimal annualRate, final int periodsPerYear, final int years) {
        return compound(principal, annualRate, periodsPerYear, years).subtract(principal);
    }

    //   rate matches period

    static BigDecimal worthAtMaturityAtCompoundInterest(final BigDecimal principal, final int periods, final BigDecimal ratePerPeriod) {
        return worthAtMaturityAtCompoundInterest(principal, ratePerPeriod, ONE_PERIOD, periods);
    }

    static BigDecimal compoundInterest(final BigDecimal principal, final int periods, final BigDecimal ratePerPeriod) {
        return compoundInterest(principal, ratePerPeriod, ONE_PERIOD, periods);
    }

    private static final int ONE_PERIOD = 1;

    // Continuous compounding

    static BigDecimal worthAtMaturityWithContinuousCompounding(final BigDecimal principal, final int years, final BigDecimal annualRate) {
        final double r = annualRate.doubleValue();
        final double y = (double) years;
        final double rate = pow(E, r * y);
        return principal.multiply(number(rate));
    }

    static BigDecimal continuousInterest(final BigDecimal principal, final int years, final BigDecimal annualRate) {
        final BigDecimal worth = worthAtMaturityWithContinuousCompounding(principal, years, annualRate);
        return worth.subtract(principal);
    }
}
