package chk.union.wp.common.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends ApiException {
    public static final String NOT_AUTHENTICATED_USER = "User is not authenticated!";

    public UnauthorizedException() {
        super(NOT_AUTHENTICATED_USER, HttpStatus.UNAUTHORIZED);
    }

    public UnauthorizedException(final String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }

    public UnauthorizedException(final String message, final String code) {
        super(message, code, HttpStatus.UNAUTHORIZED);
    }
}
