package uom.ics22116.atmservice.user;

import java.util.Collections;
import lombok.RequiredArgsConstructor;
import uom.ics22116.atmservice.account.Account;
import uom.ics22116.atmservice.account.AccountService;
import uom.ics22116.atmservice.account.FrontendAccount;

import org.hibernate.mapping.Collection;
import org.springframework.boot.autoconfigure.pulsar.PulsarProperties.Transaction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.introspect.TypeResolutionContext.Empty;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    private final AccountService accountService;

    @PatchMapping
    public ResponseEntity<?> changePassword(
          @RequestBody ChangePasswordRequest request,
          Principal connectedUser
    ) {
        service.changePassword(request, connectedUser);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Integer id) {
        Optional<User> user = service.getUserById(id);
        
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    @GetMapping("/{id}/accounts")
    public ResponseEntity<FrontendAccount> getUserAccount(@PathVariable Integer id) {
        Optional<User> userOptional = service.getUserById(id);
        
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Account account = accountService.getAccountsByUser(user);

            FrontendAccount frontendAccount = FrontendAccount.builder()
            .id(account.getId())
            .balance(account.getBalance())
            .transactions(Collections.emptyList())
            .build();

            return ResponseEntity.ok(frontendAccount);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/by-token")
    public ResponseEntity<?> getUserByToken(@RequestParam String token) {
        User user = service.getUserByToken(token);
        FrontendUser frontendUser = FrontendUser.builder()
        .id(user.getId())
        .firstname(user.getFirstname())
        .lastname(user.getLastname())
        .email(user.getEmail())
        .build();
        if (frontendUser != null) {
            return ResponseEntity.ok(frontendUser);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found for the given token");
        }
    }


}
