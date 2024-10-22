package uom.ics22116.atmservice.transaction;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uom.ics22116.atmservice.transaction.validation.NonNegativeAmount;
import uom.ics22116.atmservice.transaction.validation.ValidTransactionType;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequest {
    @NotNull
    private Integer accountId;
    @NotNull
    @NonNegativeAmount
    private BigDecimal amount;
    @ValidTransactionType
    private TransactionType type;
}
