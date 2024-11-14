package ua.dmytrolutsiuk.bankingapp.exception.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.lang.NonNull;
import org.springframework.web.ErrorResponse;

@RequiredArgsConstructor
public class ErrorResponseImpl implements ErrorResponse {

    private final HttpStatusCode errorCode;
    private final String errorMessage;

    @NonNull
    @Override
    public HttpStatusCode getStatusCode() {
        return errorCode;
    }

    @NonNull
    @Override
    public ProblemDetail getBody() {
        ProblemDetail details = ProblemDetail.forStatus(errorCode);
        details.setDetail(errorMessage);
        return details;
    }
}
