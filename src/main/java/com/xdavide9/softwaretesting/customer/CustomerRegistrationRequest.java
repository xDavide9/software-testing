package com.xdavide9.softwaretesting.customer;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CustomerRegistrationRequest(@JsonProperty("customer") Customer customer) {
}
