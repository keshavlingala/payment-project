package tech.keshav.payment.paymentproject.exceptions;

public class AuthenticationFailed extends RuntimeException {
    public AuthenticationFailed(Exception message) {
        super(message);
    }
}
