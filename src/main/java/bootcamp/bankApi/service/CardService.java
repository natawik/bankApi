package bootcamp.bankApi.service;

import bootcamp.bankApi.dao.CardDao;
import bootcamp.bankApi.models.Card;

import java.util.List;
import java.util.Random;

public class CardService {
    private final CardDao cardDao;

    public CardService(CardDao cardDao) {
        this.cardDao = cardDao;
    }

    static String generateNumber(int length) {
        Random rand = new Random();
        StringBuilder number = new StringBuilder();
        for (int i = 0; i < length; i++) {
            number.append(1 + rand.nextInt(9));
        }
        return number.toString();
    }

    public List<Card> findAllCards() {
        return cardDao.findAll();
    }

    public List<Card> findCardsByCustomer(int customerId) {
        return cardDao.findListCardByCustomer(customerId);
    }

    public List<Card> findCardsByAccount(int accountId) {
        return cardDao.findListCardByAccount(accountId);
    }

    public Card addNewCard(int accountId) {
        Card newCard = new Card();
        newCard.setAccountId(accountId);
        newCard.setNumber(generateNumber(16));
        cardDao.save(newCard);
        return newCard;
    }

    public Card deleteCard(int cardId) {
        Card cardToDelete = cardDao.findById(cardId);
        cardDao.delete(cardToDelete);
        return cardToDelete;
    }

}
