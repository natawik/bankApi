package bootcamp.bankApi.dao;

import bootcamp.bankApi.models.Customer;
import bootcamp.bankApi.utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;

import java.util.List;

public class CustomerDao extends AbstractDao<Customer> {

    /*
    Возвращает запись по id
     */
    @Override
    public Customer findById(int id) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            return session.get(Customer.class, id);
        }
    }

    /*
     Возвращает список всех записей в таблице
      */
    @Override
    public List<Customer> findAll() {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            List<Customer> customers = (List<Customer>) session.createQuery("from Customer").list();
            return customers;
        }
    }
}
