package ua.dmytrolutsiuk.bankingapp.payload.response;

public record AccountShortInfoResponse(
        String number,
        String holderName
){
}
