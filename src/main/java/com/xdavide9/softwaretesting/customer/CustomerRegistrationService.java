package com.xdavide9.softwaretesting.customer;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CustomerRegistrationService {

    private final CustomerRepository customerRepository;

    public void registerNewCustomer(CustomerRegistrationRequest request) {
        String phoneNumber = request.customer().getPhoneNumber();
        Optional<Customer> customerOptional = customerRepository
                .findByPhoneNumber(phoneNumber);
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            if (customer.getName().equals(request.customer().getName())) {
                return;
            }
            throw new IllegalStateException(String.format("phone number [%s] is taken", phoneNumber));
        }
        if(request.customer().getId() == null)
            request.customer().setId(UUID.randomUUID());
        customerRepository.save(request.customer());
    }
}
