package com.xdavide9.softwaretesting.payment;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PaymentIntegrationTest {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void itShouldCreatePaymentSuccessfully() throws Exception {
        // Given a customer with a UUID and phone number
        UUID customerId = UUID.randomUUID();
        // could use a library like jackson to create the json for us
        String customerJson = "{\n" +
                "  \"customer\": {\n" +
                "    \"id\": \"" + customerId + "\",\n" +
                "    \"name\": \"James\",\n" +
                "    \"phoneNumber\": \"+447000000000\"\n" +
                "  }\n" +
                "}";

        // Register customer
        ResultActions customerRegResultActions = mockMvc.perform(put("/api/v1/customer-registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(customerJson));

        // Payment details
        long paymentId = 1L;
        String paymentJson = "{\n" +
                "  \"payment\": {\n" +
                "    \"paymentId\": " + paymentId + ",\n" +
                "    \"customerId\": \"" + customerId + "\",\n" +
                "    \"amount\": 100.00,\n" +
                "    \"currency\": \"GBP\",\n" +
                "    \"transactionId\": \"x0x0x0x0\",\n" +
                "    \"description\": \"Zakat\"\n" +
                "  }\n" +
                "}";

        // When payment is sent
        ResultActions paymentResultActions = mockMvc.perform(post("/api/v1/payment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(paymentJson));

        // Then both customer registration and payment requests are 200 status code
        customerRegResultActions.andExpect(status().isOk());
        paymentResultActions.andExpect(status().isOk());

        // Payment is stored in db
        assertThat(paymentRepository.findById(paymentId))
                .isPresent()
                .hasValueSatisfying(payment1 -> assertThat(payment1.getCustomerId()).isEqualTo(customerId));
    }
}
