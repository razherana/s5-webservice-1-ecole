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
import mg.razherana.notes.entities.UniteEnseignement;
import mg.razherana.notes.services.UniteEnseignementService;

@RestController
@RequestMapping("/api/unites-enseignements")
@RequiredArgsConstructor
public class UniteEnseignementController {

  private final UniteEnseignementService uniteEnseignementService;

  @GetMapping
  public ApiResponse<Map<String, List<UniteEnseignement>>> getAll() {
    return ApiResponse.success(Map.of("unitesEnseignements", uniteEnseignementService.findAll()));
  }

  @GetMapping("/{id}")
  public ApiResponse<Map<String, UniteEnseignement>> getById(@PathVariable Long id) {
    return ApiResponse.success(Map.of("uniteEnseignement", uniteEnseignementService.findById(id)));
  }

  @PostMapping
  public ResponseEntity<ApiResponse<Map<String, UniteEnseignement>>> create(@RequestBody UniteEnseignement unite) {
    UniteEnseignement created = uniteEnseignementService.create(unite);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ApiResponse.success(Map.of("uniteEnseignement", created)));
  }

  @PutMapping("/{id}")
  public ApiResponse<Map<String, UniteEnseignement>> update(@PathVariable Long id,
      @RequestBody UniteEnseignement unite) {
    return ApiResponse.success(Map.of("uniteEnseignement", uniteEnseignementService.update(id, unite)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Map<String, Object>>> delete(@PathVariable Long id) {
    uniteEnseignementService.delete(id);
    return ResponseEntity.status(HttpStatus.OK)
        .body(ApiResponse.success(Map.of("uniteEnseignement", Map.of("id", id))));
  }
}
