package bootcamp.bankApi.dao;

import bootcamp.bankApi.models.Customer;
import bootcamp.bankApi.utils.HibernateSessionFactoryUtil;

import java.util.List;

public class CustomerDao extends AbstractDao<Customer> {

    @Override
    public List<Customer> findAll() {
        List<Customer> customers = (List<Customer>) HibernateSessionFactoryUtil
                .getSessionFactory()
                .openSession()
                .createQuery("from Customer")
                .list();
        return customers;
    }
}
