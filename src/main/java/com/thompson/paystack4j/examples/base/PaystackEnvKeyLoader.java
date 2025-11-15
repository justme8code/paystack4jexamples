package com.thompson.paystack4j.examples.base;

import io.github.cdimascio.dotenv.Dotenv;

public class PaystackEnvKeyLoader {
    private static final Dotenv dotenv = Dotenv.load();

    private PaystackEnvKeyLoader() {}

    public static String getPaystackPublicKey() {
        String apiKey = System.getenv("PAYSTACK_PUBLIC_KEY");
        if (apiKey == null) {
            throw new RuntimeException("Environment variable PAYSTACK_PUBLIC_KEY is not set");
        }
        return apiKey;
    }

    public static String getPaystackSecretKey() {
        return dotenv.get("PAYSTACK_SECRET_KEY");
    }
    public static String getEmail1(){
        return dotenv.get("EMAIL1");
    }
    public static String getEmail2(){
        return dotenv.get("EMAIL2");
    }

    public static String getEmail3(){
        return dotenv.get("EMAIL3");
    }
}
