package ua.dmytrolutsiuk.bankingapp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import ua.dmytrolutsiuk.bankingapp.model.constant.TransactionType;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "transaction")
@Getter
@Setter
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "transaction_type", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private TransactionType transactionType;

    @CreationTimestamp
    private Instant createdAt;

    public Transaction() {
    }

    public Transaction(Account account, BigDecimal amount, TransactionType transactionType) {
        this.account = account;
        this.amount = amount;
        this.transactionType = transactionType;
    }
}
