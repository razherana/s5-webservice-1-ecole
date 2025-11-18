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
import mg.razherana.notes.entities.Notes;
import mg.razherana.notes.services.NotesService;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
public class NotesController {

  private final NotesService notesService;

  @GetMapping
  public ApiResponse<Map<String, List<Notes>>> getAll() {
    return ApiResponse.success(Map.of("notes", notesService.findAll()));
  }

  @GetMapping("/semestres/{semestreId}")
  public ApiResponse<Map<String, List<Notes>>> getBySemestre(@PathVariable Long semestreId) {
    return ApiResponse.success(Map.of("notes", notesService.findBySemestre(semestreId)));
  }

  @GetMapping("/semestres/{semestreId}/etudiants/{etudiantId}")
  public ApiResponse<Map<String, List<Notes>>> getBySemestreAndEtudiant(@PathVariable Long semestreId,
      @PathVariable Long etudiantId) {
    return ApiResponse.success(Map.of("notes", notesService.findBySemestreAndEtudiant(semestreId, etudiantId)));
  }

  @GetMapping("/{id}")
  public ApiResponse<Map<String, Notes>> getById(@PathVariable Long id) {
    return ApiResponse.success(Map.of("note", notesService.findById(id)));
  }

  @PostMapping
  public ResponseEntity<ApiResponse<Map<String, Notes>>> create(@RequestBody Notes notes) {
    Notes created = notesService.create(notes);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ApiResponse.success(Map.of("note", created)));
  }

  @PutMapping("/{id}")
  public ApiResponse<Map<String, Notes>> update(@PathVariable Long id, @RequestBody Notes notes) {
    return ApiResponse.success(Map.of("note", notesService.update(id, notes)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Map<String, Object>>> delete(@PathVariable Long id) {
    notesService.delete(id);
    return ResponseEntity.status(HttpStatus.OK)
        .body(ApiResponse.success(Map.of("note", Map.of("id", id))));
  }
}
