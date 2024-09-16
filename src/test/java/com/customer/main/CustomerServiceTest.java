package com.customer.main;

import com.customer.pojo.Address;
import com.customer.pojo.Customer;
import com.customer.repo.CustomerRepository;
import com.customer.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @InjectMocks
    private CustomerService customerService;

    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setCustomerId(1L);
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setSpendingLimit(50000.00);
        customer.setAddresses(Collections.singletonList(new Address("Home", "123 Street", "City", "State", "12345")));
    }

    @Test
    void testCreateCustomer() {
        // Arrange
        Mockito.when(customerRepository.save(Mockito.any(Customer.class))).thenReturn(customer);

        // Act
        Customer createdCustomer = customerService.createCustomer(customer);

        // Assert
        assertNotNull(createdCustomer);
        assertEquals("John", createdCustomer.getFirstName());

        // Verify Kafka message is sent
        Mockito.verify(kafkaTemplate).send(Mockito.eq("customer-topic"), Mockito.contains("Customer created: " + customer.getCustomerId()));
    }

    @Test
    void testGetCustomersByFirstName() {
        // Arrange
        List<Customer> customerList = Collections.singletonList(customer);
        Mockito.when(customerRepository.findByFirstName("John")).thenReturn(customerList);

        // Act
        List<Customer> customers = customerService.getCustomers("John", null, null, null);

        // Assert
        assertEquals(1, customers.size());
        assertEquals("John", customers.get(0).getFirstName());
    }

    @Test
    void testGetAllCustomers() {
        // Arrange
        List<Customer> customerList = Collections.singletonList(customer);
        Mockito.when(customerRepository.findAll()).thenReturn(customerList);

        // Act
        List<Customer> customers = customerService.getCustomers(null, null, null, null);

        // Assert
        assertEquals(1, customers.size());
        assertEquals("John", customers.get(0).getFirstName());
    }
}

