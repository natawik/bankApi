package bootcamp.bankApi.dao;

import bootcamp.bankApi.models.Account;
import bootcamp.bankApi.utils.HibernateSessionFactoryUtil;
import org.hibernate.query.Query;

import java.util.List;

public class AccountDao extends AbstractDao<Account> {

    /*
    Возвращает запись по id
     */
    @Override
    public Account findById(int id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(Account.class, id);
    }

    /*
    Возвращает список всех записей в таблице
     */
    @Override
    public List<Account> findAll() {
        List<Account> accounts = (List<Account>) HibernateSessionFactoryUtil
                .getSessionFactory()
                .openSession()
                .createQuery("from Account")
                .list();
        return accounts;
    }

    /*
    Возвращает список счетов для конкретного клиента
     */
    public List<Account> getListAccountForCustomer(int customerId) {
        Query query = HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("from Account where customer_id = :paramName");
        query.setParameter("paramName", customerId);
        return query.list();
    }
}
