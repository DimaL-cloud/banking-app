package ua.dmytrolutsiuk.bankingapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.dmytrolutsiuk.bankingapp.model.AccountNumber;

import java.util.Optional;

public interface AccountNumberRepository extends JpaRepository<AccountNumber, Long> {

    boolean existsByNumber(String number);

    @Query(
            value = "SELECT * FROM account_number WHERE used = false ORDER BY id LIMIT 1 FOR UPDATE SKIP LOCKED",
            nativeQuery = true
    )
    Optional<AccountNumber> findFirstByUsedFalse();

    Long countByUsedFalse();
}
