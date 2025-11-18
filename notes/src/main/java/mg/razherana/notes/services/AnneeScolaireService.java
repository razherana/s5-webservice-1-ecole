package mg.razherana.notes.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import mg.razherana.notes.entities.AnneeScolaire;
import mg.razherana.notes.repositories.AnneeScolaireRepository;
import mg.razherana.notes.api.ApiException;

@Service
@RequiredArgsConstructor
public class AnneeScolaireService {

  private final AnneeScolaireRepository anneeScolaireRepository;

  public List<AnneeScolaire> findAll() {
    return anneeScolaireRepository.findAll();
  }

  public AnneeScolaire findById(Long id) {
    return anneeScolaireRepository.findById(id)
        .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "SCHOOL_YEAR_NOT_FOUND",
            "Annee scolaire " + id + " not found", null));
  }

  public AnneeScolaire create(AnneeScolaire anneeScolaire) {
    return anneeScolaireRepository.save(anneeScolaire);
  }

  public AnneeScolaire update(Long id, AnneeScolaire anneeScolaire) {
    AnneeScolaire existing = findById(id);
    existing.setAnnee1(anneeScolaire.getAnnee1());
    existing.setAnnee2(anneeScolaire.getAnnee2());
    return anneeScolaireRepository.save(existing);
  }

  public void delete(Long id) {
    AnneeScolaire existing = findById(id);
    anneeScolaireRepository.delete(existing);
  }
}
