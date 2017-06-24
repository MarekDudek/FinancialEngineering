package interretis.financial_engineering.securities;

import interretis.financial_engineering.CashFlows;
import interretis.financial_engineering.Interest;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.Iterator;

@AllArgsConstructor
public final class ZeroCouponBond {

    private final BigDecimal faceValue;
    private final BigDecimal yield;
    private final int paidPerYear;
    private final int maturityInYears;

    public BigDecimal price()
    {
        return Interest.discount(faceValue, yield, paidPerYear, maturityInYears);
    }

    public Iterator<BigDecimal> cashFlow()
    {
        return CashFlows.lendAtRatePerPeriod(price(), yield, maturityInYears); // FIXME: does not take into account multiple payments per year
    }
}
