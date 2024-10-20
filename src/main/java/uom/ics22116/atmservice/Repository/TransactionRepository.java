package uom.ics22116.atmservice.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import uom.ics22116.atmservice.Entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    // Find all transactions of a specific account
    List<Transaction> findByAccountId(Integer accountId);

    // Find all withdrawals of a specific account
    @Query("SELECT t FROM Transaction t WHERE t.account.id = :accountId AND t.transactionType = 'WITHDRAW'")
    List<Transaction> findAllWithdrawalsByAccountId(@Param("accountId") Integer accountId);

    // Find all deposits of a specific account
    @Query("SELECT t FROM Transaction t WHERE t.account.id = :accountId AND t.transactionType = 'DEPOSIT'")
    List<Transaction> findAllDepositsByAccountId(@Param("accountId") Integer accountId);

    // Find all deposits of a specific account
    @Query("SELECT t FROM Transaction t WHERE t.account.id = :accountId")
    List<Transaction> findAllByAccountId(@Param("accountId") Integer accountId);
}