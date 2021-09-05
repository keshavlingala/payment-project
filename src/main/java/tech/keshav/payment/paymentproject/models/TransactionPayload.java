package tech.keshav.payment.paymentproject.models;

import lombok.Data;

@Data
public class TransactionPayload{
    private String customerId;
    private String senderBIC;
    private String receiverBIC;
    private String receiverAccountNumber;
    private String receiverAccountName;
    private String transferTypeCode;
    private String messageCode;
    private Double amount;
}