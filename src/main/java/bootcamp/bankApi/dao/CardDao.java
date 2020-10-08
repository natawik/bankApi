package bootcamp.bankApi.dao;

import bootcamp.bankApi.models.Account;
import bootcamp.bankApi.models.Card;
import bootcamp.bankApi.utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class CardDao extends AbstractDao<Card> {

    /*
    Возвращает запись по id
     */
    @Override
    public Card findById(int id) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Card card = session.get(Card.class, id);
            return card;
        }
    }

    /*
     Возвращает список всех записей в таблице
      */
    @Override
    public List<Card> findAll() {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            List<Card> cards = (List<Card>) session.createQuery("from Card").list();
            return cards;
        }
    }

    /*
    Возвращает список всех карточек клиента
     */
    public List<Card> findListCardByCustomer(int customerId) {
        List<Account> accounts = new AccountDao().getListAccountForCustomer(customerId);
        List<Card> cards = new ArrayList<>();
        for (Account account : accounts) {
            cards.addAll(findListCardByAccount(account.getId()));
        }
        return cards;
    }

    /*
    Возвращает список карт, привязанных к счету
     */
    public List<Card> findListCardByAccount(int accountId) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Query query = session.createQuery("from Card where account_id = :paramName");
            query.setParameter("paramName", accountId);
            return query.list();
        }
    }
}
