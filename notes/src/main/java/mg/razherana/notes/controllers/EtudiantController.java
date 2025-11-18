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
import mg.razherana.notes.entities.Etudiant;
import mg.razherana.notes.services.EtudiantService;
import mg.razherana.notes.api.ApiResponse;

@RestController
@RequestMapping("/api/etudiants")
@RequiredArgsConstructor
public class EtudiantController {

  private final EtudiantService etudiantService;

  @GetMapping
  public ApiResponse<Map<String, List<Etudiant>>> getAll() {
    return ApiResponse.success(Map.of("etudiants", etudiantService.findAll()));
  }

  @GetMapping("/{id}")
  public ApiResponse<Map<String, Etudiant>> getById(@PathVariable Long id) {
    return ApiResponse.success(Map.of("etudiant", etudiantService.findById(id)));
  }

  @PostMapping
  public ResponseEntity<ApiResponse<Map<String, Etudiant>>> create(@RequestBody Etudiant etudiant) {
    Etudiant created = etudiantService.create(etudiant);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ApiResponse.success(Map.of("etudiant", created)));
  }

  @PutMapping("/{id}")
  public ApiResponse<Map<String, Etudiant>> update(@PathVariable Long id, @RequestBody Etudiant etudiant) {
    return ApiResponse.success(Map.of("etudiant", etudiantService.update(id, etudiant)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Map<String, Object>>> delete(@PathVariable Long id) {
    etudiantService.delete(id);
    return ResponseEntity.status(HttpStatus.OK)
        .body(ApiResponse.success(Map.of("etudiant", Map.of("id", id))));
  }
}
