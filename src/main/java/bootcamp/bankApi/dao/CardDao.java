package bootcamp.bankApi.dao;

import bootcamp.bankApi.models.Account;
import bootcamp.bankApi.models.Card;
import bootcamp.bankApi.utils.HibernateSessionFactoryUtil;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class CardDao extends AbstractDao<Card> {

    @Override
    public List<Card> findAll() {
        List<Card> cards = (List<Card>) HibernateSessionFactoryUtil
                .getSessionFactory()
                .openSession()
                .createQuery("from Card")
                .list();
        return cards;
    }

    public List<Card> findListCardByCustomer(int customerId) {
        List<Account> accounts = new AccountDao().getListAccountForCustomer(customerId);
        List<Card> cards = new ArrayList<>();
        for (Account account : accounts) {
            cards.addAll(findListCardByAccount(account.getId()));
        }
        return cards;
    }

    public List<Card> findListCardByAccount(int accountId) {
        Query query = HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("from Card where account_id = :paramName");
        query.setParameter("paramName", accountId);
        return query.list();
    }
}
