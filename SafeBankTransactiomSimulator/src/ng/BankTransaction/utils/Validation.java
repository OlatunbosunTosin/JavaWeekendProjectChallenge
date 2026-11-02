package ng.BankTransaction.utils;

import ng.BankTransaction.exceptions.InvalidAmountException;
import ng.BankTransaction.exceptions.InvalidNameException;
import ng.BankTransaction.exceptions.InvalidPinException;

import java.math.BigDecimal;

public class Validation {

    public static void validateName(String name){
        if(name == null) throw new IllegalArgumentException();
        if(name.isBlank()) {
            throw new InvalidNameException("Invalid name");
        }
    }

    public static void validateOpeningBalance(BigDecimal balance){
        if(balance == null) throw new IllegalArgumentException();
        if(balance.compareTo(BigDecimal.ZERO) < 0) throw new InvalidAmountException("Amount cannot be negative");
    }

    public static void validatePin(String pin){
        if(pin == null) throw new IllegalArgumentException();
        String regex = "\\d{4}";
        if(pin == null || !pin.matches(regex)) throw new InvalidPinException("Pin must be four digits");
    }

    public static void validatePositiveAmount(BigDecimal amount){
        if(amount == null) throw new IllegalArgumentException();
        if(amount.compareTo(BigDecimal.ZERO) <= 0) throw new InvalidAmountException("Amount must be greater than 0");
    }

    public static void validateNumericAmount(BigDecimal amount){
        if(amount == null) throw new IllegalArgumentException();

        String regex = "^\\d+(\\.\\d+)?$";
        if(!(String.valueOf(amount).matches(regex))) throw new IllegalArgumentException("Invalid Input");
    }



}
