package chk.union.wp.common.exception;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends ApiException {
    private static final HttpStatus STATUS = HttpStatus.FORBIDDEN;

    public static final String ACCESS_DENIED = "Authenticated user is not authorized to access resource!";

    public ForbiddenException() {
        super(ACCESS_DENIED, STATUS);
    }

    public ForbiddenException(final String message) {
        super(message, STATUS);
    }

    public ForbiddenException(final String message, final String code) {
        super(message, code, STATUS);
    }
}
