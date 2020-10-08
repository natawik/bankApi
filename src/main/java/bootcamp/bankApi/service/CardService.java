package bootcamp.bankApi.service;

import bootcamp.bankApi.dao.CardDao;
import bootcamp.bankApi.models.Card;

import java.util.List;
import java.util.Random;

public class CardService implements Service<Card> {
    private final CardDao cardDao;

    public CardService() {
        this.cardDao = new CardDao();
    }

    public CardService(CardDao cardDao) {
        this.cardDao = cardDao;
    }

    @Override
    public Card findById(int id) {
        return null;
    }

    @Override
    public void save(Card card) {
        cardDao.save(card);
    }

    @Override
    public void update(Card card) {
        cardDao.update(card);
    }

    @Override
    public void delete(Card card) {
        cardDao.delete(card);
    }

    @Override
    public List<Card> findAll() {
        return cardDao.findAll();
    }

    static String generateNumber(int length) {
        Random rand = new Random();
        StringBuilder number = new StringBuilder();
        for (int i = 0; i < length; i++) {
            number.append(1 + rand.nextInt(9));
            if ((i + 1) % 4 == 0 && (i + 1) < length) number.append(" ");
        }
        return number.toString();
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
        delete(cardToDelete);
        return cardToDelete;
    }
}
