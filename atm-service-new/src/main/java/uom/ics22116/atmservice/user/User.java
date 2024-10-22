package uom.ics22116.atmservice.user;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uom.ics22116.atmservice.account.Account;
import uom.ics22116.atmservice.token.Token;


import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_user")
public class User implements UserDetails {

  @Id
  @GeneratedValue
  private Integer id;

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
  @Column(unique = true)
  private String email;

  @NotBlank(message = "Password is mandatory")
  @NotNull(message = "Password is mandatory")
  @Size(min = 6, message = "Password must be at least 6 characters")
  private String password;

  @Enumerated(EnumType.STRING)
  @NotNull(message = "Role is mandatory")
  private Role role;

  @OneToMany(mappedBy = "user")
  private List<Token> tokens;

  @OneToOne(mappedBy = "user")
  private Account account;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return role.getAuthorities();
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
