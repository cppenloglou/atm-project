package uom.ics22116.atmservice.transaction;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uom.ics22116.atmservice.account.AccountService;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountService accountService;

    public Transaction createTransaction(TransactionRequest transactionRequest) {
        // Fetch the account from the repository
        var account = accountService.findAccountById(transactionRequest.getAccountId());

        BigDecimal amount = transactionRequest.getAmount();
        TransactionType type = transactionRequest.getType();

        // Handle transaction based on type
        switch (type) {
            case WITHDRAWAL:
                // Check if balance is sufficient
                if (account.getBalance().compareTo(amount) < 0) {
                    throw new IllegalArgumentException("Insufficient funds");
                }
                // Deduct the amount from the account balance
                account.setBalance(account.getBalance().subtract(amount));
                break;

            case DEPOSIT:
                // Add the amount to the account balance
                account.setBalance(account.getBalance().add(amount));
                break;

            default:
                throw new IllegalArgumentException("Invalid transaction type");
        }

        // Save the updated account
        accountService.saveAccount(account);

        // Create and save the transaction record
        var transaction = Transaction.builder()
        .account(account)
        .amount(amount)
        .transactionType(type)
        .createdAt(LocalDateTime.now())
        .build(); // Assuming you have a date field

        return transactionRepository.save(transaction);
    }
}
