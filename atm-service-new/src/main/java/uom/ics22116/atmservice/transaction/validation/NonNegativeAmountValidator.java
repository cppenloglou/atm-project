package uom.ics22116.atmservice.transaction.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class NonNegativeAmountValidator implements ConstraintValidator<NonNegativeAmount, BigDecimal> {

    @Override
    public boolean isValid(BigDecimal amount, ConstraintValidatorContext context) {
        return amount == null || amount.compareTo(BigDecimal.ZERO) >= 0;
    }
}
