package bootcamp.bankApi.dao;

import bootcamp.bankApi.models.Account;
import bootcamp.bankApi.models.Customer;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;

public class CustomerDaoTest {

    @Test
    public void saveGetCustomer() {
        Customer customer = new Customer("Джон Смит");
        CustomerDao customerDao = new CustomerDao();
        customerDao.save(customer);
        Customer newCustomer = customerDao.findById(customer.getId());

        assertEquals(customer.getFullName(), newCustomer.getFullName());
    }

    @Test
    public void updateCustomer() {
        CustomerDao customerDao = new CustomerDao();
        Customer customer = customerDao.findById(2);
        customer.setFullName("Смит Джон");
        customerDao.update(customer);
        Customer newCustomer = customerDao.findById(customer.getId());

        assertEquals(customer.getFullName(), newCustomer.getFullName());
    }

    @Test
    public void delete() {
        CustomerDao customerDao = new CustomerDao();
        Customer customer = customerDao.findById(1);
        customerDao.delete(customer);

        assertNull(customerDao.findById(customer.getId()));
    }

    @Test
    public void findByIdNotNull() {
        CustomerDao customerDao = new CustomerDao();
        Customer customer = customerDao.findById(1);

        assertNotNull(customer);
    }

    @Test
    public void findAllIsNotNull() {
        CustomerDao customerDao = new CustomerDao();
        List<Customer> customers = customerDao.findAll();

        assertNotNull(customerDao);
    }

    @Test
    public void findAll() {
        CustomerDao customerDao = new CustomerDao();
        List<Customer> customers = customerDao.findAll();

        assertEquals(customers.size() > 0, true);
    }

    @Test
    public void findById() {
        CustomerDao customerDao = new CustomerDao();
        Customer customer = customerDao.findById(3);

        assertEquals(customer.getId(), 3);
    }
}
