package uom.ics22116.atmservice.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uom.ics22116.atmservice.Entity.Account;
import uom.ics22116.atmservice.Entity.AppUser;
import uom.ics22116.atmservice.Entity.Card;
import uom.ics22116.atmservice.Repository.AccountRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    // Create a new account with balance set to 0
    public Account createAccount(Account account) {
        // Set the balance to 0 if it's null
        if (account.getBalance() == null) {
            account.setBalance(BigDecimal.ZERO);
        }
        return accountRepository.save(account);
    }

    // Create a new account with balance set to 0
    public Account registerAccount(AppUser user) {
        Account account = new Account();
        account.setAppUser(user); // Link account to user
        return createAccount(account);
    }

    // Get account by ID
    public Account getAccountById(Integer id) {
        Optional<Account> account = accountRepository.findById(id);
        return account.orElseThrow(() -> new RuntimeException("Account not found with id: " + id));
    }

    // Get all accounts
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    // Update account details
    public Account updateAccount(Integer id, Account account) {
        // Check if account exists
        if (!accountRepository.existsById(id)) {
            throw new RuntimeException("Account not found with id: " + id);
        }
        // Set the ID of the updated account to maintain the same entity
        account.setId(id);
        return accountRepository.save(account);
    }

    // Delete account by ID
    public void deleteAccount(Integer id) {
        if (!accountRepository.existsById(id)) {
            throw new RuntimeException("Account not found with id: " + id);
        }
        accountRepository.deleteById(id);
    }

    public void addCardToAccount(Account account, Card card) {
        account.getCards().add(card); // Add the card to the account's list
        accountRepository.save(account); // Save the updated account
    }
}
