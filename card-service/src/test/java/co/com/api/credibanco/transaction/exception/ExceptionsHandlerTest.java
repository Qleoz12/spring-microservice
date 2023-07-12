package co.com.api.credibanco.transaction.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExceptionsHandlerTest {

    @Mock
    private HttpServletRequest request;

    ExceptionsHandler handler;

    @BeforeEach
    void setUp() {
        handler = new ExceptionsHandler();
    }


    @Test
    void testHandleException() {


        // Define the expected exception and request URI
        Exception exception = new Exception("Test exception");
        String requestURI = "/api/some-endpoint";
        when(request.getRequestURI()).thenReturn(requestURI);

        // Call the handleException() method
        ResponseEntity<Object> response = handler.handleException(exception, request);

        // Verify the response status code, body, and values
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        ApiError apiError = (ApiError) response.getBody();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), apiError.getStatus());
        assertEquals("Test exception", apiError.getError());
        assertEquals("Test exception", apiError.getMessage());
        assertEquals(requestURI, apiError.getPath());
        assertNotNull(apiError.getTimestamp());
        assertNotNull(apiError.getStackTrace());
    }

    @Test
    void testHandleNullPointerException() {
        // Mock necessary dependencies and create an instance of the ExceptionsHandler
        ExceptionsHandler handler = new ExceptionsHandler();

        // Define the expected exception and request URI
        NullPointerException exception = new NullPointerException("Test NPE");
        String requestURI = "/api/another-endpoint";
        when(request.getRequestURI()).thenReturn(requestURI);

        // Call the handleNullPointerException() method
        ResponseEntity<Object> response = handler.handleNullPointerException(exception, request);

        // Verify the response status code, body, and values
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        ApiError apiError = (ApiError) response.getBody();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), apiError.getStatus());
        assertEquals("NullPointerException", apiError.getError());
        assertEquals("Test NPE", apiError.getMessage());
        assertEquals(requestURI, apiError.getPath());

    }
}
