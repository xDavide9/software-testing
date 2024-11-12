package com.xdavide9.softwaretesting.customer;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "Customer")
@Table(name = "customer")
public class Customer {
    @Id
    private UUID id;
    @NotBlank
    private String name;
    @NotBlank
    private String phoneNumber;

    public Customer(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }
}
