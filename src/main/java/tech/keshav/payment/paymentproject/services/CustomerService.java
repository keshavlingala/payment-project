package tech.keshav.payment.paymentproject.services;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import tech.keshav.payment.paymentproject.entities.BankBIC;
import tech.keshav.payment.paymentproject.entities.Customer;
import tech.keshav.payment.paymentproject.entities.TransactionItem;
import tech.keshav.payment.paymentproject.models.CustomResponse;
import tech.keshav.payment.paymentproject.models.TransactionPayload;
import tech.keshav.payment.paymentproject.models.TransactionRequest;
import tech.keshav.payment.paymentproject.models.TransactionResponse;
import tech.keshav.payment.paymentproject.repositories.BankRepository;
import tech.keshav.payment.paymentproject.repositories.CustomerRepository;
import tech.keshav.payment.paymentproject.repositories.MessageCodeRepository;
import tech.keshav.payment.paymentproject.repositories.TransactionRepository;
import tech.keshav.payment.paymentproject.util.Permutations;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class CustomerService {

    CustomerRepository customerRepository;

    BankRepository bankRepository;

    MessageCodeRepository messageCodeRepository;

    TransactionRepository transactionRepository;


    public Optional<Customer> getCustomerById(String cid) {
        return customerRepository.findById(cid);
    }

    public BankBIC getBankByBIC(String bic) {
        return bankRepository.findById(bic).get();
    }

    @Transactional
    public ResponseEntity<Object> processTransaction(TransactionRequest request) {


        TransactionPayload payload = request.getPayload();
        Customer customer = customerRepository.findById((request.getPayload().getCustomerId())).get();

        if (bankRepository.findById(payload.getReceiverBIC()).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomResponse("Invalid Receiver BIC", "Receiver BIC is not Valid"));
        }
        if (messageCodeRepository.findById(payload.getMessageCode()).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomResponse("Invalid Message Code", "Message Code is not valid"));
        }
        if (!List.of("O", "C").contains(payload.getTransferTypeCode().toUpperCase())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomResponse("Invalid Transfer Type Code", "Transfer Type Code is not valid"));
        }

        // TODO black list check filter of sender

        if (payload.getTransferTypeCode().equals("O")) {
            Optional<Customer> receiver = customerRepository.findById(payload.getReceiverAccountNumber());
            if (receiver.isEmpty()
                    || !receiver.get().getName().toUpperCase().contains("HDFC BANK")
                    || !customer.getName().toUpperCase().contains("HDFC BANK")) {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new CustomResponse("Transaction Failed: Invalid Receiver A/c",
                        "Receiver and Sender Account number must be an Internal HDFC bank"));
            }
            payload.setReceiverAccountName(receiver.get().getName());
        } else
            try {
                File f1 = ResourceUtils.getFile("classpath:datalist.txt");
                FileReader fr = new FileReader(f1);
                BufferedReader br = new BufferedReader(fr);
                String s;
                String[] words = payload.getReceiverAccountName().split(" ");
                Permutations putil = new Permutations(Arrays.asList(words));
                List<String> permutations = putil.getAllCombinations();
                String pattern = "(" + String.join("|", permutations) + ")";
                System.out.println(pattern);
                Pattern p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
                while ((s = br.readLine()) != null) {
                    Matcher m = p.matcher(s);
                    if (m.find()) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomResponse("Red Alert",
                                "(Busted) The Account Holder name is found in SDNList... This will be reported to higher authority.... DANGER!!! DANGER!!!!"));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


        //end code

        Double transferFee = 0.0025 * payload.getAmount();
        Double totalAmount = payload.getAmount() + transferFee;
        if (customer.getClearBalance() < totalAmount && !customer.getOverdraft()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomResponse("Transaction Failed",
                    "Insuffient Balance in you Account brooo.....!!  :-( Poor Sad Brooo...."));
        }


        TransactionItem transactionItem = new TransactionItem();
        transactionItem.setCustomer(customer);
        transactionItem.setAmount(totalAmount);
        transactionItem.setTransferCode(payload.getTransferTypeCode());
        transactionItem.setTimestamp(new Date());
        transactionItem.setReceiverBIC(bankRepository.findById(payload.getReceiverBIC()).get());
        transactionItem.setMessageCode(messageCodeRepository.findById(payload.getMessageCode()).get());
        transactionItem.setReceiverAccountNumber(payload.getReceiverAccountNumber());
        transactionItem.setReceiverName(payload.getReceiverAccountName());
        transactionRepository.save(transactionItem);
        customer.setClearBalance(customer.getClearBalance() - Math.abs(totalAmount));
        customerRepository.save(customer);
        // Create Transaction Object

        return ResponseEntity.status(HttpStatus.OK)
                .body(new TransactionResponse(request, customer, transferFee, totalAmount, transactionItem));
    }
}
