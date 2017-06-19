package interretis.financial_engineering;

import java.math.BigDecimal;

import static interretis.financial_engineering.utilities.NumericUtilities.divide;
import static interretis.financial_engineering.utilities.NumericUtilities.number;
import static java.lang.Math.E;
import static java.lang.Math.pow;
import static java.math.BigDecimal.ONE;

final class Interest {

    // One-time interest

    static BigDecimal worthAtMaturityAtBasicInterest(final BigDecimal principal, final BigDecimal rate) {
        return principal.multiply(ONE.add(rate));
    }

    static BigDecimal priceForWorthAtMaturityAtBasicInterest(final BigDecimal worth, final BigDecimal rate) {
        return divide(
                worth,
                ONE.add(rate)
        );
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

    static BigDecimal worthAtMaturityAtCompoundInterest(final BigDecimal principal, final int years, final BigDecimal annualRate, final int periodsPerYear) {
        final BigDecimal rate = compoundRate(years, annualRate, periodsPerYear);
        return principal.multiply(rate);
    }

    static BigDecimal compoundInterest(final BigDecimal principal, final int years, final BigDecimal annualRate, final int periodsPerYear) {
        final BigDecimal rate = compoundRate(years, annualRate, periodsPerYear);
        return principal.multiply(rate.subtract(ONE));
    }

    private static BigDecimal compoundRate(final int years, final BigDecimal annualRate, final int periodsPerYear) {
        final BigDecimal periodIncrease = divide(annualRate, number(periodsPerYear));
        final BigDecimal periodRate = ONE.add(periodIncrease);
        final int periods = years * periodsPerYear;
        return periodRate.pow(periods);
    }

    //   rate matches period

    static BigDecimal worthAtMaturityAtCompoundInterest(final BigDecimal principal, final int periods, final BigDecimal ratePerPeriod) {
        return worthAtMaturityAtCompoundInterest(principal, periods, ratePerPeriod, ONE_PERIOD);
    }

    static BigDecimal compoundInterest(final BigDecimal principal, final int periods, final BigDecimal ratePerPeriod) {
        return compoundInterest(principal, periods, ratePerPeriod, ONE_PERIOD);
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
