package dev.codescreen;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.spriningframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
public class TransactionServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransactionServiceApplication.class, args);
    }

    // Endpoint to handle the load request
    @PutMapping("/load")
    public ResponseEntity<>(@RequestBody LoadRequest loadRequest) {
        // Simulate processing the load request and updating the user's balance
        // In a real application, you would perform the necessary business logic here
        double newBalance = loadRequest.getAmount().getDebitOrCredit().equals("CREDIT") ?
                loadRequest.getAmount().getAmount() :
                -loadRequest.getAmount().getAmount(); // For simplicity, assuming CREDIT adds funds and DEBIT removes funds

        // Create a LoadResponse object with the updated balance
        LoadResponse response = new LoadResponse(loadRequest.getUserId(), loadRequest.getMessageId(), newBalance);

        // Return a success response with HTTP status 201
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Endpoint to handle the authorization request
    @PutMapping("/authorization")
    public ResponseEntity<>(@RequestBody AuthorizationRequest authorizationRequest) {
        // Simulate processing the authorization request and checking the user's balance
        // In a real application, you would perform the necessary business logic here
        String responseCode = "APPROVED"; // For simplicity, always approve the authorization

        // Create an AuthorizationResponse object with the response code and current balance
        AuthorizationResponse response = new AuthorizationResponse(
                authorizationRequest.getUserId(),
                authorizationRequest.getMessageId(),
                responseCode,
                authorizationRequest.getTransactionAmount().getDebitOrCredit().equals("DEBIT") ?
                        -authorizationRequest.getTransactionAmount().getAmount() :
                        authorizationRequest.getTransactionAmount().getAmount()); // Simulated balance

        // Return a success response with HTTP status 201
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Request and response classes

    static class LoadRequest {
        private String userId;
        private String messageId;
        private Amount transactionAmount;

        // Getters and setters
    }

    static class AuthorizationRequest {
        private String userId;
        private String messageId;
        private Amount transactionAmount;

        // Getters and setters
    }

    static class LoadResponse {
        private String userId;
        private String messageId;
        private double balance;

        // Constructor, getters, and setters
    }

    static class AuthorizationResponse {
        private String userId;
        private String messageId;
        private String responseCode;
        private double balance;

        // Constructor, getters, and setters
    }

    static class Amount {
        private String amount;
        private String currency;
        private String debitOrCredit;

        // Getters and setters
    }
}
