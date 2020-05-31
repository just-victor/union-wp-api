package chk.union.wp.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.validation.FieldError;

@Getter
@Setter
@RequiredArgsConstructor
public class FieldErrorResponse extends ErrorResponse {
    private String field;
    private String rejectedValue;

    public FieldErrorResponse(final FieldError error) {
        super(error.getDefaultMessage());
        this.field = error.getField();
        this.rejectedValue = String.valueOf(error.getRejectedValue());
    }
}
