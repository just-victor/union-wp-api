package chk.union.wp.common.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.FieldError;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class ErrorResponse {
    private HttpStatus status;
    private String message;
    private String code;

    private List<ErrorResponse> errors = Collections.emptyList();

    public ErrorResponse(final String message) {
        this.message = message;
    }

    public ErrorResponse(final HttpStatus status, final String message) {
        this.status = status;
        this.message = message;
    }

    public ErrorResponse(final HttpStatus status, final String message, final String code) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

    public ErrorResponse(final HttpStatus status, final String message, final String code,
                         final List<FieldError> errors) {
        this(status, message, code);
        if (!CollectionUtils.isEmpty(errors)) {
            this.errors = errors.stream()
                    .map(FieldErrorResponse::new)
                    .collect(Collectors.toList());
        }
    }
}
