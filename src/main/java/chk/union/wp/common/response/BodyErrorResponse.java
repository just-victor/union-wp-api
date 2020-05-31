package chk.union.wp.common.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BodyErrorResponse extends ErrorResponse {
    private Object data;

    public BodyErrorResponse(final HttpStatus status, final String message, final Object data) {
        super(status, message);
        this.data = data;
    }
}
