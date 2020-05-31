package chk.union.wp.common.exception.handler;


import chk.union.wp.common.exception.ApiException;
import chk.union.wp.common.exception.ConflictException;
import chk.union.wp.common.response.BodyErrorResponse;
import chk.union.wp.common.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
    private static final String INVALID_PARAMS_MSG = "Invalid parameters.";
    private static final String VALIDATION_FAILED_MSG = "Validation failed.";
    private static final String DUPLICATES_NOT_ALLOWED = "Duplicates not allowed.";

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(@NonNull final MethodArgumentNotValidException ex,
                                                                  @NonNull final HttpHeaders headers,
                                                                  @NonNull final HttpStatus status,
                                                                  @NonNull final WebRequest request) {
        LOG.error(ex.getLocalizedMessage(), ex);
        ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST, INVALID_PARAMS_MSG, null,
                ex.getBindingResult().getFieldErrors());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(ApiException.class)
    public final ResponseEntity<ErrorResponse> handle(final ApiException ex) {
        LOG.error(ex.getMessage(), ex);
        ErrorResponse response = new ErrorResponse(ex.getStatus(), ex.getMessage(), ex.getCode());
        return new ResponseEntity<>(response, ex.getStatus());
    }

    @ResponseBody
    @ExceptionHandler(ConflictException.class)
    public final ResponseEntity<ErrorResponse> handle(final ConflictException ex) {
        LOG.error(ex.getMessage(), ex);
        ErrorResponse response = new BodyErrorResponse(ex.getStatus(), ex.getMessage(), ex.getData());
        return new ResponseEntity<>(response, ex.getStatus());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public final ResponseEntity<ErrorResponse> handle(final Exception ex) {
        LOG.error(ex.getMessage(), ex);
        ErrorResponse response = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseBody
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityException(final DataIntegrityViolationException ex) {
        LOG.error(ex.getMessage(), ex);

        ErrorResponse response = new ErrorResponse(HttpStatus.CONFLICT, DUPLICATES_NOT_ALLOWED);
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }
}
