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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import mg.razherana.notes.api.ApiResponse;
import mg.razherana.notes.entities.Option;
import mg.razherana.notes.services.OptionService;

@RestController
@RequestMapping("/api/options")
@RequiredArgsConstructor
public class OptionController {

  private final OptionService optionService;

  @GetMapping
  public ApiResponse<Map<String, List<Option>>> getAll(
      @RequestParam(required = false) Long semestreId) {
    List<Option> options = semestreId != null ? optionService.findBySemestre(semestreId) : optionService.findAll();
    return ApiResponse.success(Map.of("options", options));
  }

  @GetMapping("/{id}")
  public ApiResponse<Map<String, Option>> getById(@PathVariable Long id) {
    return ApiResponse.success(Map.of("option", optionService.findById(id)));
  }

  @PostMapping
  public ResponseEntity<ApiResponse<Map<String, Option>>> create(@RequestBody Option option) {
    Option created = optionService.create(option);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ApiResponse.success(Map.of("option", created)));
  }

  @PutMapping("/{id}")
  public ApiResponse<Map<String, Option>> update(@PathVariable Long id, @RequestBody Option option) {
    return ApiResponse.success(Map.of("option", optionService.update(id, option)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Map<String, Object>>> delete(@PathVariable Long id) {
    optionService.delete(id);
    return ResponseEntity.status(HttpStatus.OK)
        .body(ApiResponse.success(Map.of("option", Map.of("id", id))));
  }
}
