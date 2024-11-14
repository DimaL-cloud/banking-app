package ua.dmytrolutsiuk.bankingapp.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.dmytrolutsiuk.bankingapp.payload.request.DepositRequest;
import ua.dmytrolutsiuk.bankingapp.payload.request.TransferRequest;
import ua.dmytrolutsiuk.bankingapp.payload.request.WithdrawRequest;
import ua.dmytrolutsiuk.bankingapp.service.TransactionService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/deposit")
    public ResponseEntity<Void> deposit(@RequestBody @Valid DepositRequest depositRequest) {
        transactionService.deposit(depositRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/withdraw")
    public ResponseEntity<Void> withdraw(@RequestBody @Valid WithdrawRequest withdrawRequest) {
        transactionService.withdraw(withdrawRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/transfer")
    public ResponseEntity<Void> transfer(@RequestBody @Valid TransferRequest transferRequest) {
        transactionService.transfer(transferRequest);
        return ResponseEntity.ok().build();
    }
}
