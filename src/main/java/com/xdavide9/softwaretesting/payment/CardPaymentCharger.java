package com.xdavide9.softwaretesting.payment;

import java.math.BigDecimal;

// use interface to switch implementations

public interface CardPaymentCharger {

    CardPaymentCharge chargeCard(
            String cardSource,
            BigDecimal amount,
            Currency currency,
            String description
    );
}
