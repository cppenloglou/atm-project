package uom.ics22116.atmservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import uom.ics22116.atmservice.auth.AuthenticationService;
import uom.ics22116.atmservice.auth.RegisterRequest;
import uom.ics22116.atmservice.user.UserRepository;

import static uom.ics22116.atmservice.user.Role.ADMIN;
import static uom.ics22116.atmservice.user.Role.MANAGER;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class AtmServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AtmServiceApplication.class, args);
    }

    @Bean
public CommandLineRunner commandLineRunner(
        AuthenticationService service,
        UserRepository userRepository // Inject your user repository
) {
    return args -> {
        // Check for existing admin
        if (!userRepository.existsByEmail("admin@mail.com")) {
            var admin = RegisterRequest.builder()
                    .firstname("Admin")
                    .lastname("Admin")
                    .email("admin@mail.com")
                    .password("password")
                    .role(ADMIN)
                    .build();
            System.out.println("Admin token: " + service.register(admin).getAccessToken());
        } else {
            System.out.println("Admin already exists.");
        }

        // Check for existing manager
        if (!userRepository.existsByEmail("manager@mail.com")) {
            var manager = RegisterRequest.builder()
                    .firstname("Manager") // Change to "Manager" for clarity
                    .lastname("Manager")
                    .email("manager@mail.com")
                    .password("password")
                    .role(MANAGER)
                    .build();
            System.out.println("Manager token: " + service.register(manager).getAccessToken());
        } else {
            System.out.println("Manager already exists.");
        }
    };
}

}
