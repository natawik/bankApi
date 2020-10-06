package bootcamp.bankApi.service;

import bootcamp.bankApi.dao.CustomerDao;
import bootcamp.bankApi.models.Customer;

import java.util.List;

public class CustomerService {
    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public List<Customer> findAllCustomers() {
        return customerDao.findAll();
    }

    public Customer findCustomerById(int customerId) {
        return customerDao.findById(customerId);
    }
}
