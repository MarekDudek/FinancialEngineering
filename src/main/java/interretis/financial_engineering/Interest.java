package interretis.financial_engineering;

import java.math.BigDecimal;

import static interretis.financial_engineering.utilities.NumericUtilities.divide;
import static interretis.financial_engineering.utilities.NumericUtilities.number;
import static java.math.BigDecimal.ONE;

final class Interest {

    static BigDecimal worthAtMaturityAtSimpleInterest(final BigDecimal investment, final int periods, final BigDecimal ratePerPeriod) {
        final BigDecimal interest = simpleInterest(investment, periods, ratePerPeriod);
        return investment.add(interest);
    }

    static BigDecimal simpleInterest(final BigDecimal investment, final int periods, final BigDecimal ratePerPeriod) {
        final BigDecimal rate = ratePerPeriod.multiply(new BigDecimal(periods));
        return investment.multiply(rate);
    }

    static BigDecimal compoundInterest(final BigDecimal investment, final int periods, final BigDecimal ratePerPeriod) {
        final BigDecimal rate = compoundRate(periods, ratePerPeriod);
        return investment.multiply(rate.subtract(ONE));
    }

    static BigDecimal worthAtMaturityAtCompoundInterest(final BigDecimal investment, final int periods, final BigDecimal ratePerPeriod) {
        final BigDecimal rate = compoundRate(periods, ratePerPeriod);
        return investment.multiply(rate);
    }

    private static BigDecimal compoundRate(final int periods, final BigDecimal ratePerPeriod) {
        final BigDecimal periodRate = ONE.add(ratePerPeriod);
        return periodRate.pow(periods);
    }

    static BigDecimal worthAtMaturityAtCompoundInterest(final BigDecimal investment, final int years, final BigDecimal annualRate, final int periods) {
        final BigDecimal rate = divide(annualRate, number(periods));
        final BigDecimal compoundRate = (ONE.add(rate)).pow(years * periods);
        return investment.multiply(compoundRate);
    }
}
