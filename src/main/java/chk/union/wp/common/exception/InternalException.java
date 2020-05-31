package chk.union.wp.common.exception;

import org.springframework.http.HttpStatus;

public class InternalException extends ApiException {
    private static final HttpStatus STATUS = HttpStatus.INTERNAL_SERVER_ERROR;

    public InternalException() {
        super("Internal Server Error", STATUS);
    }

    public InternalException(final String message) {
        super(message, STATUS);
    }

    public InternalException(final String message, final String code) {
        super(message, code, STATUS);
    }
}
