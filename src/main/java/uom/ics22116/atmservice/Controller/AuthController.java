package uom.ics22116.atmservice.Controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uom.ics22116.atmservice.Dto.LoginRequest;
import uom.ics22116.atmservice.Dto.RegistrationRequest;
import uom.ics22116.atmservice.Service.AppUserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AppUserService appUserService;

    public AuthController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest loginRequest) {
        boolean isAuthenticated = appUserService.authenticateUser(
                loginRequest.getUsername(),
                loginRequest.getPassword());

        if (isAuthenticated) {
            // Proceed with login success (generate JWT, etc.)
            return ResponseEntity.ok("Login successful!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegistrationRequest request) {
        appUserService.registerUser(request);
        return ResponseEntity.ok("User registered successfully");
    }
}
