package ua.dmytrolutsiuk.bankingapp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name = "account_number")
@Getter
@Setter
public class AccountNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number", unique = true, nullable = false)
    private String number;

    @Column(name = "used", nullable = false)
    private boolean used = false;

    @CreationTimestamp
    private Instant createdAt;
}
