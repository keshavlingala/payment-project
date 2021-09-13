package tech.keshav.payment.paymentproject.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.keshav.payment.paymentproject.entities.BankBIC;
import tech.keshav.payment.paymentproject.entities.Customer;
import tech.keshav.payment.paymentproject.models.CustomResponse;
import tech.keshav.payment.paymentproject.models.CustomerAmount;
import tech.keshav.payment.paymentproject.models.TransactionRequest;
import tech.keshav.payment.paymentproject.services.CustomerService;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("SameReturnValue")
@RestController
@RequestMapping("api")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class CustomerController {

    CustomerService customerService;

    @GetMapping("transactions")
    public ResponseEntity<Object> getAllTransaction() {
        return customerService.getAllTransactions();
    }

    @RequestMapping("alive")
    public Boolean isAlive() {
        return true;
    }


    @GetMapping("top")
    public List<Object> getTopCustomers(){
        return customerService.getTopCustomers();
    }
    @GetMapping("message-count")
    public List<Object> messageCount(){
        return this.customerService.countByMessage();
    }


    @GetMapping("customer/{cid}")
    public ResponseEntity<Object> getCustomerById(@PathVariable String cid) {
        Optional<Customer> customer = customerService.getCustomerById(cid);
        System.out.println(customer);
        return customer.<ResponseEntity<Object>>map(value -> ResponseEntity.status(HttpStatus.OK).body(value)).orElseGet(() -> ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        new CustomResponse("Account Doesn't Exist!", "Account with given A/C(" + cid + ") does not Exist!")
                ));
    }

    @GetMapping("bank/{bic}")
    public ResponseEntity<Object> getBankByBIC(@PathVariable String bic) {
        Optional<BankBIC> bank = customerService.getBankByBIC(bic);
        return bank.<ResponseEntity<Object>>map(value -> ResponseEntity.status(HttpStatus.OK).body(value)).orElseGet(() -> ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        new CustomResponse("Bank Doesn't Exist!", "Bank with given BIC(" + bic + ") does not Exist!")
                ));

    }

    @GetMapping("bank-amount")
    public List<Object> getBankAmount() {
        return customerService.getBankAmount();
    }

    @PostMapping("transaction")
    public ResponseEntity<Object> makeTransaction(@RequestBody TransactionRequest request) {
        return customerService.processTransaction(request);
    }

    @GetMapping("messagecodes")
    public ResponseEntity<Object> getAllMessageCodes() {
        return customerService.getMessageCodes();
    }
}
