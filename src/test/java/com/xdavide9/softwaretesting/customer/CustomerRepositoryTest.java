package com.xdavide9.softwaretesting.customer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository underTest;

    // test following bsd principles

    @Test
    void itShouldSelectCustomerByPhoneNumber() {
        // given
        String phoneNumber = "1234567890";
        Customer customer = new Customer(UUID.randomUUID(), "customer", phoneNumber);
        // we assume save works because it is implemented by spring data jpa
        underTest.save(customer);
        // when
        // test the query written by me in sql
        Optional<Customer> byPhoneNumber = underTest.findByPhoneNumber(phoneNumber);
        // then
        assertThat(byPhoneNumber)
                .isPresent()
                .hasValueSatisfying(c -> {
                    assertThat(c.getName()).isEqualTo(customer.getName());
                });
    }
}