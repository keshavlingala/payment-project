package tech.keshav.payment.paymentproject.entities;


import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class BankBIC {
    @Id
    String bic;
    @NotNull
    String name;



}
