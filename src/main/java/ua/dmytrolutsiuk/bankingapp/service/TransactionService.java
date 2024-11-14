package ua.dmytrolutsiuk.bankingapp.service;

import ua.dmytrolutsiuk.bankingapp.payload.request.DepositRequest;
import ua.dmytrolutsiuk.bankingapp.payload.request.TransferRequest;
import ua.dmytrolutsiuk.bankingapp.payload.request.WithdrawRequest;

import java.math.BigDecimal;

public interface TransactionService {

    void deposit(DepositRequest depositRequest);

    void withdraw(WithdrawRequest withdrawRequest);

    void transfer(TransferRequest transferRequest);
}
