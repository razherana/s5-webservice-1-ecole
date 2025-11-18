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
import mg.razherana.notes.entities.Semestre;
import mg.razherana.notes.services.SemestreService;
import mg.razherana.notes.api.ApiResponse;

@RestController
@RequestMapping("/api/semestres")
@RequiredArgsConstructor
public class SemestreController {

  private final SemestreService semestreService;

  @GetMapping
  public ApiResponse<Map<String, List<Semestre>>> getAll() {
    return ApiResponse.success(Map.of("semestres", semestreService.findAll()));
  }

  @GetMapping("/{id}")
  public ApiResponse<Map<String, Semestre>> getById(@PathVariable Long id) {
    return ApiResponse.success(Map.of("semestre", semestreService.findById(id)));
  }

  @PostMapping
  public ResponseEntity<ApiResponse<Map<String, Semestre>>> create(@RequestBody Semestre semestre) {
    Semestre created = semestreService.create(semestre);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ApiResponse.success(Map.of("semestre", created)));
  }

  @PutMapping("/{id}")
  public ApiResponse<Map<String, Semestre>> update(@PathVariable Long id, @RequestBody Semestre semestre) {
    return ApiResponse.success(Map.of("semestre", semestreService.update(id, semestre)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Map<String, Object>>> delete(@PathVariable Long id) {
    semestreService.delete(id);
    return ResponseEntity.status(HttpStatus.OK)
        .body(ApiResponse.success(Map.of("semestre", Map.of("id", id))));
  }
}
