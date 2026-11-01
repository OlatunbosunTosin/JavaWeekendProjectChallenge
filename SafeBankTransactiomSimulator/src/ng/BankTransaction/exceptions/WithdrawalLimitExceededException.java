package ng.BankTransaction.exceptions;

public class WithdrawalLimitExceededException extends RuntimeException{
    public WithdrawalLimitExceededException(String message) {
        super(message);
    }
}
