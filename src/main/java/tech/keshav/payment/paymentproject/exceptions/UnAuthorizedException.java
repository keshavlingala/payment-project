package tech.keshav.payment.paymentproject.exceptions;

public class UnAuthorizedException extends RuntimeException {
    public UnAuthorizedException() {
        super("UnAuthorized Request");
    }
}
