package chk.union.wp.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ConflictException extends ApiException {
    private Object data;

    public ConflictException(final String message) {
        super(message, HttpStatus.CONFLICT);
    }

    public ConflictException(final String message, final Object data) {
        super(message, HttpStatus.CONFLICT);
        this.data = data;
    }
}
