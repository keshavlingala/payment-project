package tech.keshav.payment.paymentproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.keshav.payment.paymentproject.entities.MessageCode;

public interface MessageCodeRepository extends JpaRepository<MessageCode, String> {
}
