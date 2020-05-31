package chk.union.wp.common.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends ApiException {
    private static final String MESSAGE = "Not Found";

    public NotFoundException() {
        super(MESSAGE, HttpStatus.NOT_FOUND);
    }

    public NotFoundException(final String message) {
        super(message, HttpStatus.NOT_FOUND);
    }

    public NotFoundException(final String message, final String code) {
        super(message, code, HttpStatus.NOT_FOUND);
    }
}
