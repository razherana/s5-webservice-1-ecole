package mg.razherana.notes.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import mg.razherana.notes.api.ApiException;
import mg.razherana.notes.entities.Option;
import mg.razherana.notes.entities.OptionUniteEnseignement;
import mg.razherana.notes.entities.GroupeMatiere;
import mg.razherana.notes.entities.UniteEnseignement;
import mg.razherana.notes.repositories.OptionRepository;
import mg.razherana.notes.repositories.OptionUniteEnseignementRepository;
import mg.razherana.notes.repositories.UniteEnseignementRepository;
import mg.razherana.notes.repositories.GroupeMatiereRepository;

@Service
@RequiredArgsConstructor
public class OptionUniteEnseignementService {

  private final OptionRepository optionRepository;
  private final OptionUniteEnseignementRepository optionUniteEnseignementRepository;
  private final UniteEnseignementRepository uniteEnseignementRepository;
  private final GroupeMatiereRepository groupeMatiereRepository;

  public List<OptionUniteEnseignement> findByOption(Long optionId) {
    resolveOption(optionId);
    return optionUniteEnseignementRepository.findByOptionId(optionId);
  }

  public OptionUniteEnseignement assignUnite(Long optionId, Long uniteId, Long groupeId, Integer credits) {
    if (uniteId == null) {
      throw new ApiException(HttpStatus.BAD_REQUEST, "UNITE_ID_REQUIRED",
          "uniteId must be provided to create the association", null);
    }
    if (groupeId == null) {
      throw new ApiException(HttpStatus.BAD_REQUEST, "GROUPE_ID_REQUIRED",
          "groupeId must be provided to associate a groupe", null);
    }
    if (credits == null || credits <= 0) {
      throw new ApiException(HttpStatus.BAD_REQUEST, "INVALID_CREDITS",
          "credits must be greater than 0", credits);
    }
    Option option = resolveOption(optionId);
    UniteEnseignement unite = resolveUnite(uniteId);
    GroupeMatiere groupe = resolveGroupe(groupeId);
    optionUniteEnseignementRepository.findByOptionIdAndUniteId(optionId, uniteId)
        .ifPresent(existing -> {
          throw new ApiException(HttpStatus.CONFLICT, "OPTION_UNITE_EXISTS",
              "This unite is already linked to the option", existing.getId());
        });
    OptionUniteEnseignement relation = new OptionUniteEnseignement();
    relation.setOption(option);
    relation.setUnite(unite);
    relation.setGroupe(groupe);
    relation.setCredits(credits);
    return optionUniteEnseignementRepository.save(relation);
  }

  public void removeUnite(Long optionId, Long uniteId) {
    OptionUniteEnseignement relation = optionUniteEnseignementRepository.findByOptionIdAndUniteId(optionId, uniteId)
        .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "OPTION_UNITE_NOT_FOUND",
            "No unite found for option " + optionId + " and unite " + uniteId, uniteId));
    optionUniteEnseignementRepository.delete(relation);
  }

  private Option resolveOption(Long optionId) {
    if (optionId == null) {
      throw new ApiException(HttpStatus.BAD_REQUEST, "OPTION_ID_REQUIRED",
          "optionId must be provided", null);
    }
    return optionRepository.findById(optionId)
        .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "OPTION_NOT_FOUND",
            "Option " + optionId + " not found", optionId));
  }

  private UniteEnseignement resolveUnite(Long uniteId) {
    if (uniteId == null) {
      throw new ApiException(HttpStatus.BAD_REQUEST, "UNITE_ID_REQUIRED",
          "uniteId must be provided", null);
    }
    return uniteEnseignementRepository.findById(uniteId)
        .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "UNITE_NOT_FOUND",
            "Unite d'enseignement " + uniteId + " not found", uniteId));
  }

  private GroupeMatiere resolveGroupe(Long groupeId) {
    if (groupeId == null) {
      throw new ApiException(HttpStatus.BAD_REQUEST, "GROUPE_ID_REQUIRED",
          "groupeId must be provided", null);
    }
    return groupeMatiereRepository.findById(groupeId)
        .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "GROUP_NOT_FOUND",
            "Groupe matiere " + groupeId + " not found", groupeId));
  }
}
