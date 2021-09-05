package tech.keshav.payment.paymentproject.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class TransactionItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer transactionID;
    @ManyToOne
    @JoinColumn(name = "customerId")
    @NotNull
    Customer customer;
    @NotNull
    String receiverName;
    @NotNull
    String receiverAccountNumber;
    @ManyToOne
    @JoinColumn(name = "receiver_bic")
    @NotNull
    BankBIC receiverBIC;
    @ManyToOne
    @JoinColumn(name = "message_code")
    @NotNull
    MessageCode messageCode;
    @NotNull
    String transferCode;
    @NotNull
    Double amount;
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    Date timestamp;

}
