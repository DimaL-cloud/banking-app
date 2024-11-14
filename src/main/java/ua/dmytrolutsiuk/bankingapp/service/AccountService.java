package ua.dmytrolutsiuk.bankingapp.service;

import org.springframework.transaction.annotation.Transactional;
import ua.dmytrolutsiuk.bankingapp.model.Account;
import ua.dmytrolutsiuk.bankingapp.payload.request.AccountCreationRequest;
import ua.dmytrolutsiuk.bankingapp.payload.response.AccountCreationResponse;
import ua.dmytrolutsiuk.bankingapp.payload.response.AccountDetailsResponse;
import ua.dmytrolutsiuk.bankingapp.payload.response.AccountShortInfoResponse;

import java.util.List;

public interface AccountService {

    List<AccountShortInfoResponse> getAllAccounts();

    AccountDetailsResponse getAccountDetailsByNumber(String accountNumber);

    AccountCreationResponse createAccount(AccountCreationRequest creationRequest);

    Account getAccountByNumber(String accountNumber);

    void updateAccount(Account account);
}
