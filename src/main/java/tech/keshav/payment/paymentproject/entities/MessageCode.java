package tech.keshav.payment.paymentproject.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class MessageCode {
    @Id
    String messageCode;
    @NotNull
    String Description;

}
