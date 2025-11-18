package mg.razherana.notes.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import lombok.RequiredArgsConstructor;
import mg.razherana.notes.api.ApiException;
import mg.razherana.notes.entities.GroupeMatiere;
import mg.razherana.notes.repositories.GroupeMatiereRepository;

@Service
@RequiredArgsConstructor
public class GroupeMatiereService {

  private final GroupeMatiereRepository groupeMatiereRepository;

  public List<GroupeMatiere> findAll() {
    return groupeMatiereRepository.findAll();
  }

  public GroupeMatiere findById(Long id) {
    return groupeMatiereRepository.findById(id)
        .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "GROUP_NOT_FOUND",
            "Groupe matiere " + id + " not found", null));
  }

  public GroupeMatiere create(GroupeMatiere groupeMatiere) {
    ensureUniqueLibelle(null, groupeMatiere.getLibelle());
    return groupeMatiereRepository.save(groupeMatiere);
  }

  public GroupeMatiere update(Long id, GroupeMatiere groupeMatiere) {
    GroupeMatiere existing = findById(id);
    ensureUniqueLibelle(id, groupeMatiere.getLibelle());
    existing.setLibelle(groupeMatiere.getLibelle());
    existing.setUnites(groupeMatiere.getUnites());
    return groupeMatiereRepository.save(existing);
  }

  public void delete(Long id) {
    GroupeMatiere existing = findById(id);
    groupeMatiereRepository.delete(existing);
  }

  private void ensureUniqueLibelle(Long currentId, String libelle) {
    if (!StringUtils.hasText(libelle)) {
      throw new ApiException(HttpStatus.BAD_REQUEST, "INVALID_LIBELLE",
          "libelle must not be empty", null);
    }

    groupeMatiereRepository.findByLibelle(libelle)
        .ifPresent(other -> {
          if (currentId == null || !other.getId().equals(currentId)) {
            throw new ApiException(HttpStatus.CONFLICT, "GROUP_LIBELLE_EXISTS",
                "libelle already used by groupe matiere " + other.getId(), other.getId());
          }
        });
  }
}
