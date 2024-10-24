package uom.ics22116.atmservice.account;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import uom.ics22116.atmservice.user.User;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    List<Account> findAllByUser(User user);
}