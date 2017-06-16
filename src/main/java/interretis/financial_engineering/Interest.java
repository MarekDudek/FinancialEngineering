package interretis.financial_engineering;

import java.math.BigDecimal;

import static interretis.financial_engineering.utilities.NumericUtilities.divide;
import static interretis.financial_engineering.utilities.NumericUtilities.number;
import static java.math.BigDecimal.ONE;

final class Interest {

    // Simple interest

    static BigDecimal worthAtMaturityAtSimpleInterest(final BigDecimal investment, final int periods, final BigDecimal ratePerPeriod) {
        final BigDecimal interest = simpleInterest(investment, periods, ratePerPeriod);
        return investment.add(interest);
    }

    static BigDecimal simpleInterest(final BigDecimal investment, final int periods, final BigDecimal ratePerPeriod) {
        final BigDecimal rate = ratePerPeriod.multiply(number(periods));
        return investment.multiply(rate);
    }

    // Compound interest

    //   annual rate + multiple compounding periods in each year

    static BigDecimal worthAtMaturityAtCompoundInterest(final BigDecimal investment, final int years, final BigDecimal annualRate, final int periodsPerYear) {
        final BigDecimal rate = compoundRate(years, annualRate, periodsPerYear);
        return investment.multiply(rate);
    }

    static BigDecimal compoundInterest(final BigDecimal investment, final int years, final BigDecimal annualRate, final int periodsPerYear) {
        final BigDecimal rate = compoundRate(years, annualRate, periodsPerYear);
        return investment.multiply(rate.subtract(ONE));
    }

    //   rate per single period

    static BigDecimal worthAtMaturityAtCompoundInterest(final BigDecimal investment, final int periods, final BigDecimal ratePerPeriod) {
        return worthAtMaturityAtCompoundInterest(investment, periods, ratePerPeriod, ONE_PERIOD);
    }

    static BigDecimal compoundInterest(final BigDecimal investment, final int periods, final BigDecimal ratePerPeriod) {
        return compoundInterest(investment, periods, ratePerPeriod, ONE_PERIOD);
    }

    private static BigDecimal compoundRate(final int years, final BigDecimal annualRate, final int periodsPerYear) {
        final BigDecimal periodIncrease = divide(annualRate, number(periodsPerYear));
        final BigDecimal periodRate = ONE.add(periodIncrease);
        final int periods = years * periodsPerYear;
        return periodRate.pow(periods);
    }

    private static final int ONE_PERIOD = 1;
}
