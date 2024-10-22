package uom.ics22116.atmservice.transaction.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import uom.ics22116.atmservice.transaction.TransactionType;

public class TransactionTypeValidator implements ConstraintValidator<ValidTransactionType, TransactionType> {

    @Override
    public boolean isValid(TransactionType type, ConstraintValidatorContext context) {
        return (type == TransactionType.WITHDRAWAL || type == TransactionType.DEPOSIT);
    }
}
