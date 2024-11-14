package ua.dmytrolutsiuk.bankingapp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.dmytrolutsiuk.bankingapp.model.AccountNumber;
import ua.dmytrolutsiuk.bankingapp.repository.AccountNumberRepository;
import ua.dmytrolutsiuk.bankingapp.service.AccountNumberService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountNumberServiceImpl implements AccountNumberService {

    private final AccountNumberRepository accountNumberRepository;

    @Override
    @Transactional
    public void generateAccountNumbers(int amount) {
        List<AccountNumber> newNumbers = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            String number;
            do {
                number = UUID.randomUUID().toString().replace("-", "").substring(0, 12);
            } while (accountNumberRepository.existsByNumber(number));
            AccountNumber accountNumber = new AccountNumber();
            accountNumber.setNumber(number);
            newNumbers.add(accountNumber);
        }
        accountNumberRepository.saveAll(newNumbers);
    }

    @Override
    @Transactional
    public AccountNumber getFreeAccountNumber() {
        Optional<AccountNumber> accountNumberOptional = accountNumberRepository.findFirstByUsedFalse();
        if (accountNumberOptional.isEmpty()) {
            accountNumberOptional = Optional.of(generateAccountNumber());
            log.warn("No free account numbers found. Generated new one: {}", accountNumberOptional.get().getNumber());
        }
        AccountNumber accountNumber = accountNumberOptional.get();
        accountNumber.setUsed(true);
        return accountNumberRepository.save(accountNumber);
    }

    @Override
    public Long getFreeAccountNumbersAmount() {
        return accountNumberRepository.countByUsedFalse();
    }

    private AccountNumber generateAccountNumber() {
        String number;
        do {
            number = UUID.randomUUID().toString().replace("-", "").substring(0, 12);
        } while (accountNumberRepository.existsByNumber(number));
        AccountNumber accountNumber = new AccountNumber();
        accountNumber.setNumber(number);
        return accountNumber;
    }

}
