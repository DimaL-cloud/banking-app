package ua.dmytrolutsiuk.bankingapp.payload.response;

import java.math.BigDecimal;
import java.time.Instant;

public record AccountDetailsResponse(
        String number,
        String holderName,
        BigDecimal balance,
        Instant createdAt
) {

}
