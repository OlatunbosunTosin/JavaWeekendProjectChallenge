package ng.BankTransaction.dtos.requests;

import java.math.BigDecimal;

public class CreateAccountRequest {
    private String name;
    private BigDecimal openingBalance;
    private String pin;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(BigDecimal openingBalance) {
        this.openingBalance = openingBalance;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

}
