package uom.ics22116.atmservice.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uom.ics22116.atmservice.Entity.AppUser;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Integer> {
    Optional<AppUser> findByUsername(String username);
}