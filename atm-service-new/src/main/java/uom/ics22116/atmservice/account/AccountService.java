package uom.ics22116.atmservice.account;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uom.ics22116.atmservice.transaction.Transaction;
import uom.ics22116.atmservice.user.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    public void createAccount(User user) {
        var account = Account
                .builder()
                .user(user)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .balance(BigDecimal.ZERO)
                .build();
        accountRepository.save(account);
    }

    public Account findAccountById(Account account) {
        return accountRepository.findById(account.getId())
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
    }

    public Account findAccountById(Integer accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
    }

    public BigDecimal getBalance(Account account) {
        return findAccountById(account).getBalance();
    }

    public BigDecimal getBalance(Integer accountId) {
        return findAccountById(accountId).getBalance();
    }

    public void saveAccount(Account account) {
        accountRepository.save(account);
    }

    public Account getAccountsByUser(User user) {
        return accountRepository.findAllByUser(user).get(0);
    }
}
