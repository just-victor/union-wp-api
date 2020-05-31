package chk.union.wp.common.response;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ResponseWrapper<T> {
    private static final ResponseWrapper responseWrapper = new ResponseWrapper();
    public static <T> ResponseWrapper<T> of(T body) {
        responseWrapper.setBody(body);
        return responseWrapper;
    }

    private T body;
}
