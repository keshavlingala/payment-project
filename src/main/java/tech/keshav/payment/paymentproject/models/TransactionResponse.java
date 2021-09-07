package tech.keshav.payment.paymentproject.models;

import lombok.Data;
import tech.keshav.payment.paymentproject.entities.Customer;
import tech.keshav.payment.paymentproject.entities.TransactionItem;

import java.util.Date;
@Data
public class TransactionResponse {
    private Customer sender;
    private TransactionDetails transaction;
    private Date date;
    private String senderBIC;
    private String receiverBIC;
    private String receiverAccountNumber;
    private String receiverAccountName;
    private String messageCode;

    public TransactionResponse(TransactionRequest request, Customer customer, Double transferFee, Double totalAmount, TransactionItem transactionItem) {
        this.sender = customer;
        this.transaction = new TransactionDetails(transferFee,totalAmount,request.getPayload().getTransferTypeCode());
        this.date = transactionItem.getTimestamp();
        this.receiverBIC=request.getPayload().getReceiverBIC();
        this.receiverAccountName = request.getPayload().getReceiverAccountName();
        this.receiverAccountNumber = request.getPayload().getReceiverAccountNumber();
        this.messageCode = request.getPayload().getMessageCode();

    }

}