package ua.dmytrolutsiuk.bankingapp.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.dmytrolutsiuk.bankingapp.exception.EntityNotFoundException;
import ua.dmytrolutsiuk.bankingapp.model.Account;
import ua.dmytrolutsiuk.bankingapp.model.AccountNumber;
import ua.dmytrolutsiuk.bankingapp.payload.request.AccountCreationRequest;
import ua.dmytrolutsiuk.bankingapp.repository.AccountRepository;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountServiceImplTest {

    @InjectMocks
    private AccountServiceImpl accountService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountNumberServiceImpl accountNumberService;

    private Account account = new Account("1234567890", "John Doe", BigDecimal.valueOf(1000));

    @Test
    public void testGetAllAccounts() {
        when(accountRepository.findAll()).thenReturn(List.of(account));

        var result = accountService.getAllAccounts();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("1234567890", result.get(0).number());
    }

    @Test
    public void whenAccountExistsThenGetAccountDetailsByNumber() {
        when(accountRepository.findByNumber("1234567890")).thenReturn(java.util.Optional.of(account));

        var result = accountService.getAccountDetailsByNumber("1234567890");

        assertNotNull(result);
        assertEquals("1234567890", result.number());
        assertEquals("John Doe", result.holderName());
        assertEquals(BigDecimal.valueOf(1000), result.balance());
    }

    @Test
    public void whenAccountDoesNotExistThenThrowEntityNotFoundException() {
        when(accountRepository.findByNumber("1234567890")).thenReturn(java.util.Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> accountService.getAccountDetailsByNumber("1234567890"));
    }

    @Test
    public void testCreateAccount() {
        var creationRequest = new AccountCreationRequest(account.getHolderName(), account.getBalance());
        AccountNumber accountNumber = new AccountNumber();
        accountNumber.setNumber(account.getNumber());

        when(accountNumberService.getFreeAccountNumber()).thenReturn(accountNumber);
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        var result = accountService.createAccount(creationRequest);

        assertNotNull(result);
        assertEquals(account.getNumber(), result.number());
        assertEquals(account.getHolderName(), result.holderName());
        assertEquals(account.getBalance(), result.balance());
    }

    @Test
    public void whenAccountExistsThenGetAccountByNumber() {
        when(accountRepository.findByNumber(account.getNumber())).thenReturn(java.util.Optional.of(account));

        var result = accountService.getAccountByNumber(account.getNumber());

        assertNotNull(result);
        assertEquals(account.getNumber(), result.getNumber());
        assertEquals(account.getHolderName(), result.getHolderName());
        assertEquals(account.getBalance(), result.getBalance());
    }

    @Test
    public void testGetAccountByNumberShouldThrowEntityNotFoundException() {
        when(accountRepository.findByNumber(account.getNumber())).thenReturn(java.util.Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> accountService.getAccountByNumber(account.getNumber()));
    }

    @Test
    public void testUpdateAccountShouldThrowEntityNotFoundException() {
        when(accountRepository.existsById(account.getId())).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> accountService.updateAccount(account));
    }
}
