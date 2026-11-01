package ng.BankTransaction.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {
    private String transactionId;
    private String accountNumber;
    private TransactionType transactionType;
    private BigDecimal amount;
    private LocalDateTime dateTime;

    public Transaction(LocalDateTime dateTime, BigDecimal amount, TransactionType transactionType, String accountNumber) {
        this.dateTime = dateTime;
        this.amount = amount;
        this.transactionType = transactionType;
        this.accountNumber = accountNumber;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
