package com.customer.service;

import com.customer.pojo.Customer;
import com.customer.repo.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final String TOPIC = "customer-topic";

    public Customer createCustomer(Customer customer) {
        Customer savedCustomer = customerRepository.save(customer);
        kafkaTemplate.send(TOPIC, "Customer created: " + savedCustomer.getCustomerId());
        return savedCustomer;
    }

    public List<Customer> getCustomers(String firstName, String lastName, String city, String state) {
        if (firstName != null) {
            return customerRepository.findByFirstName(firstName);
        } else if (lastName != null) {
            return customerRepository.findByLastName(lastName);
        } else if (city != null && state != null) {
            return customerRepository.findByAddresses_CityAndAddresses_State(city, state);
        } else {
            return customerRepository.findAll();
        }
    }
}

