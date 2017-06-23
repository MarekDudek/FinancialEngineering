package interretis.financial_engineering;

import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static interretis.financial_engineering.Interest.discount;
import static java.math.BigDecimal.ZERO;
import static java.util.Collections.nCopies;

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

    Iterator<BigDecimal> cashFlow()
    {
        final List<BigDecimal> flows = newArrayList(nCopies(maturityInYears * paidPerYear + 1, ZERO));

        return null;
    }
}
