package com.xdavide9.softwaretesting.payment.stripe;

import com.xdavide9.softwaretesting.payment.CardPaymentCharge;
import com.xdavide9.softwaretesting.payment.CardPaymentCharger;
import com.xdavide9.softwaretesting.payment.Currency;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class StripeCharger implements CardPaymentCharger {

    // this is not an actual implementation of stripe but we could assume it is
    // therefore return a successful charge

    // this could also be used as a testing utility
    // when you want to test your logic following a successful payment but without wasting money on useless api calls
    // to stripe themselves

    @Override
    public CardPaymentCharge chargeCard(String cardSource, BigDecimal amount, Currency currency, String description) {
        return new CardPaymentCharge(true);
    }
}
