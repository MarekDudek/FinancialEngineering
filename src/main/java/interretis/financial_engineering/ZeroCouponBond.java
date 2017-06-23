package interretis.financial_engineering;

import lombok.AllArgsConstructor;

import java.math.BigDecimal;

import static interretis.financial_engineering.Interest.discount;

@AllArgsConstructor
final class ZeroCouponBond {

    private final BigDecimal faceValue;
    private final BigDecimal yield;
    private final int paidPerYear;
    private final int maturityInYears;

    BigDecimal price()
    {
        return discount(faceValue, yield, paidPerYear, maturityInYears);
    }
}
