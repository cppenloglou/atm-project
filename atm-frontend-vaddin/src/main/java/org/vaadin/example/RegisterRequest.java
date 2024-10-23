package org.vaadin.example;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

  @NotBlank(message = "Firstname is mandatory")
  @NotNull(message = "Firstname is mandatory")
  @Size(min = 2, max = 50, message = "Firstname must be between 2 and 50 characters")
  private String firstname;

  @NotBlank(message = "Lastname is mandatory")
  @NotNull(message = "Lastname is mandatory")
  @Size(max = 50, message = "Lastname must be at most 50 characters")
  private String lastname;

  @NotBlank(message = "Email is mandatory")
  @NotNull(message = "Email is mandatory")
  @Email(message = "Email should be valid")
  private String email;

  @NotBlank(message = "Password is mandatory")
  @NotNull(message = "Password is mandatory")
  @Size(min = 6, message = "Password must be at least 6 characters")
  private String password;

  @NotNull(message = "Role is mandatory")
  private String role;
}
