package bootcamp.bankApi.dao;

import bootcamp.bankApi.models.Account;
import bootcamp.bankApi.models.Card;
import bootcamp.bankApi.utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class AccountDao extends AbstractDao<Account> {

    /*
    Возвращает запись по id
     */
    @Override
    public Account findById(int id) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Account account = session.get(Account.class, id);
            return account;
        }
    }

    /*
    Возвращает список всех записей в таблице
     */
    @Override
    public List<Account> findAll() {

        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            List<Account> accounts = (List<Account>) session.createQuery("from Account").list();
            return accounts;
        }
    }

    /*
    Возвращает список счетов для конкретного клиента
     */
    public List<Account> getListAccountForCustomer(int customerId) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Query query = session.createQuery("from Account where customer_id = :paramName");
            query.setParameter("paramName", customerId);
            return query.list();
        }
    }
}
