package bootcamp.bankApi.dao;

import bootcamp.bankApi.models.Account;
import bootcamp.bankApi.models.Card;
import bootcamp.bankApi.utils.HibernateSessionFactoryUtil;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class CardDao extends AbstractDao<Card> {

    /*
    Возвращает запись по id
     */
    @Override
    public Card findById(int id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(Card.class, id);
    }

    /*
     Возвращает список всех записей в таблице
      */
    @Override
    public List<Card> findAll() {
        List<Card> cards = (List<Card>) HibernateSessionFactoryUtil
                .getSessionFactory()
                .openSession()
                .createQuery("from Card")
                .list();
        return cards;
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
        Query query = HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("from Card where account_id = :paramName");
        query.setParameter("paramName", accountId);
        return query.list();
    }
}
