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
import mg.razherana.notes.entities.GroupeMatiere;
import mg.razherana.notes.services.GroupeMatiereService;

@RestController
@RequestMapping("/api/groupes-matieres")
@RequiredArgsConstructor
public class GroupeMatiereController {

  private final GroupeMatiereService groupeMatiereService;

  @GetMapping
  public ApiResponse<Map<String, List<GroupeMatiere>>> getAll() {
    return ApiResponse.success(Map.of("groupesMatieres", groupeMatiereService.findAll()));
  }

  @GetMapping("/{id}")
  public ApiResponse<Map<String, GroupeMatiere>> getById(@PathVariable Long id) {
    return ApiResponse.success(Map.of("groupeMatiere", groupeMatiereService.findById(id)));
  }

  @PostMapping
  public ResponseEntity<ApiResponse<Map<String, GroupeMatiere>>> create(@RequestBody GroupeMatiere groupeMatiere) {
    GroupeMatiere created = groupeMatiereService.create(groupeMatiere);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ApiResponse.success(Map.of("groupeMatiere", created)));
  }

  @PutMapping("/{id}")
  public ApiResponse<Map<String, GroupeMatiere>> update(@PathVariable Long id, @RequestBody GroupeMatiere groupeMatiere) {
    return ApiResponse.success(Map.of("groupeMatiere", groupeMatiereService.update(id, groupeMatiere)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Map<String, Object>>> delete(@PathVariable Long id) {
    groupeMatiereService.delete(id);
    return ResponseEntity.status(HttpStatus.OK)
        .body(ApiResponse.success(Map.of("groupeMatiere", Map.of("id", id))));
  }
}
