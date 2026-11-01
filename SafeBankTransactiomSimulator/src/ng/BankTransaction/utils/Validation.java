package ng.BankTransaction.utils;

import ng.BankTransaction.exceptions.InvalidAmountException;
import ng.BankTransaction.exceptions.InvalidNameException;
import ng.BankTransaction.exceptions.InvalidPinException;

import java.math.BigDecimal;

public class Validation {

    public static void validateName(String name){
        if(name.isBlank()) throw new InvalidNameException("Invalid name");
    }

    public static void validateOpeningBalance(BigDecimal balance){
        if(balance.compareTo(BigDecimal.ZERO) < 0) throw new InvalidAmountException("Amount cannot be negative");
    }

    public static void validatePin(String pin){
        String regex = "\\d{4}";
        if(pin == null || !pin.matches(regex)) throw new InvalidPinException("Pin must be four digits");
    }

    public static void validateAmount(BigDecimal amount){
        if(amount.compareTo(BigDecimal.ZERO) <= 0) throw new InvalidAmountException("Amount must be greater than 0");
    }


}
