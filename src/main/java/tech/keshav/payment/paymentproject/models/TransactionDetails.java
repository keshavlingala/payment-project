package tech.keshav.payment.paymentproject.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransactionDetails {
    private Double transferFee;
    private Double totalAmount;
    private String transferTypeCode;

}
