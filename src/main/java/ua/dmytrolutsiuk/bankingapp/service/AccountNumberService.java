package ua.dmytrolutsiuk.bankingapp.service;

import ua.dmytrolutsiuk.bankingapp.model.AccountNumber;

public interface AccountNumberService {

    void generateAccountNumbers(int amount);

    AccountNumber getFreeAccountNumber();

    Long getFreeAccountNumbersAmount();
}
