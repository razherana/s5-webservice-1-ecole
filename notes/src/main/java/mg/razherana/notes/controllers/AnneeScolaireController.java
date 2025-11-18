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
import mg.razherana.notes.entities.AnneeScolaire;
import mg.razherana.notes.services.AnneeScolaireService;
import mg.razherana.notes.api.ApiResponse;

@RestController
@RequestMapping("/api/annees-scolaires")
@RequiredArgsConstructor
public class AnneeScolaireController {

  private final AnneeScolaireService anneeScolaireService;

  @GetMapping
  public ApiResponse<Map<String, List<AnneeScolaire>>> getAll() {
    return ApiResponse.success(Map.of("anneesScolaires", anneeScolaireService.findAll()));
  }

  @GetMapping("/{id}")
  public ApiResponse<Map<String, AnneeScolaire>> getById(@PathVariable Long id) {
    return ApiResponse.success(Map.of("anneeScolaire", anneeScolaireService.findById(id)));
  }

  @PostMapping
  public ResponseEntity<ApiResponse<Map<String, AnneeScolaire>>> create(@RequestBody AnneeScolaire anneeScolaire) {
    AnneeScolaire created = anneeScolaireService.create(anneeScolaire);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ApiResponse.success(Map.of("anneeScolaire", created)));
  }

  @PutMapping("/{id}")
  public ApiResponse<Map<String, AnneeScolaire>> update(@PathVariable Long id, @RequestBody AnneeScolaire anneeScolaire) {
    return ApiResponse.success(Map.of("anneeScolaire", anneeScolaireService.update(id, anneeScolaire)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Map<String, Object>>> delete(@PathVariable Long id) {
    anneeScolaireService.delete(id);
    return ResponseEntity.status(HttpStatus.OK)
        .body(ApiResponse.success(Map.of("anneeScolaire", Map.of("id", id))));
  }
}
