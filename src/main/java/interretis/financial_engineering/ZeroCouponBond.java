package interretis.financial_engineering;

import lombok.AllArgsConstructor;

import java.math.BigDecimal;

import static interretis.financial_engineering.Interest.priceForWorthAtMaturityAtBasicInterest;

@AllArgsConstructor
final class ZeroCouponBond {

    private final BigDecimal principal;

    BigDecimal price(final BigDecimal interestRate) {
        return priceForWorthAtMaturityAtBasicInterest(principal, interestRate);
    }
}
