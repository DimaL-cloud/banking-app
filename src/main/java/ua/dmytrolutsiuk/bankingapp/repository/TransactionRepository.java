package ua.dmytrolutsiuk.bankingapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.dmytrolutsiuk.bankingapp.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
