package chk.union.wp.common.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends ApiException {
    private static final HttpStatus STATUS = HttpStatus.BAD_REQUEST;

    public BadRequestException() {
        super("Bad request", STATUS);
    }

    public BadRequestException(final String message) {
        super(message, STATUS);
    }

    public BadRequestException(final String message, final String code) {
        super(message, code, STATUS);
    }
}
