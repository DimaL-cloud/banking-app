package ua.dmytrolutsiuk.bankingapp.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.dmytrolutsiuk.bankingapp.exception.NotEnoughFundsException;
import ua.dmytrolutsiuk.bankingapp.model.Account;
import ua.dmytrolutsiuk.bankingapp.model.Transaction;
import ua.dmytrolutsiuk.bankingapp.payload.request.DepositRequest;
import ua.dmytrolutsiuk.bankingapp.payload.request.TransferRequest;
import ua.dmytrolutsiuk.bankingapp.payload.request.WithdrawRequest;
import ua.dmytrolutsiuk.bankingapp.repository.TransactionRepository;
import ua.dmytrolutsiuk.bankingapp.service.AccountService;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Mock
    private AccountService accountService;

    @Mock
    private TransactionRepository transactionRepository;

    private Account account;

    @BeforeEach
    public void setUp() {
        account = new Account();
        account.setNumber("123456789012");
        account.setBalance(BigDecimal.valueOf(1000));
    }

    @Test
    public void testDepositShouldIncreaseBalanceAndSaveTransaction() {
        DepositRequest depositRequest = new DepositRequest(account.getNumber(), BigDecimal.valueOf(500));
        when(accountService.getAccountByNumber(anyString())).thenReturn(account);

        transactionService.deposit(depositRequest);

        assertEquals(BigDecimal.valueOf(1500), account.getBalance());
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }


    @Test
    void testWithdrawShouldDecreaseBalanceAndSaveTransaction() {
        WithdrawRequest withdrawRequest = new WithdrawRequest(account.getNumber(), BigDecimal.valueOf(500));
        when(accountService.getAccountByNumber(anyString())).thenReturn(account);

        transactionService.withdraw(withdrawRequest);

        assertEquals(BigDecimal.valueOf(500), account.getBalance());
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    void testWithdrawShouldThrowNotEnoughFundsExceptionWhenBalanceIsInsufficient() {
        WithdrawRequest withdrawRequest = new WithdrawRequest(account.getNumber(), BigDecimal.valueOf(1500));
        when(accountService.getAccountByNumber(anyString())).thenReturn(account);

        assertThrows(NotEnoughFundsException.class, () -> transactionService.withdraw(withdrawRequest));
    }

    @Test
    void testTransferShouldTransferFundsAndSaveTransactions() {
        Account destinationAccount = new Account();
        destinationAccount.setNumber("987654321098");
        destinationAccount.setBalance(BigDecimal.valueOf(500));

        TransferRequest transferRequest = new TransferRequest(
                account.getNumber(),
                destinationAccount.getNumber(),
                BigDecimal.valueOf(500)
        );
        when(accountService.getAccountByNumber(account.getNumber())).thenReturn(account);
        when(accountService.getAccountByNumber(destinationAccount.getNumber())).thenReturn(destinationAccount);

        transactionService.transfer(transferRequest);

        assertEquals(BigDecimal.valueOf(500), account.getBalance());
        assertEquals(BigDecimal.valueOf(1000), destinationAccount.getBalance());
        verify(transactionRepository, times(2)).save(any(Transaction.class));
    }

    @Test
    void testTransferShouldThrowNotEnoughFundsExceptionWhenSourceAccountHasInsufficientFunds() {
        Account destinationAccount = new Account();
        destinationAccount.setNumber("987654321098");
        destinationAccount.setBalance(BigDecimal.valueOf(500));

        TransferRequest transferRequest = new TransferRequest(
                account.getNumber(),
                destinationAccount.getNumber(),
                BigDecimal.valueOf(1500)
        );
        when(accountService.getAccountByNumber(account.getNumber())).thenReturn(account);
        when(accountService.getAccountByNumber(destinationAccount.getNumber())).thenReturn(destinationAccount);

        assertThrows(NotEnoughFundsException.class, () -> transactionService.transfer(transferRequest));
    }
}