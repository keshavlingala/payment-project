package tech.keshav.payment.paymentproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tech.keshav.payment.paymentproject.entities.TransactionItem;

import java.util.List;

public interface TransactionRepository extends JpaRepository<TransactionItem, String> {
    @Query(value = "select c.name,sum(t.amount) as total from transaction_item t LEFT JOIN customer c on c.account_number=t.customer_id group by customer_id order by total desc limit 5;", nativeQuery = true)
    List<Object> getTopCustomers();

    @Query(value = "select message_code as code,count(transactionid) as frequency from transaction_item group by message_code", nativeQuery = true)
    List<Object> countByMessage();

    @Query(value = "SELECT b.name, sum(amount) AS total FROM transaction_item LEFT JOIN bankbic b ON b.bic = transaction_item.receiver_bic GROUP BY receiver_bic ORDER BY total DESC", nativeQuery = true)
    List<Object> receiverBankAmount();

}
