package uom.ics22116.atmservice.account;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uom.ics22116.atmservice.transaction.Transaction;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FrontendAccount {
    private Integer id;
    private BigDecimal balance;
    private List<Transaction> transactions;
}
