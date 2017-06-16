package interretis.financial_engineering;

import java.math.BigDecimal;

public final class Interest {

    public static BigDecimal worthAtMaturityAtSimpleInterest(final BigDecimal investment, final int periods, final BigDecimal ratePerPeriod) {
        final BigDecimal interest = simpleInterest(investment, periods, ratePerPeriod);
        return investment.add(interest);
    }

    public static BigDecimal simpleInterest(final BigDecimal investment, final int periods, final BigDecimal ratePerPeriod) {
        final BigDecimal rate = ratePerPeriod.multiply(new BigDecimal(periods));
        return investment.multiply(rate);
    }
}
