package com.example.wefly_app.test.midtrans;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MidtransImpl {
    public static void main(String[] args) throws JsonProcessingException {
//
//        Map<String, Object> transactionDetails = new HashMap<>();
//        BigDecimal a = BigDecimal.valueOf(50000);
//        transactionDetails.put("order_id", 1);
//        transactionDetails.put("gross_amount", a);
//
//        Map<String, Object> customerDetails = new HashMap<>();
//        customerDetails.put("first_name", "test");
//        customerDetails.put("last_name", "test last name");
//        customerDetails.put("email", "test@gmail.com");
//        customerDetails.put("phone", "08111222333");
//
//        MidTransRequestDTO midtransRequest = new MidTransRequestDTO();
//        midtransRequest.setTransaction_details(transactionDetails);
//        midtransRequest.setCustomer_details(customerDetails);
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        String requestBody = objectMapper
//                .writerWithDefaultPrettyPrinter()
//                .writeValueAsString(midtransRequest);
//
//        String url = "https://app.sandbox.midtrans.com/snap/v1/transactions";
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Accept", "application/json");
//        headers.set("Content-Type", "application/json");
//        String encodedAuth = Base64.getEncoder().encodeToString("SB-Mid-server-PHFxj3MC5t07uSEFTL7nxMsz".getBytes());
//        headers.set("Authorization", "Basic " + encodedAuth);
//
//        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
//
//        System.out.println("reponse : " + response.getBody());

        String serverKey = "SB-Mid-server-PHFxj3MC5t07uSEFTL7nxMsz";

        String passwordToHash = "16" + "201" + "1935000.00" + serverKey;
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] bytes = md.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
       if (Objects.equals(generatedPassword,
               "8c790dea02b5ffb66dafa9c510a7084e19540c5e916fd1e1421a5eda0810c2b329ce3e2989fbd033262923c0022b4229392a39eb40ebac768171d6dbbccba2cd")){
              System.out.println("true");
       } else {
           System.out.println("false");
       }

    }

}
