package com.thompson.paystack4j.examples.transferapi;

import com.thompson.paystack.client.PaystackClient;
import com.thompson.paystack.models.request.TransactionInitRequest;
import com.thompson.paystack.models.request.TransferInitRequest;
import com.thompson.paystack.models.request.TransferRecipientRequest;
import com.thompson.paystack.models.response.PaystackResponse;
import com.thompson.paystack.models.response.TransactionInitData;
import com.thompson.paystack.models.response.TransferData;
import com.thompson.paystack.models.response.TransferRecipientData;
import com.thompson.paystack4j.examples.base.PaystackEnvKeyLoader;

import java.util.Scanner;

public class TransferExample {

    public static void main(String[] args) {
        // Example: Hold money then transfer later

        PaystackClient client = new PaystackClient(PaystackEnvKeyLoader.getPaystackSecretKey());

        Scanner scanner = new Scanner(System.in);


// STEP 1: Customer pays (money goes to YOUR account)
        System.out.println("STEP 1: Initialize payment for customer.");
        System.out.println("Press Enter to initialize payment...");
        scanner.nextLine();
        TransactionInitRequest paymentRequest = TransactionInitRequest.builder()
                .email("customers_email")
                .amount(10000.00)  // ₦10,000
                .reference("ORDER_" + System.currentTimeMillis())
                .build();

        PaystackResponse<TransactionInitData> payment =
                client.transactions().initialize(paymentRequest);

        System.out.println(payment.getData().getAuthorizationUrl());

        System.out.println("\nOpen the URL above in a browser to complete payment.");
        System.out.println("Press Enter after completing the payment simulation...");
        scanner.nextLine();
// Customer pays... money now in YOUR account

// STEP 2: Create recipient (seller's bank account)
        System.out.println("\nSTEP 2: Create a transfer recipient.");
        System.out.println("Press Enter to create the recipient...");
        scanner.nextLine();
        TransferRecipientRequest recipientRequest = TransferRecipientRequest.builder()
                .name("name")
                .accountNumber("recipient account")
                .bankCode("999992")  // opay
                .build();

        PaystackResponse<TransferRecipientData> recipientResponse =
                client.transfers().createRecipient(recipientRequest);

        String recipientCode = recipientResponse.getData().getRecipientCode();
// Save recipientCode to database

        System.out.println("Recipient created with code: " + recipientCode);

// STEP 3: When ready, send money to seller (keep 10%)
        System.out.println("\nSTEP 3: Initiate transfer to the recipient.");
        System.out.println("Press Enter to send the money...");
        scanner.nextLine();
        TransferInitRequest transferRequest = TransferInitRequest.builder()
                .amount(9000.00)  // Send ₦9,000 (you keep ₦1,000)
                .recipient(recipientCode)
                .reason("Payment for service")
                .reference("TRF_" + System.currentTimeMillis())
                .build();

        PaystackResponse<TransferData> transfer =
                client.transfers().initiate(transferRequest);

        System.out.println("Transfer initiated: " + transfer.getData().getStatus());

        System.out.println("\nExample finished. Press Enter to exit.");
        scanner.nextLine();
    }
}
