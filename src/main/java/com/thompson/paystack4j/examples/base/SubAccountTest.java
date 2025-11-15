package com.thompson.paystack4j.examples.base;

import com.thompson.paystack.client.PaystackClient;
import com.thompson.paystack.models.request.SubaccountCreateRequest;
import com.thompson.paystack.models.response.PaystackResponse;
import com.thompson.paystack.models.response.SubaccountData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class SubAccountTest {
    private static final Logger logger = LoggerFactory.getLogger(SubAccountTest.class);
    private static final String EMAIL1 = PaystackEnvKeyLoader.getEmail1();
    private static final String EMAIL2 = PaystackEnvKeyLoader.getEmail2();
    private static final String EMAIL3 = PaystackEnvKeyLoader.getEmail3();
    private static final String SECRET_KEY = PaystackEnvKeyLoader.getPaystackSecretKey();

    private static final PaystackClient client = new PaystackClient(PaystackEnvKeyLoader.getPaystackSecretKey());

    public static void main(String[] args) {


//        PaystackResponse<List<SubaccountData>> subaccounts = client.subaccounts().getAll();
//
//        logger.debug("subaccounts: {}",subaccounts);

        updateSubAccount();
    }


    public static void updateSubAccount() {
        SubaccountCreateRequest subaccountCreateRequest = SubaccountCreateRequest.builder()
                .businessName("Updated Business Name")
                .settlementBank("171")  //  Example bank code
                .accountNumber("account number")  // test account
                .percentageCharge(15.0)
                .active(true)
                .build();
        PaystackResponse<SubaccountData> subaccount = client.subaccounts().update("subaccountcodeOrId", subaccountCreateRequest);
        logger.debug("Updated Subaccount: {}",subaccount);
    }
}
