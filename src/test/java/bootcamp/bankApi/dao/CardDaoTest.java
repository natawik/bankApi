package bootcamp.bankApi.dao;

import bootcamp.bankApi.models.Card;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class CardDaoTest {

    @Test
    public void saveCard() {
        CardDao cardDao = new CardDao();
        Card card = new Card("3037 5858 6763 7382", 1);
        cardDao.save(card);
        Card newCard = cardDao.findById(card.getId());

        assertEquals(card.getNumber(), newCard.getNumber());
    }

    @Test
    public void updateCard() {
        CardDao cardDao = new CardDao();
        Card card = cardDao.findById(2);
        card.setNumber("3037 5858 6763 7382");
        cardDao.update(card);
        Card newCard = cardDao.findById(card.getId());

        assertEquals(card.getNumber(), newCard.getNumber());
    }

    @Test
    public void deleteCard() {
        CardDao cardDao = new CardDao();
        Card card = cardDao.findById(4);
        cardDao.delete(card);

        assertNull(cardDao.findById(card.getId()));
    }

    @Test
    public void findAllNotNull() {
        CardDao cardDao = new CardDao();
        List<Card> cards = cardDao.findAll();

        assertNotNull(cards);
    }

    @Test
    public void findAll() {
        CardDao cardDao = new CardDao();
        List<Card> cards = cardDao.findAll();

        assertEquals(cards.size() > 0, true);
    }

    @Test
    public void findById() {
        CardDao cardDao = new CardDao();
        Card card = cardDao.findById(5);

        assertNotNull(card);
    }

    @Test
    public void findListCardByCustomer() {
        CardDao cardDao = new CardDao();
        List<Card> cards = cardDao.findListCardByCustomer(1);

        assertNotNull(cards);
    }

    @Test
    public void findListCardByAccount() {
        CardDao cardDao = new CardDao();
        List<Card> cards = cardDao.findListCardByAccount(1);

        assertNotNull(cards);
    }
}