package mg.razherana.notes.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import mg.razherana.notes.api.ApiResponse;
import mg.razherana.notes.api.ApiException;
import mg.razherana.notes.entities.OptionUniteEnseignement;
import mg.razherana.notes.services.OptionUniteEnseignementService;

@RestController
@RequestMapping("/api/options/{optionId}/unites")
@RequiredArgsConstructor
public class OptionUniteEnseignementController {

  private final OptionUniteEnseignementService optionUniteEnseignementService;

  @GetMapping
  public ApiResponse<Map<String, List<OptionUniteEnseignement>>> getUnites(@PathVariable Long optionId) {
    List<OptionUniteEnseignement> associations = optionUniteEnseignementService.findByOption(optionId);
    return ApiResponse.success(Map.of("optionUnites", associations));
  }

  @PostMapping
  public ResponseEntity<ApiResponse<Map<String, OptionUniteEnseignement>>> assignUnite(@PathVariable Long optionId,
      @RequestBody AssignUniteRequest request) {
    if (request == null || request.getUniteId() == null) {
      throw new ApiException(HttpStatus.BAD_REQUEST, "UNITE_ID_REQUIRED",
          "The uniteId field is required to associate an unite to an option", null);
    }
    OptionUniteEnseignement optionUnite = optionUniteEnseignementService.assignUnite(optionId, request.getUniteId(),
        request.getGroupeId(), request.getCredits());
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ApiResponse.success(Map.of("optionUnite", optionUnite)));
  }

  @DeleteMapping("/{uniteId}")
  public ResponseEntity<ApiResponse<Map<String, Object>>> removeUnite(@PathVariable Long optionId,
      @PathVariable Long uniteId) {
    optionUniteEnseignementService.removeUnite(optionId, uniteId);
    return ResponseEntity.status(HttpStatus.OK)
        .body(ApiResponse.success(Map.of("unite", Map.of("id", uniteId))));
  }

  @Data
  public static class AssignUniteRequest {
    private Long uniteId;
    private Long groupeId;
    private Integer credits;
  }
}
