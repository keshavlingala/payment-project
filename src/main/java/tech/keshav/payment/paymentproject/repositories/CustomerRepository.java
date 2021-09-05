package tech.keshav.payment.paymentproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.keshav.payment.paymentproject.entities.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
}
