package bootcamp.bankApi.service;

import bootcamp.bankApi.dao.CustomerDao;
import bootcamp.bankApi.models.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceTest {

    @InjectMocks
    CustomerService subj;

    @Mock
    CustomerDao customerDao;

    @Test
    public void testFindAllCustomersFound() {
        List<Customer> customers = new ArrayList<>();
        Customer customer = new Customer();
        customer.setFullName("Current");
        customers.add(customer);
        when(customerDao.findAll()).thenReturn(customers);

        List<Customer> customersForCheck = subj.findAllCustomers();

        assertNotNull(customersForCheck);
        assertFalse(customersForCheck.isEmpty());
        assertEquals(customers, customersForCheck);

        verify(customerDao, times(1)).findAll();
    }

    @Test
    public void testFindAllCustomersNotFound() {
        List<Customer> customers = new ArrayList<>();
        when(customerDao.findAll()).thenReturn(customers);

        List<Customer> customersForCheck = subj.findAllCustomers();

        assertTrue(customersForCheck.isEmpty());
        assertEquals(customers, customersForCheck);

        verify(customerDao, times(1)).findAll();
    }


    @Test
    public void testFindCustomerByIdFound() {
        Customer customer = new Customer();
        customer.setFullName("Current");
        when(customerDao.findById(1)).thenReturn(customer);

        Customer customerForCheck = subj.findCustomerById(1);

        assertNotNull(customerForCheck);
        assertEquals(customer, customerForCheck);

        verify(customerDao, times(1)).findById(1);
    }

    @Test
    public void testFindCustomerByIdNotFound() {
        when(customerDao.findById(1)).thenReturn(null);

        Customer customerForCheck = subj.findCustomerById(1);

        assertNull(customerForCheck);

        verify(customerDao, times(1)).findById(1);
    }
}