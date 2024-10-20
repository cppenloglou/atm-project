package uom.ics22116.atmservice.Service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uom.ics22116.atmservice.Dto.RegistrationRequest;
import uom.ics22116.atmservice.Entity.Account;
import uom.ics22116.atmservice.Entity.AppUser;
import uom.ics22116.atmservice.Entity.Card;
import uom.ics22116.atmservice.Repository.AppUserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AppUserService {

    private final AppUserRepository appUserRepository;

    private final PasswordEncoder passwordEncoder;

    private final AccountService accountService;

    private final CardService cardService;

    public AppUserService(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder, AccountService accountService, CardService cardService) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.accountService = accountService;
        this.cardService = cardService;
    }

    // Create a new user
    public AppUser createUser(AppUser user) {
        // Hash the password before saving
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        String encodedPin = passwordEncoder.encode(user.getPin());
        user.setPin(encodedPin);
        return appUserRepository.save(user);
    }

    // Create a new user
    public void registerUser(RegistrationRequest request) {
        // Create the AppUser
        AppUser user = new AppUser();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        AppUser savedUser = createUser(user);

        // Create the Account using AccountService
        Account savedAccount = accountService.registerAccount(savedUser);

        // Create the Card using CardService
        Card savedCard = cardService.createCard(
                request.getCardNumber(),
                request.getExpirationDate(),
                request.getCvv(),
                savedAccount
        );

        // Add the card to the account
        accountService.addCardToAccount(savedAccount, savedCard);
    }

    // Authenticate user during login
    public boolean authenticateUser(String username, String rawPassword) {
        AppUser user = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User with this " + username + " not found"));

        // Check if the entered password matches the stored hashed password
        return passwordEncoder.matches(rawPassword, user.getPassword());
    }

    // Get user by ID
    public AppUser getUserById(Integer id) {
        Optional<AppUser> user = appUserRepository.findById(id);
        return user.orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    // Get all users
    public List<AppUser> getAllUsers() {
        return appUserRepository.findAll();
    }

    // Update user details
    public AppUser updateUser(Integer id, AppUser appUser) {
        // Check if user exists
        if (!appUserRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        // Set the ID of the updated user to maintain the same entity
        appUser.setId(id);
        return appUserRepository.save(appUser);
    }

    // Delete user by ID
    public void deleteUser(Integer id) {
        if (!appUserRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        appUserRepository.deleteById(id);
    }
}
