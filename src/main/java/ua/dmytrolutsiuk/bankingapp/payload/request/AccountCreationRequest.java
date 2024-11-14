package ua.dmytrolutsiuk.bankingapp.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record AccountCreationRequest(
        @NotBlank
        String holderName,

        @NotNull
        @Positive
        BigDecimal balance
) {
}
