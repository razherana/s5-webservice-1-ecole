package mg.razherana.notes.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import lombok.RequiredArgsConstructor;
import mg.razherana.notes.api.ApiException;
import mg.razherana.notes.entities.UniteEnseignement;
import mg.razherana.notes.repositories.UniteEnseignementRepository;

@Service
@RequiredArgsConstructor
public class UniteEnseignementService {

  private final UniteEnseignementRepository uniteEnseignementRepository;

  public List<UniteEnseignement> findAll() {
    return uniteEnseignementRepository.findAll();
  }

  public UniteEnseignement findById(Long id) {
    return uniteEnseignementRepository.findById(id)
        .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "UE_NOT_FOUND",
            "Unite d'enseignement " + id + " not found", null));
  }

  public UniteEnseignement create(UniteEnseignement unite) {
    ensureValidCode(null, unite.getCodeMatiere());
    ensureValidCredits(unite.getCredits());
    return uniteEnseignementRepository.save(unite);
  }

  public UniteEnseignement update(Long id, UniteEnseignement unite) {
    UniteEnseignement existing = findById(id);
    ensureValidCode(id, unite.getCodeMatiere());
    ensureValidCredits(unite.getCredits());
    existing.setCodeMatiere(unite.getCodeMatiere());
    existing.setIntitule(unite.getIntitule());
    existing.setCredits(unite.getCredits());
    existing.setGroupe(unite.getGroupe());
    existing.setSemestre(unite.getSemestre());
    existing.setNotes(unite.getNotes());
    return uniteEnseignementRepository.save(existing);
  }

  public void delete(Long id) {
    UniteEnseignement existing = findById(id);
    uniteEnseignementRepository.delete(existing);
  }

  private void ensureValidCode(Long currentId, String codeMatiere) {
    if (!StringUtils.hasText(codeMatiere)) {
      throw new ApiException(HttpStatus.BAD_REQUEST, "INVALID_CODE",
          "codeMatiere must not be empty", null);
    }
    uniteEnseignementRepository.findByCodeMatiere(codeMatiere)
        .ifPresent(other -> {
          if (currentId == null || !other.getId().equals(currentId)) {
            throw new ApiException(HttpStatus.CONFLICT, "UE_CODE_EXISTS",
                "codeMatiere already used by unite d'enseignement " + other.getId(), other.getId());
          }
        });
  }

  private void ensureValidCredits(Integer credits) {
    if (credits == null || credits <= 0) {
      throw new ApiException(HttpStatus.BAD_REQUEST, "INVALID_CREDITS",
          "credits must be greater than 0", credits);
    }
  }
}
