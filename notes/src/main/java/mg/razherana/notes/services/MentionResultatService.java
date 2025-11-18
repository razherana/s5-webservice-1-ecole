package mg.razherana.notes.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import mg.razherana.notes.api.ApiException;
import mg.razherana.notes.entities.MentionResultat;
import mg.razherana.notes.repositories.MentionResultatRepository;

@Service
@RequiredArgsConstructor
public class MentionResultatService {

  private final MentionResultatRepository mentionResultatRepository;

  public List<MentionResultat> findAll() {
    return mentionResultatRepository.findAll();
  }

  public MentionResultat findById(Long id) {
    return mentionResultatRepository.findById(id)
        .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "MENTION_NOT_FOUND",
            "Mention resultat " + id + " not found", null));
  }

  public MentionResultat create(MentionResultat mention) {
    validateRange(mention.getMoyenneMin(), mention.getMoyenneMax());
    ensureNoOverlap(null, mention.getMoyenneMin(), mention.getMoyenneMax());
    return mentionResultatRepository.save(mention);
  }

  public MentionResultat update(Long id, MentionResultat mention) {
    MentionResultat existing = findById(id);
    validateRange(mention.getMoyenneMin(), mention.getMoyenneMax());
    ensureNoOverlap(id, mention.getMoyenneMin(), mention.getMoyenneMax());
    existing.setLibelle(mention.getLibelle());
    existing.setMoyenneMin(mention.getMoyenneMin());
    existing.setMoyenneMax(mention.getMoyenneMax());
    return mentionResultatRepository.save(existing);
  }

  public void delete(Long id) {
    MentionResultat existing = findById(id);
    mentionResultatRepository.delete(existing);
  }

  private void validateRange(Integer min, Integer max) {
    if (min == null || max == null || min > max) {
      throw new ApiException(HttpStatus.BAD_REQUEST, "INVALID_RANGE",
          "moyenneMin must be less than or equal to moyenneMax", null);
    }
  }

  private void ensureNoOverlap(Long currentId, int min, int max) {
    List<MentionResultat> mentions = mentionResultatRepository.findAll();
    for (MentionResultat other : mentions) {
      if (currentId != null && other.getId() != null && other.getId().equals(currentId)) {
        continue;
      }
      if (rangesOverlap(min, max, other.getMoyenneMin(), other.getMoyenneMax())) {
        throw new ApiException(HttpStatus.CONFLICT, "MENTION_RANGE_CONFLICT",
            "Mention range overlaps with mention " + other.getId(), other.getId());
      }
    }
  }

  private boolean rangesOverlap(int min1, int max1, int min2, int max2) {
    return min1 <= max2 && min2 <= max1;
  }
}
