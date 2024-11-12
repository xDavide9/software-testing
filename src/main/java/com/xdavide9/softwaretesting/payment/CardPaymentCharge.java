package com.xdavide9.softwaretesting.payment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class CardPaymentCharge {
    private final boolean isCardDebited;
}
