package ua.dmytrolutsiuk.bankingapp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.dmytrolutsiuk.bankingapp.exception.NotEnoughFundsException;
import ua.dmytrolutsiuk.bankingapp.model.Account;
import ua.dmytrolutsiuk.bankingapp.model.Transaction;
import ua.dmytrolutsiuk.bankingapp.model.constant.TransactionType;
import ua.dmytrolutsiuk.bankingapp.payload.request.DepositRequest;
import ua.dmytrolutsiuk.bankingapp.payload.request.TransferRequest;
import ua.dmytrolutsiuk.bankingapp.payload.request.WithdrawRequest;
import ua.dmytrolutsiuk.bankingapp.repository.TransactionRepository;
import ua.dmytrolutsiuk.bankingapp.service.AccountService;
import ua.dmytrolutsiuk.bankingapp.service.TransactionService;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final AccountService accountService;
    private final TransactionRepository transactionRepository;

    @Override
    @Transactional
    public void deposit(DepositRequest depositRequest) {
        String accountNumber = depositRequest.accountNumber();
        BigDecimal amount = depositRequest.amount();

        Account account = accountService.getAccountByNumber(accountNumber);
        account.setBalance(account.getBalance().add(amount));
        accountService.updateAccount(account);

        Transaction transaction = new Transaction(account, depositRequest.amount(), TransactionType.DEPOSIT);
        transactionRepository.save(transaction);

        log.info("Deposited {} to account {}", amount, accountNumber);
    }

    @Override
    @Transactional
    public void withdraw(WithdrawRequest withdrawRequest) {
        String accountNumber = withdrawRequest.accountNumber();
        BigDecimal amount = withdrawRequest.amount();
        Account account = accountService.getAccountByNumber(accountNumber);
        if (account.getBalance().compareTo(amount) < 0) {
            throw new NotEnoughFundsException("Not enough funds for withdrawal");
        }
        account.setBalance(account.getBalance().subtract(amount));
        accountService.updateAccount(account);

        Transaction transaction = new Transaction(account, amount, TransactionType.WITHDRAW);
        transactionRepository.save(transaction);

        log.info("Withdrew {} from account {}", amount, accountNumber);
    }

    @Override
    @Transactional
    public void transfer(TransferRequest transferRequest) {
        String sourceAccountNumber = transferRequest.sourceAccountNumber();
        String destinationAccountNumber = transferRequest.destinationAccountNumber();
        BigDecimal amount = transferRequest.amount();

        Account sourceAccount = accountService.getAccountByNumber(sourceAccountNumber);
        Account destinationAccount = accountService.getAccountByNumber(destinationAccountNumber);
        if (sourceAccount.getBalance().compareTo(amount) < 0) {
            throw new NotEnoughFundsException("Not enough funds for transfer");
        }

        sourceAccount.setBalance(sourceAccount.getBalance().subtract(amount));
        accountService.updateAccount(sourceAccount);

        destinationAccount.setBalance(destinationAccount.getBalance().add(amount));
        accountService.updateAccount(destinationAccount);

        Transaction debitTransaction = new Transaction(sourceAccount, amount, TransactionType.TRANSFER);
        transactionRepository.save(debitTransaction);

        Transaction creditTransaction = new Transaction(destinationAccount, amount, TransactionType.TRANSFER);
        transactionRepository.save(creditTransaction);

        log.info("Transferred {} from account {} to account {}", amount, sourceAccountNumber, destinationAccountNumber);
    }
}