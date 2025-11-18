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
import mg.razherana.notes.entities.MentionResultat;
import mg.razherana.notes.services.MentionResultatService;

@RestController
@RequestMapping("/api/mentions-resultats")
@RequiredArgsConstructor
public class MentionResultatController {

  private final MentionResultatService mentionResultatService;

  @GetMapping
  public ApiResponse<Map<String, List<MentionResultat>>> getAll() {
    return ApiResponse.success(Map.of("mentionsResultats", mentionResultatService.findAll()));
  }

  @GetMapping("/{id}")
  public ApiResponse<Map<String, MentionResultat>> getById(@PathVariable Long id) {
    return ApiResponse.success(Map.of("mentionResultat", mentionResultatService.findById(id)));
  }

  @PostMapping
  public ResponseEntity<ApiResponse<Map<String, MentionResultat>>> create(@RequestBody MentionResultat mention) {
    MentionResultat created = mentionResultatService.create(mention);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ApiResponse.success(Map.of("mentionResultat", created)));
  }

  @PutMapping("/{id}")
  public ApiResponse<Map<String, MentionResultat>> update(@PathVariable Long id, @RequestBody MentionResultat mention) {
    return ApiResponse.success(Map.of("mentionResultat", mentionResultatService.update(id, mention)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Map<String, Object>>> delete(@PathVariable Long id) {
    mentionResultatService.delete(id);
    return ResponseEntity.status(HttpStatus.OK)
        .body(ApiResponse.success(Map.of("mentionResultat", Map.of("id", id))));
  }
}
