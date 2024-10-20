package uom.ics22116.atmservice.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "card")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "card_number", unique = true, nullable = false)
    private String cardNumber;

    @Column(name = "expiration_date", nullable = false)
    private String expirationDate;

    @Column(name = "cvv", nullable = false)
    private String cvv;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account; // Each card is linked to one account

}