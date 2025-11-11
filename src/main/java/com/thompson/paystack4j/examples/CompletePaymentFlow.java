package com.thompson.paystack4j.examples;

import com.thompson.paystack.client.PaystackClient;
import com.thompson.paystack.enums.Currency;
import com.thompson.paystack.enums.TransactionStatus;
import com.thompson.paystack.models.request.TransactionInitRequest;
import com.thompson.paystack.models.response.PaystackResponse;
import com.thompson.paystack.models.response.TransactionData;
import com.thompson.paystack.models.response.TransactionInitData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

 class CompletePaymentFlow {
    private static final Logger logger = LoggerFactory.getLogger(CompletePaymentFlow.class);
    private static final String EMAIL1 = PaystackEnvKeyLoader.getEmail1();
    private static final String EMAIL2 = PaystackEnvKeyLoader.getEmail2();
    private static final String SECRET_KEY = PaystackEnvKeyLoader.getPaystackSecretKey();

    public static void main(String[] args) {
        PaystackClient client = new PaystackClient(SECRET_KEY);
        Scanner scanner = new Scanner(System.in);

        // STEP 1: Initialize Payment
        logger.info("=== Initialize Payment ===\n");

        TransactionInitRequest request = TransactionInitRequest.builder()
                .email(EMAIL1)
                .amount(5000.00)  // ₦5,000
                .currency(Currency.NGN)
                .reference("PAY_" + System.currentTimeMillis())
                .build();

        PaystackResponse<TransactionInitData> initResponse =
                client.transactions().initialize(request);

        if (initResponse.isStatus()) {
            TransactionInitData data = initResponse.getData();

            logger.info("Payment initialized successfully!");
            logger.info("Reference: {}" , data.getReference());
            logger.info("Payment URL: {}" , data.getAuthorizationUrl());

            // STEP 2: Customer pays
            logger.info("=== STEP 2: Make Payment ===\n");
            logger.info("1. Open this URL in your browser:");
            logger.info("Authorization URL {}" , data.getAuthorizationUrl());
            logger.info("2. Use this test card:");
            logger.info("   Card: 4084084084084081");
            logger.info("   CVV: 408");
            logger.info("   Expiry: 12/25");
            logger.info("   PIN: 0000");
            logger.info("   OTP: 123456");

            logger.info("Press ENTER after completing the payment...");
            scanner.nextLine();

            // Verify Payment
            logger.info("\n=== STEP 3: Verify Payment ===\n");

            PaystackResponse<TransactionData> verifyResponse =
                    client.transactions().verify(data.getReference());

            if (verifyResponse.isStatus()) {
                TransactionData transaction = verifyResponse.getData();

                logger.info("Status: {}" , transaction.getStatus());
                logger.info("Amount: ₦ {}" , (transaction.getAmount() / 100.0));

                if (transaction.getStatusEnum() == TransactionStatus.SUCCESS) {
                    logger.info("🎉 Payment Successful!");
                    logger.info("✅ Product/Service can be delivered");
                } else {
                    logger.info("❌ Payment Not Successful");
                    logger.info("Status: {}", transaction.getStatus());
                }
            }
        }
        scanner.close();
    }
}