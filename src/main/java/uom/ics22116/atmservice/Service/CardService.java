package uom.ics22116.atmservice.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uom.ics22116.atmservice.Entity.Account;
import uom.ics22116.atmservice.Entity.Card;
import uom.ics22116.atmservice.Repository.CardRepository;

@Service
public class CardService {

    private final CardRepository cardRepository;

    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public Card createCard(String cardNumber, String expirationDate, String cvv, Account account) {
        Card card = new Card();
        card.setCardNumber(cardNumber);
        card.setExpirationDate(expirationDate);
        card.setCvv(cvv);
        card.setAccount(account); // Link card to account
        return cardRepository.save(card);
    }
}
