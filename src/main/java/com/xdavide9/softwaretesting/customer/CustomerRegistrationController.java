package com.xdavide9.softwaretesting.customer;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/customer-registration")
public class CustomerRegistrationController {

    @PutMapping
    public void RegisterNewCustomer(@Valid @RequestBody CustomerRegistrationRequest request) {
    }

}
