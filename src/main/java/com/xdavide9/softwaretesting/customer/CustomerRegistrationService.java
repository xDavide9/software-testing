package com.xdavide9.softwaretesting.customer;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class CustomerRegistrationService {
    public void registerNewCustomer(@Valid CustomerRegistrationRequest request) {
    }
}
