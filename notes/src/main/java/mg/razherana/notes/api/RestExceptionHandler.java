package mg.razherana.notes.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class RestExceptionHandler {

  @ExceptionHandler(ApiException.class)
  public ResponseEntity<ApiResponse<Void>> handleApiException(ApiException ex) {
    System.out.println("Error exception here: " + ex.getMessage());
    ApiResponse<Void> response = ApiResponse.error(ex.getCode(), ex.getMessage(), ex.getDetails());
    return ResponseEntity.status(ex.getStatus()).body(response);
  }

  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<ApiResponse<Void>> handleResponseStatusException(ResponseStatusException ex) {
    HttpStatus status = HttpStatus.valueOf(ex.getStatusCode().value());
    System.out.println("ResponseStatusException: " + ex.getReason());
    ApiResponse<Void> response = ApiResponse.error("ERROR", ex.getReason(), null);
    return ResponseEntity.status(status).body(response);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ApiResponse<Void>> handleMissingBodyException(HttpMessageNotReadableException ex) {
    HttpStatus status = HttpStatus.BAD_REQUEST;
    ApiResponse<Void> response = ApiResponse.error("ERROR", "Missing request body", null);
    return ResponseEntity.status(status).body(response);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse<Void>> handleGenericException(Exception ex) {
    ex.printStackTrace();
    ApiResponse<Void> response = ApiResponse.error("INTERNAL_ERROR", "Unexpected error", null);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
  }
}
