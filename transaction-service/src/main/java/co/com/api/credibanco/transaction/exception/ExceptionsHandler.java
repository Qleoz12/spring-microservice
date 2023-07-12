package co.com.api.credibanco.transaction.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionsHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleException(Exception ex, HttpServletRequest request) {
        final HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        final ApiError apiError = ApiError.builder()
                .timestamp(System.currentTimeMillis())
                .status(status.value())
                .error(ex.getMessage())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .stackTrace(ex.getStackTrace())
                .build();
        return ResponseEntity.status(status).body(apiError);
    }

    @ExceptionHandler({NullPointerException.class})
    public ResponseEntity<Object> handleNullPointerException(Exception ex, HttpServletRequest request) {
        final HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        final ApiError apiError = ApiError.builder()
                .timestamp(System.currentTimeMillis())
                .status(status.value())
                .error("NullPointerException")
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .stackTrace(ex.getStackTrace())
                .build();
        return ResponseEntity.status(status).body(apiError);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        var fieldError=ex.getFieldError();
        var field= fieldError != null && fieldError.getField()!= null ? fieldError.getField() : "";
        var defaultMessage= fieldError != null && fieldError.getDefaultMessage() != null ? fieldError.getDefaultMessage() : "";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiError.builder()
                .timestamp(System.currentTimeMillis())
                .status(status.value())
                .error(field)
                .message("Total Errors:" + ex.getErrorCount() + " First Error:" + defaultMessage)
                .stackTrace(ex.getStackTrace())
                .build());
    }
}
