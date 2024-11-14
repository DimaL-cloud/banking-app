package ua.dmytrolutsiuk.bankingapp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.dmytrolutsiuk.bankingapp.exception.EntityNotFoundException;
import ua.dmytrolutsiuk.bankingapp.model.Account;
import ua.dmytrolutsiuk.bankingapp.model.AccountNumber;
import ua.dmytrolutsiuk.bankingapp.payload.request.AccountCreationRequest;
import ua.dmytrolutsiuk.bankingapp.payload.response.AccountCreationResponse;
import ua.dmytrolutsiuk.bankingapp.payload.response.AccountDetailsResponse;
import ua.dmytrolutsiuk.bankingapp.payload.response.AccountShortInfoResponse;
import ua.dmytrolutsiuk.bankingapp.repository.AccountRepository;
import ua.dmytrolutsiuk.bankingapp.service.AccountNumberService;
import ua.dmytrolutsiuk.bankingapp.service.AccountService;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountNumberService accountNumberService;

    @Override
    public List<AccountShortInfoResponse> getAllAccounts() {
        return accountRepository.findAll().stream()
                .map(account -> new AccountShortInfoResponse(
                        account.getNumber(),
                        account.getHolderName()
                ))
                .toList();
    }

    @Override
    public AccountDetailsResponse getAccountDetailsByNumber(String accountNumber) {
        return accountRepository.findByNumber(accountNumber)
                .map(account -> new AccountDetailsResponse(
                        account.getNumber(),
                        account.getHolderName(),
                        account.getBalance(),
                        account.getCreatedAt()
                ))
                .orElseThrow(() -> new EntityNotFoundException("Account with number " + accountNumber + " not found"));
    }

    @Override
    @Transactional
    public AccountCreationResponse createAccount(AccountCreationRequest creationRequest) {
        AccountNumber accountNumber = accountNumberService.getFreeAccountNumber();
        Account account = new Account(
                accountNumber.getNumber(),
                creationRequest.holderName(),
                creationRequest.balance()
        );
        Account newAccount = accountRepository.save(account);
        log.info("Account created: {}", newAccount);
        return new AccountCreationResponse(
                newAccount.getNumber(),
                newAccount.getHolderName(),
                newAccount.getBalance()
        );
    }

    @Override
    public Account getAccountByNumber(String accountNumber) {
        return accountRepository.findByNumber(accountNumber)
                .orElseThrow(() -> new EntityNotFoundException("Account with number " + accountNumber + " not found"));
    }

    @Override
    @Transactional
    public void updateAccount(Account account) {
        if (!accountRepository.existsById(account.getId())) {
            throw new EntityNotFoundException("Account with id " + account.getId() + " not found");
        }
        accountRepository.save(account);
    }
}
