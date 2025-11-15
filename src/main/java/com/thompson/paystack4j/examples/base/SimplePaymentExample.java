package com.thompson.paystack4j.examples.base;

import com.thompson.paystack.client.PaystackClient;
import com.thompson.paystack.enums.Currency;
import com.thompson.paystack.models.request.TransactionInitRequest;
import com.thompson.paystack.models.response.PaystackResponse;
import com.thompson.paystack.models.response.TransactionInitData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Example: Simple payment without subaccount (you handle the split manually)
 */
class SimplePaymentExample {
    private static final Logger logger = LoggerFactory.getLogger(SimplePaymentExample.class);
    private static final String EMAIL1 = PaystackEnvKeyLoader.getEmail1();
    private static final String EMAIL2 = PaystackEnvKeyLoader.getEmail2();
    private static final String SECRET_KEY = PaystackEnvKeyLoader.getPaystackSecretKey();

    public static void main(String[] args) {
        PaystackClient client = new PaystackClient(SECRET_KEY);

        // Initialize a simple payment
        TransactionInitRequest request = TransactionInitRequest.builder()
                .email(EMAIL1)
                .amount(10000.00)  // ₦10,000
                .currency(Currency.NGN)
                .reference("PAY_" + System.currentTimeMillis())
                .build();

        PaystackResponse<TransactionInitData> response = client.transactions().initialize(request);

        if (response.isStatus()) {
            logger.info("Payment URL: {}", response.getData().getAuthorizationUrl());

            // After payment, you manually transfer 90% to seller using Transfer API
            // (Not implemented in this basic version)
        }
    }
}
