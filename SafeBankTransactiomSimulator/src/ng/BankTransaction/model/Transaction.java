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

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
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

    @Override
    public String toString() {
        return "{" + '\n' +
                " transactionId : " + transactionId + '\n' +
                " accountNumber : " + accountNumber + '\n' +
                " transactionType : " + transactionType + '\n' +
                " amount : " + amount + '\n' +
                " date : " + dateTime.toLocalDate() + '\n' +
                " time : " + dateTime.toLocalTime() + '\n' +
                '}';
    }
}
