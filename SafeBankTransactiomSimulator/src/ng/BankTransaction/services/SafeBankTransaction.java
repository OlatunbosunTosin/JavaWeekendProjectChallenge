package ng.BankTransaction.services;

import ng.BankTransaction.dtos.requests.CreateAccountRequest;
import ng.BankTransaction.exceptions.InsufficientFundsException;
import ng.BankTransaction.exceptions.WithdrawalLimitExceededException;
import ng.BankTransaction.model.Account;
import ng.BankTransaction.model.Transaction;
import ng.BankTransaction.model.TransactionType;
import ng.BankTransaction.utils.Validation;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SafeBankTransaction {

    private List<Account> accounts = new ArrayList<>();
    private int count;

    public List<Account> getAccounts() {
        return accounts;
    }

    public Account CreateAccount(CreateAccountRequest request){
        Validation.validateName(request.getName());
        Validation.validateOpeningBalance(request.getOpeningBalance());
        Validation.validatePin(request.getPin());

        Account account = new Account();
        account.setName(request.getName());
        account.setPin(request.getPin());
        account.setAccountNumber(generateAccountNumber());

        accounts.add(account);
        BigDecimal openingBalance = request.getOpeningBalance();

        if (openingBalance.compareTo(BigDecimal.ZERO) > 0) {
            deposit(account.getAccountNumber(), openingBalance);
        }

        return account;

    }

    private String generateAccountNumber(){
        return String.valueOf(++count);
    }

    public void deposit(String accountNumber, BigDecimal amount){
        Account account = findAccount(accountNumber);
        Validation.validatePositiveAmount(amount);
        account.deposit(amount);
        Transaction transaction = new Transaction(LocalDateTime.now(), amount, TransactionType.DEPOSIT, accountNumber);
        transaction.setTransactionId(account.generateTransactionId());
        account.addTransaction(transaction);

    }

    public void withdraw(String accountNumber, BigDecimal amount, String pin){
        Account account = findAccount(accountNumber);
        Validation.validatePositiveAmount(amount);
        if(!pin.equals(account.getPin())) throw new IllegalArgumentException("Invalid pin");
        if(account.getBalance().compareTo(amount) < 0) throw new InsufficientFundsException("Insufficient funds");
        if(amount.compareTo(new BigDecimal("100000")) > 0) throw new WithdrawalLimitExceededException("Single withdrawal limit is ₦100,000");
        account.withdraw(amount);
        Transaction transaction = new Transaction(LocalDateTime.now(), amount, TransactionType.WITHDRAWAL, accountNumber);
        transaction.setTransactionId(account.generateTransactionId());
        account.addTransaction(transaction);

    }

    public BigDecimal checkBalance(String accountNumber, String pin) {
        Account account = findAccount(accountNumber);
        if(!pin.equals(account.getPin())) throw new IllegalArgumentException("Invalid pin");
        return account.getBalance();
    }

    public List<Transaction> viewTransactionHistory(String accountNumber, String pin){
        Account account = findAccount(accountNumber);
        return account.getTransactions();
    }

    private Account findAccount(String accountNumber){
        for(Account account : accounts){
            if(account.getAccountNumber().equals(accountNumber))
                return account;
        }throw new IllegalArgumentException("Account not found");
    }




}
