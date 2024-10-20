package uom.ics22116.atmservice.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Future;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RegistrationRequest {
    @NotBlank(message = "Username is mandatory")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Username can only contain alphanumeric characters")
    private String username;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 6, max = 40, message = "Password must be between 6 and 40 characters")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{6,}$",
            message = "Password must contain at least one digit, one lowercase letter, one uppercase letter, and one special character")
    private String password;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Phone number is mandatory")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Phone number must be between 10 to 15 digits and can optionally start with a '+'")
    private String phone;

    @NotBlank(message = "PIN is mandatory")
    @Pattern(regexp = "^[0-9]{4}$", message = "PIN must be exactly 4 digits")
    private String pin;

    @NotBlank(message = "Card number is mandatory")
    @Pattern(regexp = "^\\d{16}$", message = "Card number must be exactly 16 digits")
    private String cardNumber; // Card details

    @NotBlank(message = "Expiration date is mandatory")
    @Pattern(regexp = "^(0[1-9]|1[0-2])/\\d{2}$", message = "Expiration date must be in the format MM/YY")
    private String expirationDate; // Card expiration

    @NotBlank(message = "CVV is mandatory")
    @Pattern(regexp = "^\\d{3}$", message = "CVV must be exactly 3 digits")
    private String cvv; // Card CVV
}
