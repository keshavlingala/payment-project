package tech.keshav.payment.paymentproject.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.keshav.payment.paymentproject.entities.Customer;
import tech.keshav.payment.paymentproject.models.CustomResponse;
import tech.keshav.payment.paymentproject.models.TransactionRequest;
import tech.keshav.payment.paymentproject.services.CustomerService;

import java.util.Optional;

@RestController
@RequestMapping("api/user")
@AllArgsConstructor
public class CustomerController {

    CustomerService customerService;

    @RequestMapping("alive")
    public Boolean isAlive() {
        return true;
    }

    @GetMapping("customer/{cid}")
    public ResponseEntity<Object> getCustomerById(@PathVariable String cid) {
        Optional<Customer> customer = customerService.getCustomerById(cid);
        System.out.println(customer);
        return customer.<ResponseEntity<Object>>map(value -> ResponseEntity.status(HttpStatus.OK).body(value)).orElseGet(() -> ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        new CustomResponse("Customer Not Found", "Customer with given ID " + cid + " Not Found")
                ));
    }

    @GetMapping("bank/{bic}")
    public ResponseEntity<Object> getBankByBIC(@PathVariable String bic) {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.getBankByBIC(bic));
    }

    @PostMapping("transaction")
    public ResponseEntity<Object> makeTransaction(@RequestBody TransactionRequest request){
        return customerService.processTransaction(request);
    }
}
