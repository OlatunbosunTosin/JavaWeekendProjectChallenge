package ng.BankTransaction.exceptions;

public class InvalidNameException extends RuntimeException{
    public InvalidNameException(String message) {
        super(message);
    }

}
