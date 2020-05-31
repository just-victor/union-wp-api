package chk.union.wp.common.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiException extends RuntimeException {
    private String code;
    private HttpStatus status;

    public ApiException() {
        super();
        this.status = HttpStatus.BAD_REQUEST;
    }

    public ApiException(final String message) {
        super(message);
        this.status = HttpStatus.BAD_REQUEST;
    }

    public ApiException(final String message, final HttpStatus status) {
        super(message);
        this.status = status;
    }

    public ApiException(final String message, final String code, final HttpStatus status) {
        super(message);
        this.code = code;
        this.status = status;
    }
}
