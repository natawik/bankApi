package bootcamp.bankApi.service;

import bootcamp.bankApi.dao.CardDao;
import bootcamp.bankApi.models.Card;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CardServiceTest {
    @InjectMocks
    CardService subj;

    @Mock
    CardDao cardDao;

    @Test
    public void testFindAllCardsFound() {
        List<Card> cards = new ArrayList<>();
        Card card1 = new Card();
        card1.setAccountId(1);
        card1.setNumber("12345678");
        Card card2 = new Card();
        card2.setAccountId(1);
        card2.setNumber("12345678");
        cards.add(card1);
        cards.add(card2);
        when(cardDao.findAll()).thenReturn(cards);

        List<Card> cardsForCheck = subj.findAll();

        assertNotNull(cardsForCheck);
        assertFalse(cardsForCheck.isEmpty());
        assertEquals(2, cardsForCheck.size());
        assertEquals(cards, cardsForCheck);

        verify(cardDao, times(1)).findAll();
    }

    @Test
    public void testFindAllCardsNotFound() {
        List<Card> cards = new ArrayList<>();
        when(cardDao.findAll()).thenReturn(cards);

        List<Card> cardsForCheck = subj.findAll();

        assertNotNull(cardsForCheck);
        assertTrue(cardsForCheck.isEmpty());
        assertEquals(cards, cardsForCheck);

        verify(cardDao, times(1)).findAll();
    }

    @Test
    public void testFindCardsByCustomerFound() {
        List<Card> cards = new ArrayList<>();
        Card card1 = new Card();
        card1.setAccountId(1);
        card1.setNumber("2538120846");
        cards.add(card1);
        when(cardDao.findListCardByCustomer(1)).thenReturn(cards);

        List<Card> cardsForCheck = subj.findCardsByCustomer(1);

        assertNotNull(cardsForCheck);
        assertFalse(cardsForCheck.isEmpty());
        assertEquals(1, cardsForCheck.size());
        assertEquals(cards, cardsForCheck);

        verify(cardDao, times(1)).findListCardByCustomer(1);
    }

    @Test
    public void testFindCardsByCustomerNotFound() {
        List<Card> cards = new ArrayList<>();
        when(cardDao.findListCardByCustomer(1)).thenReturn(cards);

        List<Card> cardsForCheck = subj.findCardsByCustomer(1);

        assertNotNull(cardsForCheck);
        assertTrue(cardsForCheck.isEmpty());
        assertEquals(cards, cardsForCheck);

        verify(cardDao, times(1)).findListCardByCustomer(1);
    }

    @Test
    public void testFindCardsByAccountFound() {
        List<Card> cards = new ArrayList<>();
        Card card1 = new Card();
        card1.setAccountId(1);
        card1.setNumber("2538120846");
        cards.add(card1);
        when(cardDao.findListCardByAccount(1)).thenReturn(cards);

        List<Card> cardsForCheck = subj.findCardsByAccount(1);

        assertNotNull(cardsForCheck);
        assertFalse(cardsForCheck.isEmpty());
        assertEquals(1, cardsForCheck.size());
        assertEquals(cards, cardsForCheck);

        verify(cardDao, times(1)).findListCardByAccount(1);
    }

    @Test
    public void testFindCardsByAccountNotFound() {
        List<Card> cards = new ArrayList<>();
        when(cardDao.findListCardByAccount(1)).thenReturn(cards);

        List<Card> cardsForCheck = subj.findCardsByAccount(1);

        assertNotNull(cardsForCheck);
        assertTrue(cardsForCheck.isEmpty());
        assertEquals(cards, cardsForCheck);

        verify(cardDao, times(1)).findListCardByAccount(1);
    }

    @Test
    public void testAddNewCardNotAdded() {

        verifyZeroInteractions(cardDao);
    }

    @Test
    public void testDeleteCardNotDeleted() {
        when(cardDao.findById(1)).thenReturn(null);

        verifyZeroInteractions(cardDao);
    }
}