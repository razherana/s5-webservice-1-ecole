package mg.razherana.notes.controllers;

import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import mg.razherana.notes.api.ApiException;
import mg.razherana.notes.api.ApiResponse;
import mg.razherana.notes.security.JwtUtil;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final JwtUtil jwtUtil;

  @PostMapping("/login")
  public ResponseEntity<ApiResponse<Map<String, String>>> login(@RequestBody AuthRequest request) {
    try {
      var authToken = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
      authenticationManager.authenticate(authToken);
      String token = jwtUtil.generateToken(request.getUsername());
      return ResponseEntity.ok(ApiResponse.success(Map.of("token", token)));
    } catch (BadCredentialsException ex) {
      throw new ApiException(HttpStatus.UNAUTHORIZED, "INVALID_CREDENTIALS", "Invalid username or password", null);
    }
  }
}