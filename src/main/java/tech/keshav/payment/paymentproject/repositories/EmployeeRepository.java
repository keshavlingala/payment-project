package tech.keshav.payment.paymentproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.keshav.payment.paymentproject.entities.Employee;
@Repository
public interface EmployeeRepository extends JpaRepository<Employee,String> {
    Boolean existsByUsername(String username);
}
