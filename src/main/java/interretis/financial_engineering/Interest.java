package interretis.financial_engineering;

import java.math.BigDecimal;

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
}
