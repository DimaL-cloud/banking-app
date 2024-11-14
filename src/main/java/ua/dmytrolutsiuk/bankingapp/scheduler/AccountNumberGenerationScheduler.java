package ua.dmytrolutsiuk.bankingapp.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ua.dmytrolutsiuk.bankingapp.service.AccountNumberService;

@Slf4j
@Component
@RequiredArgsConstructor
public class AccountNumberGenerationScheduler {

    private static final int BATCH_SIZE = 10_000;
    private static final Long MIN_EXISTING_NUMBERS_NUMBER = 10_000L;

    private final AccountNumberService accountNumberService;

    @Scheduled(fixedDelay = 30000)
    @SchedulerLock(
            name = "AccountNumberGenerationScheduler_generateAccountNumbersPeriodically",
            lockAtMostFor = "PT5M"
    )
    public void generateAccountNumbersPeriodically() {
        log.info("Starting account number generation task...");
        Long freeAccountsAmount = accountNumberService.getFreeAccountNumbersAmount();
        if (freeAccountsAmount < MIN_EXISTING_NUMBERS_NUMBER) {
            accountNumberService.generateAccountNumbers(BATCH_SIZE);
            log.info("Account number generation task completed. Added {} numbers", BATCH_SIZE);
        } else {
            log.info("Account number generation task completed. No numbers added");
        }
    }
}
