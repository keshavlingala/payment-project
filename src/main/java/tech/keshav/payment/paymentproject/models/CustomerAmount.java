package tech.keshav.payment.paymentproject.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import tech.keshav.payment.paymentproject.entities.Customer;
import tech.keshav.payment.paymentproject.entities.TransactionItem;

@AllArgsConstructor
@Data
public class CustomerAmount {
    TransactionItem transactionItem;
    Double total;
}
