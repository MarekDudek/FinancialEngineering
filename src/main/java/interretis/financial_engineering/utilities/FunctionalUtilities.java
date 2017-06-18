package interretis.financial_engineering.utilities;

import java.math.BigDecimal;
import java.util.List;

import static java.math.BigDecimal.ZERO;

public final class FunctionalUtilities {

    public static BigDecimal sum(final List<BigDecimal> numbers) {
        return numbers.stream().reduce(BigDecimal::add).orElse(ZERO);
    }
}
