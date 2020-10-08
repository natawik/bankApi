package bootcamp.bankApi.service;

import bootcamp.bankApi.dao.CustomerDao;
import bootcamp.bankApi.models.Customer;

import java.util.List;

public class CustomerService implements Service<Customer> {
    private final CustomerDao customerDao;

    public CustomerService() {
        this.customerDao = new CustomerDao();
    }

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }


    @Override
    public Customer findById(int customerId) {
        return customerDao.findById(customerId);
    }

    @Override
    public void save(Customer customer) {
        customerDao.save(customer);
    }

    @Override
    public void update(Customer customer) {
        customerDao.update(customer);
    }

    @Override
    public void delete(Customer customer) {
        customerDao.delete(customer);
    }

    @Override
    public List<Customer> findAll() {
        return customerDao.findAll();
    }
}
