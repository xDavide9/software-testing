package com.xdavide9.softwaretesting.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerRegistrationServiceTest {

    // use mocks to make tests faster
    // test every edge case (happy path, when customer exists, when phone number is taken...)

    @Mock
    private CustomerRepository customerRepository;

    @Captor
    private ArgumentCaptor<Customer> customerArgumentCaptor;

    private CustomerRegistrationService underTest;

    @BeforeEach
    void setUp() {
        underTest = new CustomerRegistrationService(customerRepository);
    }

    @Test
    void itShouldSaveNewCustomer() {
        // Given a phone number and a customer
        String phoneNumber = "000099";
        Customer customer = new Customer(UUID.randomUUID(), "Maryam", phoneNumber);

        // ... a request
        CustomerRegistrationRequest request = new CustomerRegistrationRequest(customer);

        // ... No customer with phone number passed
        when(customerRepository.findByPhoneNumber(phoneNumber))
                .thenReturn(Optional.empty());

        // When
        underTest.registerNewCustomer(request);

        // Then
        then(customerRepository).should().save(customerArgumentCaptor.capture());
        Customer customerArgumentCaptorValue = customerArgumentCaptor.getValue();
        assertThat(customerArgumentCaptorValue).isEqualTo(customer);
    }

    @Test
    void itShouldSaveNewCustomerWhenIdIsNull() {
        // Given a phone number and a customer
        String phoneNumber = "000099";
        Customer customer = new Customer("Maryam", phoneNumber);

        // ... a request
        CustomerRegistrationRequest request = new CustomerRegistrationRequest(customer);

        // ... No customer with phone number passed
        when(customerRepository.findByPhoneNumber(phoneNumber))
            .thenReturn(Optional.empty());

        // When
        underTest.registerNewCustomer(request);

        // Then
        // use mockito to make assertions on the mocked objects
        then(customerRepository).should().save(customerArgumentCaptor.capture());
        Customer customerArgumentCaptorValue = customerArgumentCaptor.getValue();
        assertThat(customerArgumentCaptorValue.getName())
                .isEqualTo(customer.getName());
        assertThat(customerArgumentCaptorValue.getPhoneNumber())
                .isEqualTo(customer.getPhoneNumber());
        assertThat(customerArgumentCaptorValue.getId()).isNotNull();
    }

    @Test
    void itShouldNotSaveCustomerWhenCustomerExists() {
        // Given a phone number and a customer
        String phoneNumber = "000099";
        Customer customer = new Customer(UUID.randomUUID(), "Maryam", phoneNumber);

        // ... a request
        CustomerRegistrationRequest request = new CustomerRegistrationRequest(customer);

        // ... an existing customer is retuned
        when(customerRepository.findByPhoneNumber(phoneNumber))
                .thenReturn(Optional.of(customer));

        // When
        underTest.registerNewCustomer(request);

        // Then
        then(customerRepository).should(never()).save(any());
    }

    @Test
    void itShouldThrowWhenPhoneNumberIsTaken() {
        // Given a phone number and a customer
        String phoneNumber = "000099";
        Customer customer = new Customer(UUID.randomUUID(), "Maryam", phoneNumber);
        Customer customerTwo = new Customer(UUID.randomUUID(), "John", phoneNumber);

        // ... a request
        CustomerRegistrationRequest request = new CustomerRegistrationRequest(customer);

        // ... No customer with phone number passed
        when(customerRepository.findByPhoneNumber(phoneNumber))
                .thenReturn(Optional.of(customerTwo));
        // When
        // Then
        // can use assertJ to make assertions on the exceptions
        assertThatThrownBy(() -> underTest.registerNewCustomer(request))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining(String.format("phone number [%s] is taken", phoneNumber));

        // Finally
        then(customerRepository).should(never()).save(any(Customer.class));

    }

}