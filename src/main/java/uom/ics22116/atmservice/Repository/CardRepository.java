package uom.ics22116.atmservice.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uom.ics22116.atmservice.Entity.Card;

public interface CardRepository extends JpaRepository<Card, Integer> {
}