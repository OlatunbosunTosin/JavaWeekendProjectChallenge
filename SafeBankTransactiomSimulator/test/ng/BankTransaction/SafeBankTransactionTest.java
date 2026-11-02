package ng.BankTransaction;

import ng.BankTransaction.dtos.requests.CreateAccountRequest;
import ng.BankTransaction.exceptions.*;
import ng.BankTransaction.model.Account;
import ng.BankTransaction.services.SafeBankTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class SafeBankTransactionTest {

    private SafeBankTransaction transaction;

    @BeforeEach
    public void setUp(){
        transaction = new SafeBankTransaction();
    }

    @Test
    public void createAccountWithBlankName_throwInvalidNameException(){
        CreateAccountRequest request = new CreateAccountRequest();
        request.setName(" ");
        assertThrows(InvalidNameException.class,()-> transaction.CreateAccount(request));
    }

    @Test
    public void createAccountWithNegativeOpeningBalance_throwInvalidAmountException(){
        CreateAccountRequest request = new CreateAccountRequest();
        request.setName("Ayo Dele");
        request.setOpeningBalance(new BigDecimal(-1000));
        assertThrows(InvalidAmountException.class,()-> transaction.CreateAccount(request));
    }

    @Test
    public void createAccountWithThreeDigitPin_throwInvalidPinException(){
        CreateAccountRequest request = new CreateAccountRequest();
        request.setName("Ayo Dele");
        request.setOpeningBalance(BigDecimal.ZERO);
        request.setPin("123");
        assertThrows(InvalidPinException.class,()-> transaction.CreateAccount(request));
    }

    @Test
    public void createAccountWithValidDetails_countIs1(){
        CreateAccountRequest request = new CreateAccountRequest();
        request.setName("Ayo Dele");
        request.setOpeningBalance(BigDecimal.ZERO);
        request.setPin("1234");
        transaction.CreateAccount(request);
        assertEquals(1, transaction.getAccounts().size());
    }

    @Test
    public void createTwoAccountWithValidDetails_countIs2(){
        CreateAccountRequest request = new CreateAccountRequest();
        request.setName("Ayo Dele");
        request.setOpeningBalance(BigDecimal.ZERO);
        request.setPin("1234");
        transaction.CreateAccount(request);

        CreateAccountRequest requestTwo = new CreateAccountRequest();
        requestTwo.setName("Ayo Mide");
        requestTwo.setOpeningBalance(BigDecimal.ZERO);
        requestTwo.setPin("1234");
        transaction.CreateAccount(requestTwo);
        assertEquals(2, transaction.getAccounts().size());
    }

    @Test
    public void createAnAccountWithValidDetails_depositNegative500_throwException(){
        CreateAccountRequest request = new CreateAccountRequest();
        request.setName("Ayo Dele");
        request.setOpeningBalance(BigDecimal.ZERO);
        request.setPin("1234");
        transaction.CreateAccount(request);

        assertThrows(InvalidAmountException.class,()-> transaction.deposit("1",new BigDecimal("-500")));
    }

    @Test
    public void createAnAccountWithValidDetails_withdraw1000_throwException(){
        CreateAccountRequest request = new CreateAccountRequest();
        request.setName("Ayo Dele");
        request.setOpeningBalance(BigDecimal.ZERO);
        request.setPin("1234");
        Account account = transaction.CreateAccount(request);
        String acctNumber = account.getAccountNumber();

        assertThrows(InsufficientFundsException.class,()-> transaction.withdraw(acctNumber,new BigDecimal("1000"), "1234"));
    }

    @Test
    public void createAnAccountWithValidDetails_withdraw120000_throwException(){
        CreateAccountRequest request = new CreateAccountRequest();
        request.setName("Ayo Dele");
        request.setOpeningBalance(new BigDecimal("200000"));
        request.setPin("1234");
        Account account = transaction.CreateAccount(request);
        String acctNumber = account.getAccountNumber();

        assertThrows(WithdrawalLimitExceededException.class,()-> transaction.withdraw(acctNumber,new BigDecimal("120000"), "1234"));
    }

    @Test
    public void createAnAccountWithValidDetails_NonNumericInput_throwException(){
        CreateAccountRequest request = new CreateAccountRequest();
        request.setName("Ayo Dele");
        request.setOpeningBalance(new BigDecimal("200000"));
        request.setPin("1234");
        Account account = transaction.CreateAccount(request);
        String acctNumber = account.getAccountNumber();

        assertThrows(IllegalArgumentException.class,()-> transaction.withdraw(acctNumber,new BigDecimal("a"), "1234"));
    }

    @Test
    public void deposit10000_balanceIs10000_withdraw2000_balanceIs8000(){
        CreateAccountRequest request = new CreateAccountRequest();
        request.setName("Ayo Dele");
        request.setOpeningBalance(new BigDecimal("10000"));
        request.setPin("1234");
        Account account = transaction.CreateAccount(request);
        String acctNumber = account.getAccountNumber();
        assertEquals(new BigDecimal("10000"), transaction.checkBalance(acctNumber, "1234"));
        transaction.withdraw(acctNumber,new BigDecimal("2000"), "1234");
        assertEquals(new BigDecimal("8000"), transaction.checkBalance(acctNumber, "1234"));

    }

    @Test
    public void deposit10000_balanceIs10000_withdraw2000_balanceIs8000_transactionIs2(){
        CreateAccountRequest request = new CreateAccountRequest();
        request.setName("Ayo Dele");
        request.setOpeningBalance(new BigDecimal("10000"));
        request.setPin("1234");
        Account account = transaction.CreateAccount(request);
        String acctNumber = account.getAccountNumber();

        assertEquals(new BigDecimal("10000"), account.getBalance());
        transaction.withdraw(acctNumber,new BigDecimal("2000"), "1234");
        assertEquals(new BigDecimal("8000"), account.getBalance());
        assertEquals(2, account.getTransactions().size());

    }

}
