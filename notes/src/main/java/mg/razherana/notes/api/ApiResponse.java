package mg.razherana.notes.api;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse<T> {

  private String status;
  private T data;
  private ApiError error;

  public static <T> ApiResponse<T> success(T data) {
    return new ApiResponse<>("success", data, null);
  }

  public static <T> ApiResponse<T> error(String code, String message, Object details) {
    return new ApiResponse<>("error", null, new ApiError(code, message, details));
  }
}
