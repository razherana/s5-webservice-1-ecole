package mg.razherana.notes.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import mg.razherana.notes.api.ApiResponse;
import mg.razherana.notes.entities.Session;
import mg.razherana.notes.services.SessionService;

@RestController
@RequestMapping("/api/sessions")
@RequiredArgsConstructor
public class SessionController {

  private final SessionService sessionService;

  @GetMapping
  public ApiResponse<Map<String, List<Session>>> getAll() {
    return ApiResponse.success(Map.of("sessions", sessionService.findAll()));
  }

  @GetMapping("/{id}")
  public ApiResponse<Map<String, Session>> getById(@PathVariable Long id) {
    return ApiResponse.success(Map.of("session", sessionService.findById(id)));
  }

  @PostMapping
  public ResponseEntity<ApiResponse<Map<String, Session>>> create(@RequestBody Session session) {
    Session created = sessionService.create(session);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ApiResponse.success(Map.of("session", created)));
  }

  @PutMapping("/{id}")
  public ApiResponse<Map<String, Session>> update(@PathVariable Long id, @RequestBody Session session) {
    return ApiResponse.success(Map.of("session", sessionService.update(id, session)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Map<String, Object>>> delete(@PathVariable Long id) {
    sessionService.delete(id);
    return ResponseEntity.status(HttpStatus.OK)
        .body(ApiResponse.success(Map.of("session", Map.of("id", id))));
  }
}
