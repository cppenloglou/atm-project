package uom.ics22116.atmservice.transaction.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = TransactionTypeValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidTransactionType {
    String message() default "Invalid transaction type, must be WITHDRAWAL OR DEPOSIT";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

