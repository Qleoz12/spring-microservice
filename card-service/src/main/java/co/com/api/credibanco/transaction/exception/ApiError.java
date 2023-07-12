package co.com.api.credibanco.transaction.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ApiError {

    private Long timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;
    private StackTraceElement[] stackTrace;

}
