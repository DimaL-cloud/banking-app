package ua.dmytrolutsiuk.bankingapp.payload.response;

import java.math.BigDecimal;

public record AccountCreationResponse(
        String number,
        String holderName,
        BigDecimal balance
) {
}
