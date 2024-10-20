package uom.ics22116.atmservice.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uom.ics22116.atmservice.Entity.Transaction;
import uom.ics22116.atmservice.Service.TransactionService;

import java.util.List;

@RestController
@RequestMapping("api/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    // Create a new transaction
    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) {
        Transaction createdTransaction = transactionService.createTransaction(transaction);
        return ResponseEntity.ok(createdTransaction);
    }

    // Get transaction by ID
    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Integer id) {
        Transaction transaction = transactionService.getTransactionById(id);
        return ResponseEntity.ok(transaction);
    }

    // Get all transactions by account ID
    @GetMapping("/{accountId}")
    public ResponseEntity<List<Transaction>> getAllTransactions(@PathVariable Integer accountId) {
        List<Transaction> transactions = transactionService.getAllTransactions(accountId);
        return ResponseEntity.ok(transactions);
    }

    // Get all withdrawals by account ID
    @GetMapping("/withdrawals/{accountId}")
    public ResponseEntity<List<Transaction>> getAllWithdrawalsByAccountId(@PathVariable Integer accountId) {
        List<Transaction> withdrawals = transactionService.getAllWithdrawalsByAccountId(accountId);
        return ResponseEntity.ok(withdrawals);
    }

    // Get all deposits by account ID
    @GetMapping("/deposits/{accountId}")
    public ResponseEntity<List<Transaction>> getAllDepositsByAccountId(@PathVariable Integer accountId) {
        List<Transaction> deposits = transactionService.getAllDepositsByAccountId(accountId);
        return ResponseEntity.ok(deposits);
    }

    // Delete transaction by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Integer id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }
}

