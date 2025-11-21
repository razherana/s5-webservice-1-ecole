package mg.razherana.notes.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import lombok.RequiredArgsConstructor;
import mg.razherana.notes.api.ApiException;
import mg.razherana.notes.entities.Option;
import mg.razherana.notes.entities.Semestre;
import mg.razherana.notes.repositories.OptionRepository;
import mg.razherana.notes.repositories.SemestreRepository;

@Service
@RequiredArgsConstructor
public class OptionService {

  private final OptionRepository optionRepository;
  private final SemestreRepository semestreRepository;

  public List<Option> findAll() {
    return optionRepository.findAll();
  }

  public Option findById(Long id) {
    return optionRepository.findById(id)
        .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "OPTION_NOT_FOUND",
            "Option " + id + " not found", null));
  }

  public List<Option> findBySemestre(Long semestreId) {
    ensureSemestreExists(semestreId);
    return optionRepository.findBySemestreId(semestreId);
  }

  public Option create(Option option) {
    validateLibelle(option.getLibelle());
    Semestre semestre = resolveSemestre(option.getSemestre());
    ensureUniqueLibelle(null, semestre.getId(), option.getLibelle());
    Option toSave = new Option();
    toSave.setLibelle(option.getLibelle());
    toSave.setSemestre(semestre);
    return optionRepository.save(toSave);
  }

  public Option update(Long id, Option option) {
    Option existing = findById(id);
    validateLibelle(option.getLibelle());
    Semestre semestre = resolveSemestre(option.getSemestre());
    ensureUniqueLibelle(id, semestre.getId(), option.getLibelle());
    existing.setLibelle(option.getLibelle());
    existing.setSemestre(semestre);
    return optionRepository.save(existing);
  }

  public void delete(Long id) {
    Option existing = findById(id);
    optionRepository.delete(existing);
  }

  private void validateLibelle(String libelle) {
    if (!StringUtils.hasText(libelle)) {
      throw new ApiException(HttpStatus.BAD_REQUEST, "OPTION_LIBELLE_REQUIRED",
          "Option libelle must not be empty", libelle);
    }
  }

  private Semestre resolveSemestre(Semestre semestre) {
    if (semestre == null || semestre.getId() == null) {
      throw new ApiException(HttpStatus.BAD_REQUEST, "OPTION_SEMESTRE_REQUIRED",
          "Option must reference a semestre", null);
    }
    return semestreRepository.findById(semestre.getId())
        .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "SEMESTRE_NOT_FOUND",
            "Semestre " + semestre.getId() + " not found", semestre.getId()));
  }

  private void ensureSemestreExists(Long semestreId) {
    semestreRepository.findById(semestreId)
        .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "SEMESTRE_NOT_FOUND",
            "Semestre " + semestreId + " not found", semestreId));
  }

  private void ensureUniqueLibelle(Long currentId, Long semestreId, String libelle) {
    if (libelle == null) {
      return;
    }
    optionRepository.findBySemestreIdAndLibelleIgnoreCase(semestreId, libelle)
        .ifPresent(existing -> {
          if (currentId == null || !existing.getId().equals(currentId)) {
            throw new ApiException(HttpStatus.CONFLICT, "OPTION_DUPLICATE",
                "An option with the same libelle already exists for this semestre", existing.getId());
          }
        });
  }
}
