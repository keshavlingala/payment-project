package tech.keshav.payment.paymentproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.keshav.payment.paymentproject.entities.BankBIC;

public interface BankRepository extends JpaRepository<BankBIC,String> {
}
