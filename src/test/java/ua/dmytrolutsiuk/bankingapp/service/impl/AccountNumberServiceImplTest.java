package ua.dmytrolutsiuk.bankingapp.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.dmytrolutsiuk.bankingapp.model.AccountNumber;
import ua.dmytrolutsiuk.bankingapp.repository.AccountNumberRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountNumberServiceImplTest {


    @InjectMocks
    private AccountNumberServiceImpl accountNumberService;

    @Mock
    private AccountNumberRepository accountNumberRepository;

    private AccountNumber accountNumber;

    @BeforeEach
    void setUp() {
        accountNumber = new AccountNumber();
        accountNumber.setNumber("123456789012");
        accountNumber.setUsed(false);
    }

    @Test
    void testGenerateAccountNumbersShouldGenerateSpecifiedAmountOfNumbers() {
        int amount = 5;
        when(accountNumberRepository.existsByNumber(anyString())).thenReturn(false);

        accountNumberService.generateAccountNumbers(amount);

        verify(accountNumberRepository, times(amount)).existsByNumber(anyString());
        verify(accountNumberRepository, times(1)).saveAll(anyList());
    }

    @Test
    void testGetFreeAccountNumberShouldReturnFreeAccountNumber() {
        when(accountNumberRepository.findFirstByUsedFalse()).thenReturn(Optional.of(accountNumber));
        when(accountNumberRepository.save(accountNumber)).thenReturn(accountNumber);

        var result = accountNumberService.getFreeAccountNumber();

        assertNotNull(result);
        assertEquals(accountNumber.getNumber(), result.getNumber());
        assertTrue(result.isUsed());
        verify(accountNumberRepository, times(1)).save(accountNumber);
    }

    @Test
    void testGetFreeAccountNumberShouldGenerateNewAccountNumberWhenNoneAreFree() {
        when(accountNumberRepository.findFirstByUsedFalse()).thenReturn(Optional.empty());

        AccountNumber generatedAccountNumber = new AccountNumber();
        generatedAccountNumber.setNumber("987654321098");
        when(accountNumberRepository.save(any(AccountNumber.class))).thenReturn(generatedAccountNumber);

        var result = accountNumberService.getFreeAccountNumber();

        assertNotNull(result);
        assertEquals("987654321098", result.getNumber());
        verify(accountNumberRepository, times(1)).save(any(AccountNumber.class));
    }

    @Test
    void testGetFreeAccountNumbersAmountShouldReturnCorrectCount() {
        long expectedCount = 10;
        when(accountNumberRepository.countByUsedFalse()).thenReturn(expectedCount);

        long result = accountNumberService.getFreeAccountNumbersAmount();

        assertEquals(expectedCount, result);
        verify(accountNumberRepository, times(1)).countByUsedFalse();
    }

}
