package uom.ics22116.atmservice.Service;

import org.springframework.stereotype.Service;
import uom.ics22116.atmservice.Entity.Transaction;
import uom.ics22116.atmservice.Repository.TransactionRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    // Create a new transaction
    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    // Get transaction by ID
    public Transaction getTransactionById(Integer id) {
        Optional<Transaction> transaction = transactionRepository.findById(id);
        return transaction.orElseThrow(() -> new RuntimeException("Transaction not found with id: " + id));
    }

    // Get all transactions
    public List<Transaction> getAllTransactions(Integer accountId) {
        return transactionRepository.findAllByAccountId(accountId);
    }

    // Get all withdrawals by account ID
    public List<Transaction> getAllWithdrawalsByAccountId(Integer accountId) {
        return transactionRepository.findAllWithdrawalsByAccountId(accountId);
    }

    // Get all deposits by account ID
    public List<Transaction> getAllDepositsByAccountId(Integer accountId) {
        return transactionRepository.findAllDepositsByAccountId(accountId);
    }

    // Delete transaction by ID
    public void deleteTransaction(Integer id) {
        if (!transactionRepository.existsById(id)) {
            throw new RuntimeException("Transaction not found with id: " + id);
        }
        transactionRepository.deleteById(id);
    }
}
