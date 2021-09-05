package tech.keshav.payment.paymentproject.models;

import lombok.Data;

@Data
public class TransactionRequest {
    private TransactionPayload payload;
}