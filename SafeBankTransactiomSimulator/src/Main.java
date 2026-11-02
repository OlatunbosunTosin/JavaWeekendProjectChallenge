import ng.BankTransaction.dtos.requests.CreateAccountRequest;
import ng.BankTransaction.exceptions.*;
import ng.BankTransaction.model.Account;
import ng.BankTransaction.model.Transaction;
import ng.BankTransaction.services.SafeBankTransaction;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class Main{
    private static SafeBankTransaction bankTransaction = new SafeBankTransaction();

    public static void main(String[] args){
        goToMainMenu();
    }

    public static void goToMainMenu(){
        print("""
                    Welcome to Bank
            1. Create Account
            2. deposit
            3. withdraw
            4. check balance
            5. view transaction history
            6. exit
                    """);
        char userChoice = input("What do you want to do? ").charAt(0);
        switch(userChoice){
            case '1' -> createAccount();
            case '2' -> deposit();
            case '3' -> withdraw();
            case '4' -> checkBalance();
            case '5' -> viewTransactionHistory();
            case '6' -> exit();
        }
    }

    private static void viewTransactionHistory() {
        String accountNumber = input("Enter your account number: ");
        String pin = input("Enter your pin: ");
        try {
            List<Transaction> transactionHistory = bankTransaction.viewTransactionHistory(accountNumber, pin);
            print("Your balance is " + transactionHistory.toString() + "\n");
        }
        catch (IllegalArgumentException e){
            print(e.getMessage() + "\n");
        }
        goToMainMenu();
    }


    private static void print(String message) {
        System.out.print(message);
    }

    private static void exit() {
        System.exit(0);
    }

    private static void checkBalance() {
        String accountNumber = input("Enter your account number: ");
        String pin = input("Enter your pin: ");
        try {
            BigDecimal balance = bankTransaction.checkBalance(accountNumber, pin);
            print("Your balance is " + balance + "\n");
        }
        catch (IllegalArgumentException e){
            print(e.getMessage() + "\n");
        }
        goToMainMenu();
    }

    private static void withdraw() {
        String accountNumber = input("Enter your account number: ");
        BigDecimal amount = new BigDecimal(input("Amount: "));
        String pin = input("Enter your pin: ");
        try{
            bankTransaction.withdraw(accountNumber, amount, pin);
            print("Withdrawal successful\n");
        } catch (InvalidAmountException | InsufficientFundsException | WithdrawalLimitExceededException | IllegalArgumentException e) {
            print(e.getMessage() + "\n");
        }

        goToMainMenu();
    }

    private static void deposit() {
        String accountNumber = input("Enter your account number: ");
        BigDecimal amount = new BigDecimal(input("Amount: "));
        try{
            bankTransaction.deposit(accountNumber, amount);
            print("Deposit successful\n");
        } catch (InvalidAmountException | IllegalArgumentException e) {
            print(e.getMessage() + "\n");
        }

        goToMainMenu();
    }

    private static void createAccount() {
        CreateAccountRequest request = new CreateAccountRequest();
        String name = input("Enter your name: ");
        String pin = input("Set your pin: ");
        BigDecimal openingBalance = new BigDecimal(input("Opening Amount: "));

        request.setName(name);
        request.setPin(pin);
        request.setOpeningBalance(openingBalance);
        try {
            Account account = bankTransaction.CreateAccount(request);
            print("Account created successfully\n");
            print("Your Account number is " + account.getAccountNumber() + "\n");
        }catch (InvalidNameException | InvalidAmountException | InvalidPinException | InsufficientFundsException | WithdrawalLimitExceededException | IllegalArgumentException e) {
            print(e.getMessage() + "\n");
        }
        goToMainMenu();
    }

    private static String input(String message) {
        Scanner inputCollector = new Scanner(System.in);
        print(message);
        return inputCollector.nextLine();
    }

}