package ua.dmytrolutsiuk.bankingapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.dmytrolutsiuk.bankingapp.model.Account;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByNumber(String number);
}
