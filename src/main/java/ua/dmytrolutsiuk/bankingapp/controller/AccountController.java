package ua.dmytrolutsiuk.bankingapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.dmytrolutsiuk.bankingapp.payload.request.AccountCreationRequest;
import ua.dmytrolutsiuk.bankingapp.payload.response.AccountCreationResponse;
import ua.dmytrolutsiuk.bankingapp.payload.response.AccountDetailsResponse;
import ua.dmytrolutsiuk.bankingapp.payload.response.AccountShortInfoResponse;
import ua.dmytrolutsiuk.bankingapp.service.AccountService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    @GetMapping
    @Operation(summary = "Get all accounts")
    public ResponseEntity<List<AccountShortInfoResponse>> getAllAccounts() {
        return new ResponseEntity<>(accountService.getAllAccounts(), HttpStatus.OK);
    }

    @GetMapping("/{accountNumber}")
    @Operation(summary = "Get account details")
    public ResponseEntity<AccountDetailsResponse> getAccountDetails(
            @PathVariable @NotBlank @Valid String accountNumber
    ) {
        return new ResponseEntity<>(accountService.getAccountDetailsByNumber(accountNumber), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Create account")
    public ResponseEntity<AccountCreationResponse> createAccount(
            @RequestBody @Valid AccountCreationRequest creationRequest
    ) {
        return new ResponseEntity<>(accountService.createAccount(creationRequest), HttpStatus.CREATED);
    }

}
