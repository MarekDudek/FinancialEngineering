package interretis.financial_engineering;

import lombok.AllArgsConstructor;

import java.math.BigDecimal;

import static interretis.financial_engineering.Interest.priceAtBasicInterest;

@AllArgsConstructor
final class ZeroCouponBond {

    private final BigDecimal principal;

    BigDecimal price(final BigDecimal interestRate) {
        return priceAtBasicInterest(principal, interestRate);
    }
}
